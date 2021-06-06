package ast;
import java.util.Vector;
import java.lang.String;


public class PrettyPrintVisitor extends Visitor
{
	static final String TAB = "    ";
	static final String NEWLINE = "\n";
	private String toStringStatements(Vector<Statement> statements)
	{
		String sStatements = "";
		for (int i = 0; i < statements.size(); ++i)
		{
			// add a TAB to each line of the statement string
			String sStatement = statements.elementAt(i).accept(this).toString();
			sStatement = TAB + sStatement;
			sStatement.replaceAll("\n", ("\n" + TAB));

			sStatements += sStatement;
			if (i < statements.size() - 1)
			{
				sStatements += NEWLINE;
			}
		}
		return sStatements;
	}
	private String toStringExprParen(String sExpression, int num_parentheses)
	{
		for (int i = 0; i < num_parentheses; ++i)
		{
			sExpression = "(" + sExpression + ")";
		}
		return sExpression;
	}

	public Object visit(Program program)
	{
		String sProgram = "";

		Vector<Function> functions = program.getFunctions();
		for (Function function : functions)
		{
			sProgram += function.accept(this).toString();
			sProgram += "\n\n";
		}

		return sProgram;
	}

	public Object visit(Function function)
	{
		String sFunctionDeclaration = function.get_function_declaration().accept(this).toString();
		String sFunctionBody = function.get_function_body().accept(this).toString();
		return sFunctionDeclaration + "\n" + sFunctionBody;
	}

	public Object visit(FunctionDeclaration function_declaration)
	{
		String sCompoundType = function_declaration.get_compound_type().accept(this).toString();
		String sIdentifier = function_declaration.get_identifier().accept(this).toString();
		String sParameters = "";

		Vector<Variable> parameters = function_declaration.get_parameters();
		for (int i = 0; i < parameters.size(); ++i)
		{
			sParameters += parameters.elementAt(i).accept(this).toString();
			if (i < parameters.size() - 1)
			{
				sParameters += ", ";
			}
		}

		return (sCompoundType + " " + sIdentifier + " (" + sParameters + ")");
	}

	public Object visit(FunctionBody function_body)
	{
		String sVariables = "";
		String sStatements = toStringStatements(function_body.get_statements());

		Vector<Variable> variables = function_body.get_variables();
		for (int i = 0; i < variables.size(); ++i)
		{
			sVariables += TAB;
			sVariables += variables.elementAt(i).accept(this).toString();
			sVariables += ";";
			if (i < variables.size() - 1)
			{
				sVariables += NEWLINE;
			}
		}
		
		boolean is_variables = variables.size() > 0;
		boolean is_statements = !sStatements.isEmpty();
		if (is_variables && is_statements)
		{
			sVariables += "\n\n";
			sVariables += sStatements;
		}
		else if (is_statements) // && !is_variables
		{
			sVariables = sStatements;
		}
		return "{\n" + sVariables + "\n}";
	}

	public Object visit(CompoundType compound_type)
	{
		String sTypeNode = compound_type.get_type_node().accept(this).toString();
		String sArray = "";
		
		if (compound_type.is_array())
		{
			sArray = "[" + compound_type.toString() + "]";
		}

		return sTypeNode + sArray;
	}

	public Object visit(TypeNode type_node)
	{
		switch (type_node.get_type())
		{
			case Type_Int:
				return "int";
			case Type_Float:
				return "float";
			case Type_Char:
				return "char";
			case Type_String:
				return "string";
			case Type_Boolean:
				return "boolean";
			case Type_Void:
				return "void";
			default:
				break;
		}
		return "?";
	}

	public Object visit(Identifier identifier)
	{
		return identifier.get_name();
	}

	public Object visit(Variable variable)
	{
		String sCompoundType = variable.get_compound_type().accept(this).toString();
		String sIdentifier = variable.get_identifier().accept(this).toString();
		return sCompoundType + " " + sIdentifier;
	}

	public Object visit(Block block)
	{
		return "{\n" + toStringStatements(block.get_statements()) + "\n}";
	}


	public Object visit(StatementAssignment statement_assignment)
	{
		// id (BRACKET_LEFT expr BRACKET_RIGHT)? ASSIGN expr SEMICOLON
		String sIdentifier = statement_assignment.get_identifier().accept(this).toString();
		String sArrayIndex = "";
		String sExpression = statement_assignment.get_expression().accept(this).toString();

		if (statement_assignment.is_array())
		{
			sArrayIndex += "[";
			sArrayIndex += statement_assignment.get_array_index().accept(this).toString();
			sArrayIndex += "]";
		}

		return sIdentifier + sArrayIndex + "=" + sExpression + ";";
	}

