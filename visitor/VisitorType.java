package visitor;

import java.util.Vector;

import visitor.ast.Block;
import visitor.ast.CompoundType;
import visitor.ast.Expression;
import visitor.ast.ExpressionFunction;
import visitor.ast.ExpressionIdentifier;
import visitor.ast.ExpressionOperation;
import visitor.ast.Function;
import visitor.ast.FunctionBody;
import visitor.ast.FunctionDeclaration;
import visitor.ast.Identifier;
import visitor.ast.LiteralBoolean;
import visitor.ast.LiteralCharacter;
import visitor.ast.LiteralFloat;
import visitor.ast.LiteralInteger;
import visitor.ast.LiteralString;
import visitor.ast.Program;
import visitor.ast.Statement;
import visitor.ast.StatementAssignment;
import visitor.ast.StatementEmpty;
import visitor.ast.StatementExpression;
import visitor.ast.StatementIfElse;
import visitor.ast.StatementPrint;
import visitor.ast.StatementReturn;
import visitor.ast.StatementWhile;
import visitor.ast.Type;
import visitor.ast.TypeNode;
import visitor.ast.Variable;

import java.lang.String;

public class VisitorType extends Visitor
{
    /* PRIVATE */
    
    private Environment<ElementFunction> m_func_env;
    private Environment<Element> m_var_env;
    private ElementFunction m_func_curr;

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
        throw new SemanticException(result, m_func_env, m_var_env, m_func_curr);
    }

    private boolean __isMain(ElementFunction ef, int lineNumber, int lineOffset)
    {
        if (!ef.getName().equals("main"))
        {
            return false; // function is not called 'main'
        }
        if (ef.getParameters().size() > 0)
        {
            String errorMsg = "The main() function should have no parameters";
            __throwError(errorMsg, lineNumber, lineOffset);
        }
        if (!__isSubtype(Type.Type_Void, ef.getType()))
        {
            String errorMsg = "The return type of the main() function should be void";
            __throwError(errorMsg, lineNumber, lineOffset);
        }
        return true;
    }

    /* PUBLIC */

    public VisitorType()
    {
        m_func_env = new Environment<ElementFunction>();
        m_var_env = new Environment<Element>();
        m_func_curr = null;
    }

    // returns null
    public Object visit(Program program)
    {
        boolean hasMain = false;

        // go through function declarations
        // and populate the function environement
        for (Function function : program.getFunctions())
        {
            FunctionDeclaration fd = function.getFunctionDeclaration();
            ElementFunction ef = (ElementFunction) fd.accept(this);
            m_func_env.add(ef.getName(), ef);

            // check whether the function is main
            hasMain = (hasMain || __isMain(ef, fd.getLine(), fd.getCharPositionInLine()));
        }

        // check that main is one of the functions
        if (!hasMain)
            __throwError("The program must have a main() function", program.getLine(), program.getCharPositionInLine());

        // go through function bodies
        // and type-check statements
        for (Function function : program.getFunctions())
        {
            __enscope();
            function.accept(this);
            __descope();
        }
        return null;
    }

    // returns null
	public Object visit(Function function)
    {
        // add function parameters to the variable environment
        String name = function.getFunctionDeclaration().getIdentifier().getName();
        m_func_curr = m_func_env.find(name);

        for (Element e : m_func_curr.getParameters())
            m_var_env.add(e.getName(), e);

        function.getFunctionBody().accept(this);
        return null;
    }

    // returns an ElementFunction
    public Object visit(FunctionDeclaration func_decl)
    {
        // check that the function is not already in the environment
        String name = func_decl.getIdentifier().getName();
        if (m_func_env.find(name) != null)
            __throwError("Functions with the same name are not allowed", func_decl.getLine(), func_decl.getCharPositionInLine());

        Pair<Type, Integer> pair = (Pair<Type, Integer>) func_decl.getCompoundType().accept(this);
        Type type = pair.getFirst();
        int array_size = pair.getSecond();
    
        // create the ElementFunction
        ElementFunction ef = new ElementFunction(type, array_size, name);
        __enscope();
        for (Variable var : func_decl.getParameters())
        {
            Element e = (Element) var.accept(this);
            m_var_env.add(e.getName(), e);
            ef.addParameter(e);
        }
        __descope();
        return ef;
    }

    // returns null
    public Object visit(FunctionBody function_body)
    {
        // add variable declarations to variable environment
        for (Variable var : function_body.getVariables())
        {
            Element e = (Element) var.accept(this);
            m_var_env.add(e.getName(), e);
        }

        // type-check statements
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
        if (__isSubtype(Type.Type_Void, type) && ct.isArray())
            __throwError("Arrays cannot be of type 'void'", ct.getLine(), ct.getCharPositionInLine());

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
        Pair<Type, Integer> pair = (Pair<Type, Integer>) var.getCompoundType().accept(this);
        Type type = pair.getFirst();
        int array_size = pair.getSecond();

        // check that the variable is not of type Void
        if (__isSubtype(Type.Type_Void, type))
            __throwError("Variables of type 'void' are not allowed", var.getLine(), var.getCharPositionInLine());

        // check that the variable is not already in the environment
        String name = var.getIdentifier().accept(this).toString();
        if (m_var_env.find(name) != null)
            __throwError("Re-declaration of variables is not allowed", var.getLine(), var.getCharPositionInLine());
        
        // create and return the Element
        return new Element(type, array_size, name);
    }


    // ALL STATEMENTS RETURN NULL
    public Object visit(StatementAssignment sa)
    {
        /*
        Identifier m_identifier;
        Expression m_array_index; // if null, then "id = expr", else "id[index] = expr"
        Expression m_expression;
        */
        String name = sa.getIdentifier().accept(this).toString();
        Element e = m_var_env.find(name);

        // check that the variable to be assigned is in the environment
        if (e == null)
            __throwError("Variable to be assigned is not declared", sa.getLine(), sa.getCharPositionInLine());

        // check if the variable is an array but the assignment does not assign an index
        if (e.isArray() && !sa.isArray())
            __throwError("Must assign the value to some index of the array variable", sa.getLine(), sa.getCharPositionInLine());

        // check if the assignment tries to index an non-array
        if (!e.isArray() && sa.isArray())
            __throwError("Cannot index a non-array variable", sa.getLine(), sa.getCharPositionInLine());

        // check if the indexing uses an int
        if (sa.isArray())
        {
            Type type_index = (Type) sa.getArrayIndex().accept(this);
            if (!__isSubtype(Type.Type_Int, type_index))
                __throwError("Cannot index array with non-Integer", sa.getLine(), sa.getCharPositionInLine());
        }

        Type type = (Type) sa.getExpression().accept(this);

        // check that correct type is being assigned to the variable
        if (!__isSubtype(e.getType(), type))
            __throwError("Type of assignment expression does not match variable", sa.getLine(), sa.getCharPositionInLine());

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
        Type type = (Type) sie.getExpression().accept(this);

        // check that the conditional is a boolean
        if (!__isSubtype(Type.Type_Boolean, type))
            __throwError("If-Else conditional expression is not of type 'boolean'", sie.getLine(), sie.getCharPositionInLine());

        // type-check the blocks
        sie.getBlockFirst().accept(this);
        if (sie.hasElse())
            sie.getBlockSecond().accept(this);
        return null;
    }

    public Object visit(StatementPrint sp)
    {
        // type valid for int, float, char, string, or boolean
        Type type = (Type) sp.getExpression().accept(this);
        if (__isSubtype(Type.Type_Void, type))
            __throwError("Cannot print type 'void'", sp.getLine(), sp.getCharPositionInLine());
        return null;
    }

    public Object visit(StatementReturn sr)
    {
        if (sr.hasExpression())
        {
            if (__isSubtype(Type.Type_Void, m_func_curr.getType()))
                __throwError("Return expression should not return anything", sr.getLine(), sr.getCharPositionInLine());

            Type type = (Type) sr.getExpression().accept(this);

            if (!__isSubtype(m_func_curr.getType(), type))
                __throwError("Type of return expression does not match the function's return type", sr.getLine(), sr.getCharPositionInLine());
        }

        else if (!__isSubtype(Type.Type_Void, m_func_curr.getType()))
            __throwError("Return expression should return something", sr.getLine(), sr.getCharPositionInLine());

        return null;
    }

    public Object visit(StatementWhile sw)
    {
        Type type = (Type) sw.getExpression().accept(this);

        // check that the conditional is a boolean
        if (!__isSubtype(Type.Type_Boolean, type))
            __throwError("While conditional expression is not of type 'boolean'", sw.getLine(), sw.getCharPositionInLine());

        // type-check the blocks
        sw.getBlock().accept(this);
        return null;
    }


    // ALL EXPRESSIONS (AND LITERALS) RETURN TYPE
    public Object visit(ExpressionFunction exf)
    {
        String name = exf.getIdentifier().accept(this).toString();
        ElementFunction ef = m_func_env.find(name);

        // check the function exists
        if (ef == null)
            __throwError("Function to be called does not exist", exf.getLine(), exf.getCharPositionInLine());

        Vector<Element> parameters = ef.getParameters();
        Vector<Expression> expressions = exf.getParameters();

        // check there are as many expressions as parameters
        if (parameters.size() != expressions.size())
            __throwError("Incorrect number of parameters in function call", exf.getLine(), exf.getCharPositionInLine());

        // check each expression matches the type of the parameter
        for (int i = 0; i < parameters.size(); ++i)
        {
            Element var = parameters.elementAt(i);
            Expression exp = expressions.elementAt(i);
            Type type = (Type) exp.accept(this);

            if (!__isSubtype(var.getType(), type))
                __throwError("Type of expression does not match the function parameter", exp.getLine(), exp.getCharPositionInLine());
        }

        return ef.getType();
    }

    public Object visit(ExpressionIdentifier ei)
    {
        String name = ei.getIdentifier().accept(this).toString();
        Element ef = m_var_env.find(name);

        // check the variable exists
        if (ef == null)
            __throwError("Variable is not declared", ei.getLine(), ei.getCharPositionInLine());

        // check if the variable is an array but the expression does not index
        if (ef.isArray() && !ei.isArray())
            __throwError("Type 'array' does not exist; Please index the array", ei.getLine(), ei.getCharPositionInLine());

        // check if the assignment tries to index an non-array
        if (!ef.isArray() && ei.isArray())
            __throwError("Cannot index a non-array variable", ei.getLine(), ei.getCharPositionInLine());

        // check that the index of the array is an int
        if (ei.isArray())
        {
            Type type_index = (Type) ei.getArrayIndex().accept(this);
            if (!__isSubtype(Type.Type_Int, type_index))
                __throwError("Cannot index array with non-Integer", ei.getLine(), ei.getCharPositionInLine());
        }

        return ef.getType();
    }

    public Object visit(ExpressionOperation eo)
    {
        Type type1 = (Type) eo.getExpressionFirst().accept(this);
        Type type2 = (Type) eo.getExpressionSecond().accept(this);

        // check that both sides of the operation are of the same type
        boolean isSubtype1 = __isSubtype(type1, type2);
        boolean isSubtype2 = __isSubtype(type2, type1);
        if (!isSubtype1 || !isSubtype2)
            __throwError("Cannot operate on values of different types", eo.getLine(), eo.getCharPositionInLine());

        // check the operation is not on void
        if (__isSubtype(Type.Type_Void, type1))
            __throwError("Cannot operate on values of type 'void'", eo.getLine(), eo.getCharPositionInLine());

		switch (eo.getOperator())
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