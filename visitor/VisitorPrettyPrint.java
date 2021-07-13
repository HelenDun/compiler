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
import visitor.ast.TypeNode;
import visitor.ast.Variable;

import java.lang.String;

public class VisitorPrettyPrint extends Visitor
{
	/* PRIVATE */

	private static final String TAB = "    ";
	private static final String NEWLINE = "\n";

	private String __toStringStatements(Vector<Statement> statements)
	{
		String sStatements = "";
		for (int i = 0; i < statements.size(); ++i)
		{
			// get the statement as a String
			Statement statement = statements.elementAt(i);
			String sStatement = statement.accept(this).toString();

			// add a TAB to each line of the statement string
			sStatement = TAB + sStatement;
			sStatement = sStatement.replaceAll(NEWLINE, NEWLINE + TAB);

			// add the statement to the list of statements
			sStatements += sStatement;
		}

		// remove any whitespace at the end of the string
		sStatements.stripTrailing();
		return sStatements;
	}
	
	private String __toStringExprParen(String sExpression, int num_parentheses)
	{
		for (int i = 0; i < num_parentheses; ++i)
		{
			sExpression = "(" + sExpression + ")";
		}
		return sExpression;
	}

	/* PUBLIC */

	public VisitorPrettyPrint()
	{
	}

	public Object visit(Program program)
	{
		String sProgram = "";
		Vector<Function> functions = program.getFunctions();
		for (int i = 0; i < functions.size(); ++i)
		{
			sProgram += functions.elementAt(i).accept(this).toString();
			if (i < functions.size() - 1)
			{
				sProgram += "\n\n";
			}
		}
		return sProgram;
	}

	public Object visit(Function function)
	{
		String sFunctionDeclaration = function.getFunctionDeclaration().accept(this).toString();
		String sFunctionBody = function.getFunctionBody().accept(this).toString();
		return sFunctionDeclaration + "\n" + sFunctionBody;
	}

	public Object visit(FunctionDeclaration function_declaration)
	{
		String sCompoundType = function_declaration.getCompoundType().accept(this).toString();
		String sIdentifier = function_declaration.getIdentifier().accept(this).toString();
		String sParameters = "";

		Vector<Variable> parameters = function_declaration.getParameters();
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
		String sStatements = __toStringStatements(function_body.getStatements());

		Vector<Variable> variables = function_body.getVariables();
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
		String sTypeNode = compound_type.getTypeNode().accept(this).toString();
		String sArray = "";
		
		if (compound_type.isArray())
		{
			sArray = "[" + String.valueOf(compound_type.getArraySize()) + "]";
		}

		return sTypeNode + sArray;
	}

	public Object visit(TypeNode type_node)
	{
		return type_node.getType().toString();
	}

	public Object visit(Identifier identifier)
	{
		return identifier.getName();
	}

	public Object visit(Variable variable)
	{
		String sCompoundType = variable.getCompoundType().accept(this).toString();
		String sIdentifier = variable.getIdentifier().accept(this).toString();
		return sCompoundType + " " + sIdentifier;
	}

	public Object visit(Block block)
	{
		return "{\n" + __toStringStatements(block.getStatements()) + "\n}";
	}


	public Object visit(StatementAssignment statement_assignment)
	{
		// id (BRACKET_LEFT expr BRACKET_RIGHT)? ASSIGN expr SEMICOLON
		String sIdentifier = statement_assignment.getIdentifier().accept(this).toString();
		String sArrayIndex = "";
		String sExpression = statement_assignment.getExpression().accept(this).toString();

		if (statement_assignment.isArray())
		{
			sArrayIndex += "[";
			sArrayIndex += statement_assignment.getArrayIndex().accept(this).toString();
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
		return statement_expression.getExpression().accept(this).toString() + ";";
	}

	public Object visit(StatementIfElse statement_if_else)
	{
		String sExpression = "if (";
		sExpression += statement_if_else.getExpression().accept(this).toString();
		sExpression += ")\n";

		String sBlock1 = statement_if_else.getBlockFirst().accept(this).toString();
		String sBlock2 = "";
		if (statement_if_else.hasElse())
		{
			sBlock2 = statement_if_else.getBlockSecond().accept(this).toString();
			return sExpression + sBlock1 + "\nelse\n" + sBlock2;
		}
		return sExpression + sBlock1;
	}

	public Object visit(StatementPrint statement_print)
	{
		// (PRINT | PRINTLN) expr SEMICOLON
		String sExpression = statement_print.getExpression().accept(this).toString();
		String sPrint = "print";

		if (statement_print.isNewline())
		{
			sPrint += "ln";
		}

		return sPrint + " " + sExpression + ";";
	}

	public Object visit(StatementReturn statement_return)
	{
		// RETURN expr? SEMICOLON
		String sExpression = "";

		if (statement_return.hasExpression())
		{
			sExpression += " ";
			sExpression += statement_return.getExpression().accept(this).toString();
		}

		return "return" + sExpression + ";";
	}

	public Object visit(StatementWhile statement_while)
	{
		// WHILE PAREN_LEFT expr PAREN_RIGHT block
		String sExpression = statement_while.getExpression().accept(this).toString();
		String sBlock = statement_while.getBlock().accept(this).toString();

		return "while (" + sExpression + ")\n" + sBlock;
	}


	public Object visit(ExpressionFunction expression_function)
	{
		// id PAREN_LEFT exprList? PAREN_RIGHT
		// foo(a, b+c)
		String sIdentifier = expression_function.getIdentifier().accept(this).toString();
		String sParameters = "";

		Vector<Expression> parameters = expression_function.getParameters();
		for (int i = 0; i < parameters.size(); ++i)
		{
			sParameters += parameters.elementAt(i).accept(this).toString();
			if (i < parameters.size() - 1)
			{
				sParameters += ",";
			}
		}

		String sExpression = sIdentifier + "(" + sParameters + ")";
		return __toStringExprParen(sExpression, expression_function.getParentheses());
	}

	public Object visit(ExpressionIdentifier expression_identifier)
	{
		// id (BRACKET_LEFT expr BRACKET_RIGHT)?
		String sIdentifier = expression_identifier.getIdentifier().accept(this).toString();
		String sArrayIndex = "";
		
		if (expression_identifier.isArray())
		{
			sArrayIndex += "[";
			sArrayIndex += expression_identifier.getArrayIndex().accept(this).toString();
			sArrayIndex += "]";
		}

		String sExpression = sIdentifier + sArrayIndex;
		return __toStringExprParen(sExpression, expression_identifier.getParentheses());
	}

	public Object visit(ExpressionOperation expression_operation)
	{
		String sOperator = expression_operation.getOperator().toString();
		String sExpression1 = expression_operation.getExpressionFirst().accept(this).toString();
		String sExpression2 = expression_operation.getExpressionSecond().accept(this).toString();
		String sExpression = sExpression1 + sOperator + sExpression2;
		return __toStringExprParen(sExpression, expression_operation.getParentheses());
	}
    

	public Object visit(LiteralBoolean literal_boolean)
	{
		return String.valueOf(literal_boolean.getValue());
	}

	public Object visit(LiteralCharacter literal_character)
	{
		return '\'' + String.valueOf(literal_character.getValue()) + '\'';
	}

	public Object visit(LiteralFloat literal_float)
	{
		return String.valueOf(literal_float.get_value());
	}

	public Object visit(LiteralInteger literal_integer)
	{
		return String.valueOf(literal_integer.getValue());
	}

	public Object visit(LiteralString literal_string)
	{
		return String.valueOf(literal_string.getValue());
	}
}
