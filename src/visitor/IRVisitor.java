package visitor;

import visitor.ir.*;

public abstract class IRVisitor 
{
	public abstract Object visit(IRProgram program);
	public abstract Object visit(IRFunction function);
	public abstract Object visit(IRDeclaration declaration);

	public abstract Object visit(IRStatementGoto statement_goto);
	public abstract Object visit(IRStatementLabel statement_label);
	public abstract Object visit(IRStatementPrint statement_print);
	public abstract Object visit(IRStatementReturn statement_return);

	public abstract Object visit(IRAssignmentArray assignment_array);
	public abstract Object visit(IRAssignmentCall assignment_call);
	public abstract Object visit(IRAssignmentConstant assignment_constant);
	public abstract Object visit(IRAssignmentOperation assignment_operation);
	public abstract Object visit(IRAssignmentRegister assignment_register);
}
