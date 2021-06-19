package ast;

import java.util.Vector;
import java.lang.String;
import java.lang.RuntimeException;

public class VisitorType extends Visitor
{
    /* PRIVATE */
    
    private Vector<EnvironmentElementFunction> m_function_environment;
    private Vector<EnvironmentElement> m_variable_environment;
    private EnvironmentElementFunction m_current_function;
    
    private EnvironmentElementFunction __findElementFunction(String name)
    {
        for (EnvironmentElementFunction ef : m_function_environment)
        {
            if (ef.get_name().equals(name))
            {
                return ef;
            }
        }
        return null;
    }

    private EnvironmentElement __findElementVariable(String name)
    {
        for (EnvironmentElement ef : m_variable_environment)
        {
            if (ef.get_name().equals(name))
            {
                return ef;
            }
        }
        return null;
    }

    private boolean __isSubtype(Type t1, Type t2)
    {
        return t1.equals(t2);
    }

    private void __throwError(String errorMsg, int lineNumber, int lineOffset)
    {
        String result = "Error:";
        result += String.valueOf(lineNumber) + ":";
        result += String.valueOf(lineOffset) + ":";
        result += errorMsg;
        throw new SemanticException(result, m_function_environment, m_variable_environment);
    }

    private boolean __isMain(EnvironmentElementFunction ef, int lineNumber, int lineOffset)
    {
        if (!ef.get_name().equals("main"))
        {
            return false; // function is not called 'main'
        }
        if (ef.get_parameters().size() > 0)
        {
            String errorMsg = "The main() function should have no parameters";
            __throwError(errorMsg, lineNumber, lineOffset);
        }
        if (!__isSubtype(Type.Type_Void, ef.get_type()))
        {
            String errorMsg = "The return type of the main() function should be void";
            __throwError(errorMsg, lineNumber, lineOffset);
        }
        return true;
    }

    /* PUBLIC */

    public VisitorType()
    {
        m_function_environment = new Vector<EnvironmentElementFunction>();
        m_variable_environment = new Vector<EnvironmentElement>();
        m_current_function = null;
    }

    // returns null
    public Object visit(Program program)
    {
        boolean hasMain = false;

        // go through function declarations
        // and populate the function environement
        for (Function function : program.getFunctions())
        {
            FunctionDeclaration fd = function.get_function_declaration();

            EnvironmentElementFunction ef = (EnvironmentElementFunction) fd.accept(this);
            m_function_environment.add(ef);

            // check whether the function is main
            hasMain = (hasMain || __isMain(ef, fd.getLine(), fd.getCharPositionInLine()));
        }

        // check that main is one of the functions
        if (!hasMain)
            __throwError("The program must have a main() function", program.getLine(), program.getCharPositionInLine());

        // go through function bodies
        // and type-check statements
        for (Function function : program.getFunctions())
            function.accept(this);
        return null;
    }

    // returns null
	public Object visit(Function function)
    {
        // add function parameters to the variable environment
        String name = function.get_function_declaration().get_identifier().get_name();
        EnvironmentElementFunction ef = __findElementFunction(name);
        m_current_function = ef;

        m_variable_environment.clear();
        for (EnvironmentElement e : ef.get_parameters())
            m_variable_environment.add(e);

        function.get_function_body().accept(this);
        return null;
    }

    // returns an EnvironmentElementFunction
    public Object visit(FunctionDeclaration fd)
    {
        // check that the function is not already in the environment
        String name = fd.get_identifier().get_name();
        if (__findElementFunction(name) != null)
            __throwError("Functions with the same name are not allowed", fd.getLine(), fd.getCharPositionInLine());

        // create the EnvironmentElementFunction
        Pair<Type, Integer> pair = (Pair<Type, Integer>) fd.get_compound_type().accept(this);
        Type type = pair.getFirst();
        int array_size = pair.getSecond();
    
        EnvironmentElementFunction ef = new EnvironmentElementFunction(type, array_size, name);
        m_variable_environment.clear();
        for (Variable var : fd.get_parameters())
        {
            EnvironmentElement e = (EnvironmentElement) var.accept(this);
            m_variable_environment.add(e);
            ef.add_parameter(e);
        }
        return ef;
    }

    // returns null
    public Object visit(FunctionBody function_body)
    {
        // add variable declarations to variable environment
        for (Variable var : function_body.get_variables())
            m_variable_environment.add(((EnvironmentElement) var.accept(this)));

        // type-check statements
        for (Statement statement : function_body.get_statements())
            statement.accept(this);
        return null;
    }

    // returns null
    public Object visit(Block block)
    {
        for (Statement statement : block.get_statements())
            statement.accept(this);
        return null;
    }