	public Object visit(StatementEmpty statement_empty)
	{
		return ";";
	}

	public Object visit(StatementExpression statement_expression)
	{
		return statement_expression.get_expression().accept(this).toString() + ";";
	}

	public Object visit(StatementIfElse statement_if_else)
	{
		String sExpression = "if (";
		sExpression += statement_if_else.get_expression().accept(this).toString();
		sExpression += ")\n";

		String sBlock1 = statement_if_else.get_block1().accept(this).toString();
		String sBlock2 = "";
		if (statement_if_else.is_else())
		{
			sBlock2 = statement_if_else.get_block2().accept(this).toString();
			return sExpression + sBlock1 + "\nelse\n" + sBlock2;
		}
		return sExpression + sBlock1;
	}

	public Object visit(StatementPrint statement_print)
	{
		// (PRINT | PRINTLN) expr SEMICOLON
		String sExpression = statement_print.get_expression().accept(this).toString();
		String sPrint = "print";

		if (statement_print.is_newline())
		{
			sPrint += "ln";
		}

		return sPrint + " " + sExpression + ";";
	}

	public Object visit(StatementReturn statement_return)
	{
		// RETURN expr? SEMICOLON
		String sExpression = "";

		if (statement_return.is_return_value())
		{
			sExpression += " ";
			sExpression += statement_return.get_expression().accept(this).toString();
		}

		return "return" + sExpression + ";";
	}

	public Object visit(StatementWhile statement_while)
	{
		// WHILE PAREN_LEFT expr PAREN_RIGHT block
		String sExpression = statement_while.get_expression().accept(this).toString();
		String sBlock = statement_while.get_block().accept(this).toString();

		return "while (" + sExpression + ")\n" + sBlock;
	}


	public Object visit(ExpressionFunction expression_function)
	{
		// id PAREN_LEFT exprList? PAREN_RIGHT
		// foo(a, b+c)
		String sIdentifier = expression_function.get_identifier().accept(this).toString();
		String sParameters = "";

		if (expression_function.is_parameters())
		{
			Vector<Expression> parameters = expression_function.get_parameters();
			for (int i = 0; i < parameters.size(); ++i)
			{
				sParameters += parameters.elementAt(i).accept(this).toString();
				if (i < parameters.size() - 1)
				{
					sParameters += ",";
				}
			}
		}

		String sExpression = sIdentifier + "(" + sParameters + ")";
		return toStringExprParen(sExpression, expression_function.get_num_parentheses());
	}

	public Object visit(ExpressionIdentifier expression_identifier)
	{
		// id (BRACKET_LEFT expr BRACKET_RIGHT)?
		String sIdentifier = expression_identifier.get_identifier().accept(this).toString();
		String sArrayIndex = "";
		
		if (expression_identifier.is_array())
		{
			sArrayIndex += "[";
			sArrayIndex += expression_identifier.get_array_index().accept(this).toString();
			sArrayIndex += "]";
		}

		String sExpression = sIdentifier + sArrayIndex;
		return toStringExprParen(sExpression, expression_identifier.get_num_parentheses());
	}

	public Object visit(ExpressionOperation expression_operation)
	{
		String sOperator = "";
		String sExpression1 = expression_operation.get_expression1().accept(this).toString();
		String sExpression2 = expression_operation.get_expression2().accept(this).toString();

		switch (expression_operation.get_operator())
		{
			case Operator_Multiply:
				sOperator = "*";
				break;
			case Operator_Subtraction:
				sOperator = "-";
				break;
			case Operator_Addition:
				sOperator = "+";
				break;
			case Operator_Less_Than:
				sOperator = "<";
				break;
			case Operator_Equals:
				sOperator = "==";
				break;
			default:
				break;
		}

		String sExpression = sExpression1 + sOperator + sExpression2;
		return toStringExprParen(sExpression, expression_operation.get_num_parentheses());
	}
    

	public Object visit(LiteralBoolean literal_boolean)
	{
		return String.valueOf(literal_boolean.get_value());
	}

	public Object visit(LiteralCharacter literal_character)
	{
		return String.valueOf(literal_character.get_value());
	}

	public Object visit(LiteralFloat literal_float)
	{
		return String.valueOf(literal_float.get_value());
	}

	public Object visit(LiteralInteger literal_integer)
	{
		return String.valueOf(literal_integer.get_value());
	}

	public Object visit(LiteralString literal_string)
	{
		return String.valueOf(literal_string.get_value());
	}
}
