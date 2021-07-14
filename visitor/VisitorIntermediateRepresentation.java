package visitor;

import visitor.ast.*;
import visitor.ir.*;
import java.lang.String;
import java.util.Vector;

public class VisitorIntermediateRepresentation extends Visitor
{
    /* PRIVATE */

    private Environment<ElementFunction> m_func_env;
    private Environment<ElementRegister> m_var_env;
    private Pair<IRFunction, ElementFunction> m_func_curr;
    private IRProgram m_program;
    private int m_counter_register;
    private int m_counter_label;

    private void __enscope()
    {
        m_func_env.enscope();
        m_var_env.enscope();
    }

    private void __descope()
    {
        m_func_env.descope();
        m_var_env.descope();
    }

    private int __getRegister()
    {
        return m_counter_register++;
    }

    private int __getLabel()
    {
        return m_counter_label++;
    }

    private void __addVariable(Element e, boolean isParameter)
    {
        String name = e.getName();
        Type type = e.getType();
        boolean is_array = e.isArray();
        int array_size = e.getArraySize();
        int register = __getRegister();

        IRDeclaration declaration = new IRDeclaration(register, is_array, name, isParameter);
        m_func_curr.getFirst().addDeclaration(declaration);

        ElementRegister er = new ElementRegister(type, array_size, name, register);
        m_var_env.add(name, er);

        if (is_array)
        {
            IRStatement statement = new IRAssignmentArray(register, array_size, type);
            m_func_curr.getFirst().addStatement(statement);
        }
    }

    private Pair<Type,Integer> __addLiteral(Literal l)
    {
        Type type = l.getType();
        int register = __getRegister();
        m_func_curr.getFirst().addDeclaration(new IRDeclaration(register, false, type));
        m_func_curr.getFirst().addStatement(new IRAssignmentConstant(register, l));
        return new Pair<Type,Integer>(type, register);
    }

    /* PUBLIC */

    public VisitorIntermediateRepresentation(String name, Environment<ElementFunction> func_env)
    {
        m_func_env = func_env;
        m_var_env = new Environment<ElementRegister>();
        m_func_curr = null;
        m_program = new IRProgram(name);
        m_counter_register = 0;
        m_counter_label = 0;
    }

    public IRProgram getIRProgram()
    {
        return m_program;
    }

    // returns an Intermediate Representation
    public Object visit(Program program)
    {
        m_counter_label = 0;

        // add the function to the Intermediate Representation
        for (Function function : program.getFunctions())
        {
            __enscope();
            function.accept(this);
            __descope();
        }
        return m_program;
    }

    // returns null
	public Object visit(Function function)
    {
        m_counter_register = 0;
        function.getFunctionDeclaration().accept(this);
        function.getFunctionBody().accept(this);
        return null;
    }

    // returns null
    public Object visit(FunctionDeclaration func_decl)
    {
        // set current function
        String name = func_decl.getIdentifier().getName();
        Type type = func_decl.getCompoundType().getTypeNode().getType();
        boolean is_array = func_decl.getCompoundType().isArray();

        IRFunction irf = new IRFunction(type, is_array, name);
        m_program.addFunction(irf);
        ElementFunction ef = m_func_env.find(name);        
        m_func_curr = new Pair<IRFunction, ElementFunction>(irf, ef);

        // add function parameters to Intermediate Representation and variable environment
        for (Element e : ef.getParameters())
        {
            irf.addParameter(e.getType(), e.isArray());
            __addVariable(e, true);
        }

        return null;
    }

    // returns null
    public Object visit(FunctionBody function_body)
    {
        // add variable declarations to ir and variable environment
        for (Variable var : function_body.getVariables())
        {
            Element e = (Element) var.accept(this);
            __addVariable(e, false);
        }

        // convert statements into ir
        for (Statement statement : function_body.getStatements())
            statement.accept(this);

        return null;
    }

    // returns null
    public Object visit(Block block)
    {
        for (Statement statement : block.getStatements())
            statement.accept(this);
        return null;
    }

    // returns Pair<Type, Integer>
    public Object visit(CompoundType ct)
    {
        // check that void[] isn't a thing
        Type type = (Type) ct.getTypeNode().accept(this);
        return new Pair<Type, Integer>(type, ct.getArraySize());
    }

    // returns Type
    public Object visit(TypeNode type_node)
    {
        return type_node.getType();
    }

    // returns String
    public Object visit(Identifier identifier)
    {
        return identifier.getName();
    }

    // returns an Element
    public Object visit(Variable var)
    {
        // create and return the Element
        Pair<Type, Integer> pair = (Pair<Type, Integer>) var.getCompoundType().accept(this);
        String name = var.getIdentifier().accept(this).toString();
        return new Element(pair.getFirst(), pair.getSecond(), name);
    }


    // ALL STATEMENTS RETURN NULL
    public Object visit(StatementAssignment sa)
    {
        // find the element being assigned to
        String name = sa.getIdentifier().accept(this).toString();
        ElementRegister e = m_var_env.find(name);

        // get the register the expression assigned to
        Pair<Type,Integer> type_register_address = (Pair<Type,Integer>) sa.getExpression().accept(this);

        // add assignment statement to list of statements for this function
        IRAssignmentRegister irar = new IRAssignmentRegister(e.getRegister(), type_register_address.getSecond());
        m_func_curr.getFirst().addStatement(irar);
        return null;
    }

