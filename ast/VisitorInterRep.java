package ast;
import java.util.Vector;
import java.lang.String;

public class VisitorInterRep extends Visitor
{
    /* PRIVATE */
    Environment<Element> m_environment;
    String m_declarations;
    String m_lines;
    String m_full;
    int m_register_counter;

    private int __addDeclaration(String type, String name, boolean isParameter)
    {
        m_declarations += "TEMP ";
        m_declarations += m_register_counter;
        m_register_counter += 1;

        m_declarations += ':';
        m_declarations += type;

        if (name != null)
        {
            m_declarations += " ["
            if (isParameter)
            {
                m_declarations += 'P';
            }
            else
            {
                m_declarations += 'L';
            }
            m_declarations += "(\"";
            m_declarations += name;
            m_declarations += "\")]";
        }
        m_declarations += ";\n";
        return m_register_counter - 1;
    }

    private void __addAssignment(int register, String right)
    {
        m_lines += 'T';
        m_lines += String.valueOf(register);
        m_lines += " := ";
        m_lines += right;
        m_lines += ";\n";
        return;
    }

    private String __typeToString(Type t)
    {
        switch (t)
        {
            case Type.Type_Boolean:
                return 'Z';
            case Type.Type_Char:
                return 'C';
            case Type.Type_Float:
                return 'F';
            case Type.Type_Int:
                return 'I';
            case Type.Type_String:
                return 'U';
            case Type.Type_Void:
                return 'V';
            default:
                throw RuntimeException("Something went wrong with Type Enum");
        }
        return null;
    }

    private String __operatorToString(Operator o)
    {
        switch (o)
        {
            case Operator.Operator_Multiply:
                return '*';
            case Operator.Operator_Addition:
                return '+';
            case Operator.Operator_Subtraction:
                return '-';
            case Operator.Operator_Less_Than:
                return '<';
            case Operator.Operator_Equals:
                return "==";
            default:
                throw RuntimeException("Something went wrong with Operator Enum");
        }
        return null;
    }

    /* PUBLIC */

    public VisitorInterRep(String programName)
    {
        m_full = "PROG ";
        m_full += programName;
        m_full += '\n';
    }

    public Object visit(Program program)
    {
        for (Function func : program.getFunctions())
        {
            FunctionDeclaration func_dec = func.get_function_declaration();
            Type type;
            int array_size;
            String name;

            Element e = new Element(type, array_size, name);
            m_environment.add()
        }
        for (Function func : program.getFunctions())
        {
            func.accept(this);
        }
        return m_full;
    }

    public Object visit(Function function)
    {
        m_declarations = "";
        m_lines = "";
        m_register_counter = 0;
        m_environment.enscope();

        function.get_function_declaration().accept(this);
        function.get_function_body().accept(this);

        m_full += m_declarations;
        m_full += '\n';
        m_full += m_lines;
        m_environment.descope();
        return null;
    }

    public Object visit(FunctionDeclaration function_declaration)
    {
        m_full += "FUNC ";
        m_full += functionName;
        m_full += '(';
        for (Variable parameter : function_declaration.get_parameters())
        {
            String type = parameter.get_compound_type().accept(this).toString();
            String name = parameter.get_identifier().accept(this).toString();
            int register = __addDeclaration(type, name, true);
            m_environment.add(name, register);
            m_full += type;
        }
        m_full += ')';
        m_full += function_declaration.get_compound_type().accept(this).toString();
        m_full += '\n';
        return null;
    }

    public Object visit(FunctionBody function_body)
    {
        for (Variable var : function_body.get_variables())
        {
            var.accept(this);
        }
        for (Statement state : function_body.get_statements())
        {
            state.accept(this);
        }
        return null
    }

    // UNFINISHED
    public Object visit(Block block)
    {
        return null;
    }

    public Object visit(CompoundType compound_type)
    {
        String t = "";
        if (compound_type.is_array())
            t += 'A';
        t += compound_type.get_type_node().accept(this).toString();
        return t;
    }

    public Object visit(TypeNode type_node)
    {
        return __typeToString(type_node.get_type());
    }

    public Object visit(Identifier identifier)
    {
        return identifier.get_name();
    }

    public Object visit(Variable variable)
    {
        String type = variable.get_compound_type().accept(this).toString();
        String name = variable.get_identifier().accept(this).toString();
        int register = __addDeclaration(type, name, false);
        m_environment.add(name, register);
        return null;
    }

    public Object visit(StatementAssignment statement_assignment)
    {
        /*
        Identifier m_identifier;
        Expression m_array_index; // if null, then "id = expr", else "id[index] = expr"
        Expression m_expression;
        */
        int temp_register = (int) statement_assignment.get_expression.accept(this);
    }

    public Object visit(StatementEmpty statement_empty)
    {
        return null;
    }

    /*
	public Object visit(StatementExpression statement_expression);
	public Object visit(StatementIfElse statement_if_else);
	public Object visit(StatementPrint statement_print);
	public Object visit(StatementReturn statement_return);
	public Object visit(StatementWhile statement_while);

	public Object visit(ExpressionFunction expression_function);
	public Object visit(ExpressionIdentifier expression_identifier);

    */

	public Object visit(ExpressionIdentifier expression_identifier)
    {
        // get type
        VisitorType visit_type = new VisitorType();
        String type = __typeToString(expression_operation.get_expression1().accept(visit_type));

        int register = __addDeclaration('Z', null, false);
        String value = "FALSE";
        if (literal_boolean.get_value())
        {
            value = "TRUE";
        }
        __addAssignment(register, value);
        return register;
    }

	public Object visit(ExpressionOperation expression_operation)
    {
        int register1 = (int) expression_operation.get_expression1().accept(this);
        int register2 = (int) expression_operation.get_expression2().accept(this);

        // get type
        VisitorType visit_type = new VisitorType();
        String type = __typeToString(expression_operation.get_expression1().accept(visit_type));

        String value = 'T' + String.valueOf(register1);
        value += ' ' + type + __operatorToString(expression_operation.get_operator()) + ' ';
        value += 'T' + String.valueOf(register2);
        __addAssignment(register1, value);
        return register1;
    }

	public Object visit(LiteralBoolean literal_boolean)
    {
        int register = __addDeclaration('Z', null, false);
        String value = "FALSE";
        if (literal_boolean.get_value())
        {
            value = "TRUE";
        }
        __addAssignment(register, value);
        return register;
    }

	public Object visit(LiteralCharacter literal_character)
    {
        int register = __addDeclaration('C', null, false);
        String value = '\'' + literal_character.get_value() + '\'';
        __addAssignment(register, value);
        return register;
    }

	public Object visit(LiteralFloat literal_float)
    {
        int register = __addDeclaration('F', null, false);
        String value = String.valueOf(literal_float.get_value());
        __addAssignment(register, value);
        return register;
    }

	public Object visit(LiteralInteger literal_integer)
    {
        int register = __addDeclaration('I', null, false);
        String value = String.valueOf(literal_integer.get_value());
        __addAssignment(register, value);
        return register;
    }

    public Object visit(LiteralString literal_string)
    {
        int register = __addDeclaration('U', null, false);
        String value = '"' + literal_string.get_value() + '"';
        __addAssignment(register, value);
        return register;
    }
}