    // returns Pair<Type, Integer> 
    public Object visit(CompoundType ct)
    {
        // check that void[] isn't a thing
        Type type = (Type) ct.get_type_node().accept(this);
        if (__isSubtype(Type.Type_Void, type) && ct.is_array())
            __throwError("Arrays cannot be of type 'void'", ct.getLine(), ct.getCharPositionInLine());

        return new Pair<Type, Integer>(type, ct.get_array_size());
    }

    // returns Type
    public Object visit(TypeNode type_node)
    {
        return type_node.get_type();
    }

    // returns String
    public Object visit(Identifier identifier)
    {
        return identifier.get_name();
    }

    // returns an EnvironmentElement
    public Object visit(Variable var)
    {
        Pair<Type, Integer> pair = (Pair<Type, Integer>) var.get_compound_type().accept(this);
        Type type = pair.getFirst();
        int array_size = pair.getSecond();

        // check that the variable is not of type Void
        if (__isSubtype(Type.Type_Void, type))
            __throwError("Variables of type 'void' are not allowed", var.getLine(), var.getCharPositionInLine());

        // check that the variable is not already in the environment
        String name = var.get_identifier().accept(this).toString();
        if (__findElementFunction(name) != null)
            __throwError("Re-declaration of variables is not allowed", var.getLine(), var.getCharPositionInLine());
        
        // create and return the EnvironmentElement
        return new EnvironmentElement(type, array_size, name);
    }


    // ALL STATEMENTS RETURN NULL
    public Object visit(StatementAssignment sa)
    {
        /*
        Identifier m_identifier;
        Expression m_array_index; // if null, then "id = expr", else "id[index] = expr"
        Expression m_expression;
        */
        String name = sa.get_identifier().accept(this).toString();
        EnvironmentElement e = __findElementVariable(name);

        // check that the variable to be assigned is in the environment
        if (e == null)
            __throwError("Variable to be assigned is not declared", sa.getLine(), sa.getCharPositionInLine());

        // check if the variable is an array but the assignment does not assign an index
        if (e.is_array() && !sa.is_array())
            __throwError("Must assign the value to some index of the array variable", sa.getLine(), sa.getCharPositionInLine());

        // check if the assignment tries to index an non-array
        if (!e.is_array() && sa.is_array())
            __throwError("Cannot index a non-array variable", sa.getLine(), sa.getCharPositionInLine());

        // check if the indexing uses an int
        if (sa.is_array())
        {
            Type type_index = (Type) sa.get_array_index().accept(this);
            if (__isSubtype(Type.Type_Int, type_index))
                __throwError("Cannot index array with non-Integer", sa.getLine(), sa.getCharPositionInLine());
        }

        Type type = (Type) sa.get_expression().accept(this);

        // check that correct type is being assigned to the variable
        if (__isSubtype(e.get_type(), type))
            __throwError("Type of assignment expression does not match variable", sa.getLine(), sa.getCharPositionInLine());

        return null;
    }

    public Object visit(StatementEmpty statement_empty)
    {
        return null;
    }

    public Object visit(StatementExpression statement_expression)
    {
        statement_expression.get_expression().accept(this);
        return null;
    }

    public Object visit(StatementIfElse sie)
    {
        Type type = (Type) sie.get_expression().accept(this);

        // check that the conditional is a boolean
        if (__isSubtype(Type.Type_Boolean, type))
            __throwError("If-Else conditional expression is not of type 'boolean'", sie.getLine(), sie.getCharPositionInLine());

        // type-check the blocks
        sie.get_block1().accept(this);
        sie.get_block2().accept(this);
        return null;
    }

    public Object visit(StatementPrint sp)
    {
        // type valid for int, float, char, string, or boolean
        Type type = (Type) sp.get_expression().accept(this);
        if (__isSubtype(Type.Type_Void, type))
            __throwError("Cannot print type 'void'", sp.getLine(), sp.getCharPositionInLine());
        return null;
    }

    public Object visit(StatementReturn sr)
    {
        if (sr.hasReturnExpression())
        {
            if (__isSubtype(Type.Type_Void, m_current_function.m_type))
                __throwError("Return expression should not return anything", sr.getLine(), sr.getCharPositionInLine());

            Type type = (Type) sr.getExpression().accept(this);

            if (__isSubtype(m_current_function.m_type, type))
                __throwError("Type of return expression does not match the function's return type", sr.getLine(), sr.getCharPositionInLine());
        }

        if (!__isSubtype(Type.Type_Void, m_current_function.m_type))
            __throwError("Return expression should return something", sr.getLine(), sr.getCharPositionInLine());

        return null;
    }

    public Object visit(StatementWhile sw)
    {
        Type type = (Type) sw.get_expression().accept(this);

        // check that the conditional is a boolean
        if (__isSubtype(Type.Type_Boolean, type))
            __throwError("While conditional expression is not of type 'boolean'", sw.getLine(), sw.getCharPositionInLine());

        // type-check the blocks
        sw.get_block().accept(this);
        return null;
    }


