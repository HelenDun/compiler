package visitor;

import visitor.ast.*;
import visitor.ir.*;
import java.lang.String;
import java.util.Vector;

public class VisitorIntermediateRepresentation extends Visitor
{
    /* PRIVATE */
    private static final int NO_ARRAY = -1;

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

    private int __getRegister(boolean is_array, Type type)
    {
        return __getRegister(is_array, type, null, false);
    }

    private int __getRegister(boolean is_array, Type type, String name, boolean isParameter)
    {
        int register = m_counter_register++;
        IRDeclaration declaration = new IRDeclaration(register, is_array, type, name, isParameter);
        m_func_curr.getFirst().addDeclaration(declaration);
        return register;
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
        int register = __getRegister(is_array, type, name, isParameter);

        

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
        int register = __getRegister(false, type);
        m_func_curr.getFirst().addStatement(new IRAssignmentConstant(register, NO_ARRAY, type, l));
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
        m_counter_label = 0;
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
        Pair<Type,Integer> pair = (Pair<Type,Integer>) var.getCompoundType().accept(this);
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

        // get the array index if the variable being assigned to is an array
        int array_index = NO_ARRAY;
        if (sa.isArray())
        {
            Pair<Type,Integer> type_register_array = (Pair<Type,Integer>) sa.getArrayIndex().accept(this);
            array_index = type_register_array.getSecond();
        }

        // add assignment statement to list of statements for this function
        IRAssignmentRegister irar = new IRAssignmentRegister(e.getRegister(), array_index, type_register_address.getFirst(), type_register_address.getSecond(), NO_ARRAY);
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
        m_func_curr.getFirst().addStatement(new IRStatementGoto(type_register.getSecond(), labelIf));

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
            statement.setType(type_register.getFirst());
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
        type_register = (Pair<Type,Integer>) sw.getExpression().accept(this);
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

        Type type = ef.getType();
        int register = -1;
        if (type != Type.Type_Void)
            register = __getRegister(false, type);
        IRAssignmentCall irac = new IRAssignmentCall(register, NO_ARRAY, name, type);

        Vector<Expression> expressions = exf.getParameters();
        for (Expression expression : expressions)
        {
            Pair<Type,Integer> pair = (Pair<Type,Integer>) expression.accept(this);
            irac.addParameterRegister(pair);
        }
        m_func_curr.getFirst().addStatement(irac);

        return new Pair<Type,Integer>(type, register);
    }

    public Object visit(ExpressionIdentifier ei)
    {
        String name = ei.getIdentifier().accept(this).toString();

        ElementRegister er = m_var_env.find(name);
        Type type = er.getType();
        int register_assign = er.getRegister();

        // if an array, get the value from the array at the index
        if (ei.isArray())
        {
            Pair<Type,Integer> type_register = (Pair<Type,Integer>) ei.getArrayIndex().accept(this);
            register_assign = __getRegister(false, type);
            int register_right = er.getRegister();
            int register_right_array = type_register.getSecond();
            m_func_curr.getFirst().addStatement(new IRAssignmentRegister(register_assign, NO_ARRAY, type_register.getFirst(), register_right, register_right_array));
        }

        return new Pair<Type,Integer>(type, register_assign);
    }

    public Object visit(ExpressionOperation eo)
    {
        Pair<Type,Integer> type_register_first = (Pair<Type,Integer>) eo.getExpressionFirst().accept(this);
        Pair<Type,Integer> type_register_second = (Pair<Type,Integer>) eo.getExpressionSecond().accept(this);

        int register_left = type_register_first.getSecond();
        int register_right = type_register_second.getSecond();
        Operator operator = eo.getOperator();
        Type type = type_register_first.getFirst();

        Type type_new = type;
        if (operator == Operator.Operator_Equals || operator == Operator.Operator_Less_Than)
            type_new = Type.Type_Boolean;
        int register_assign = __getRegister(false, type_new);

        IRAssignmentOperation irao = new IRAssignmentOperation(register_assign, NO_ARRAY, type, register_right, register_left, operator);
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