    public Object visit(StatementEmpty statement_empty)
    {
        return null;
    }

    public Object visit(StatementExpression statement_expression)
    {
        statement_expression.getExpression().accept(this);
        return null;
    }

    public Object visit(StatementIfElse sie)
    {
        Pair<Type,Integer> type_register = (Pair<Type,Integer>) sie.getExpression().accept(this);
        int labelIf = __getLabel();
        int labelEnd = __getLabel();

        // if conditional is true, go to that block
        m_func_curr.getFirst().addStatement(new IRStatementGoto(labelIf, type_register.getSecond()));

        // add lines for else block
        if (sie.hasElse())
            sie.getBlockSecond().accept(this);

        // after else statement, goto after if statement
        m_func_curr.getFirst().addStatement(new IRStatementGoto(labelEnd));

        // add if label
        m_func_curr.getFirst().addStatement(new IRStatementLabel(labelIf));

        // add lines for if block
        sie.getBlockFirst().accept(this);

        // add end label
        m_func_curr.getFirst().addStatement(new IRStatementLabel(labelEnd));

        // done
        return null;
    }

    public Object visit(StatementPrint sp)
    {
        Pair<Type,Integer> type_register = (Pair<Type,Integer>) sp.getExpression().accept(this);
        m_func_curr.getFirst().addStatement(new IRStatementPrint(type_register.getSecond(), type_register.getFirst(), sp.isNewline()));
        return null;
    }

    public Object visit(StatementReturn sr)
    {
        IRStatementReturn statement = new IRStatementReturn();
        if (sr.hasExpression())
        {
            Pair<Type,Integer> type_register = (Pair<Type,Integer>) sr.getExpression().accept(this);
            statement.setRegister(type_register.getSecond());
        }
        m_func_curr.getFirst().addStatement(statement);
        return null;
    }

    public Object visit(StatementWhile sw)
    {
        // add label for looping
        int labelLoop = __getLabel();
        int labelEnd = __getLabel();
        
        // add the conditional to enter the while loop
        Pair<Type,Integer> type_register = (Pair<Type,Integer>) sw.getExpression().accept(this);
        m_func_curr.getFirst().addStatement(new IRStatementGoto(type_register.getSecond(), labelLoop));
        m_func_curr.getFirst().addStatement(new IRStatementGoto(labelEnd));
        m_func_curr.getFirst().addStatement(new IRStatementLabel(labelLoop));

        // add the block statements to the Intermediate Representation
        sw.getBlock().accept(this);

        // add the conditional to repeat the while loop
        sw.getExpression().accept(this);
        m_func_curr.getFirst().addStatement(new IRStatementGoto(type_register.getSecond(), labelLoop));

        // add the way out of the while loop
        m_func_curr.getFirst().addStatement(new IRStatementLabel(labelEnd));

        return null;
    }


    // ALL EXPRESSIONS (AND LITERALS) RETURN TYPE
    public Object visit(ExpressionFunction exf)
    {
        /*
        private Identifier m_identifier;
        private Vector<Expression> m_parameters;
        */
        String name = exf.getIdentifier().accept(this).toString();
        ElementFunction ef = m_func_env.find(name);
        int register = __getRegister();
        Type type = ef.getType();

        IRAssignmentCall irac = new IRAssignmentCall(register, name, type);

        Vector<Expression> expressions = exf.getParameters();
        for (Expression expression : expressions)
        {
            Pair<Type,Integer> pair = (Pair<Type,Integer>) expression.accept(this);
            irac.addParameterRegister(pair.getSecond());
        }
        m_func_curr.getFirst().addStatement(irac);

        return new Pair<Type,Integer>(type, register);
    }

    public Object visit(ExpressionIdentifier ei)
    {
        String name = ei.getIdentifier().accept(this).toString();
        ElementRegister er = m_var_env.find(name);
        return new Pair<Type,Integer>(er.getType(), er.getRegister());
    }

    public Object visit(ExpressionOperation eo)
    {
        Pair<Type,Integer> type_register_first = (Pair<Type,Integer>) eo.getExpressionFirst().accept(this);
        Pair<Type,Integer> type_register_second = (Pair<Type,Integer>) eo.getExpressionSecond().accept(this);

        int register_left = type_register_first.getSecond();
        int register_right = type_register_second.getSecond();
        int register_assign = register_right;
        Operator operator = eo.getOperator();
        Type type = type_register_first.getFirst();

        Type type_new = type;
        if (operator == Operator.Operator_Equals || operator == Operator.Operator_Less_Than)
        {
            type_new = Type.Type_Boolean;
            if (type != type_new)
                register_assign = __getRegister();
        }

        IRAssignmentOperation irao = new IRAssignmentOperation(register_assign, register_right, register_left, operator, type);
        m_func_curr.getFirst().addStatement(irao);

        return new Pair<Type,Integer>(type_new, register_assign);
    }

    public Object visit(LiteralBoolean literal_boolean)
    {
        return __addLiteral(literal_boolean);
    }

    public Object visit(LiteralCharacter literal_character)
    {
        return __addLiteral(literal_character);
    }

    public Object visit(LiteralFloat literal_float)
    {
        return __addLiteral(literal_float);
    }

    public Object visit(LiteralInteger literal_integer)
    {
        return __addLiteral(literal_integer);
    }

    public Object visit(LiteralString literal_string)
    {
        return __addLiteral(literal_string);
    }
}