    // ALL EXPRESSIONS (AND LITERALS) RETURN TYPE
    public Object visit(ExpressionFunction ef)
    {
        String name = ef.m_identifier.accept(this).toString();
        EnvironmentElementFunction eef = __findElementFunction(name);

        // check the function exists
        if (eef == null)
            __throwError("Function to be called does not exist", ef.getLine(), ef.getCharPositionInLine());

        Vector<EnvironmentElement> parameters = eef.get_parameters();
        Vector<Expression> expressions = ef.get_parameters();

        // check there are as many expressions as parameters
        if (parameters.size() != expressions.size())
            __throwError("Incorrect number of parameters in function call", ef.getLine(), ef.getCharPositionInLine());

        // check each expression matches the type of the parameter
        for (int i = 0; i < parameters.size(); ++i)
        {
            EnvironmentElement var = parameters.elementAt(i);
            Expression exp = expressions.elementAt(i);
            Type type = (Type) exp.accept(this);

            if (__isSubtype(var.get_type(), type))
                __throwError("Type of expression does not match the function parameter", exp.getLine(), exp.getCharPositionInLine());
        }

        return eef.get_type();
    }

    public Object visit(ExpressionIdentifier ei)
    {
        String name = ei.m_identifier.accept(this).toString();
        EnvironmentElement ef = __findElementVariable(name);

        // check the variable exists
        if (ef == null)
            __throwError("Variable is not declared", ei.getLine(), ei.getCharPositionInLine());

        // check if the variable is an array but the expression does not index
        if (ef.is_array() && !ei.is_array())
            __throwError("Type 'array' does not exist; Please index the array", ei.getLine(), ei.getCharPositionInLine());

        // check if the assignment tries to index an non-array
        if (!ef.is_array() && ei.is_array())
            __throwError("Cannot index a non-array variable", ei.getLine(), ei.getCharPositionInLine());

        // check that the index of the array is an int
        if (ei.is_array())
        {
            Type type_index = (Type) ei.get_array_index().accept(this);
            if (__isSubtype(Type.Type_Int, type_index))
                __throwError("Cannot index array with non-Integer", ei.getLine(), ei.getCharPositionInLine());
        }

        return ef.get_type();
    }

    public Object visit(ExpressionOperation eo)
    {
        Type type1 = (Type) eo.get_expression1().accept(this);
        Type type2 = (Type) eo.get_expression2().accept(this);

        // check that both sides of the operation are of the same type
        if (!__isSubtype(type1, type2) || !__isSubtype(type2, type1))
            __throwError("Cannot operate on values of different types", eo.getLine(), eo.getCharPositionInLine());

        // check the operation is not on void
        if (__isSubtype(Type.Type_Void, type1))
            __throwError("Cannot operate on values of type 'void'", eo.getLine(), eo.getCharPositionInLine());

		switch (eo.get_operator())
		{
			case Operator_Multiply:
                if (__isSubtype(Type.Type_Char, type1))
                    __throwError("Cannot multiply chars", eo.getLine(), eo.getCharPositionInLine());
                if (__isSubtype(Type.Type_String, type1))
                    __throwError("Cannot multiply strings", eo.getLine(), eo.getCharPositionInLine());
                if (__isSubtype(Type.Type_Boolean, type1))
                    __throwError("Cannot multiply booleans", eo.getLine(), eo.getCharPositionInLine());
                break;
			case Operator_Addition:
                if (__isSubtype(Type.Type_Boolean, type1))
                    __throwError("Cannot add booleans", eo.getLine(), eo.getCharPositionInLine());
                break;
			case Operator_Subtraction:
                if (__isSubtype(Type.Type_String, type1))
                    __throwError("Cannot subtract strings", eo.getLine(), eo.getCharPositionInLine());
                if (__isSubtype(Type.Type_Boolean, type1))
                    __throwError("Cannot subtract booleans", eo.getLine(), eo.getCharPositionInLine());
                break;
			case Operator_Less_Than:
				return Type.Type_Boolean;
			case Operator_Equals:
				return Type.Type_Boolean;
			default:
				break;
		}
        return type1;
    }

    public Object visit(LiteralBoolean literal_boolean)
    {
        return Type.Type_Boolean;
    }

    public Object visit(LiteralCharacter literal_character)
    {
        return Type.Type_Char;
    }

    public Object visit(LiteralFloat literal_float)
    {
        return Type.Type_Float;
    }

    public Object visit(LiteralInteger literal_integer)
    {
        return Type.Type_Int;
    }

    public Object visit(LiteralString literal_string)
    {
        return Type.Type_String;
    }
}