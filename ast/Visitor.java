package ast;
public abstract class Visitor
{
	public abstract Object visit(Program program);
	public abstract Object visit(Function function);
	public abstract Object visit(FunctionDeclaration function_declaration);
	public abstract Object visit(FunctionBody function_body);
	public abstract Object visit(Block block);
	public abstract Object visit(CompoundType compound_type);
	public abstract Object visit(TypeNode type_node);
	public abstract Object visit(Identifier identifier);
	public abstract Object visit(Variable variable);
	
	public abstract Object visit(StatementAssignment statement_assignment);
	public abstract Object visit(StatementEmpty statement_empty);
	public abstract Object visit(StatementExpression statement_expression);
	public abstract Object visit(StatementIfElse statement_if_else);
	public abstract Object visit(StatementPrint statement_print);
	public abstract Object visit(StatementReturn statement_return);
	public abstract Object visit(StatementWhile statement_while);

	public abstract Object visit(ExpressionFunction expression_function);
	public abstract Object visit(ExpressionIdentifier expression_identifier);
	public abstract Object visit(ExpressionOperation expression_operation);

	public abstract Object visit(LiteralBoolean literal_boolean);
	public abstract Object visit(LiteralCharacter literal_character);
	public abstract Object visit(LiteralFloat literal_float);
	public abstract Object visit(LiteralInteger literal_integer);
	public abstract Object visit(LiteralString literal_string);
}