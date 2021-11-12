package visitor.ast;

import visitor.Visitor;

public abstract class Statement extends AbstractSyntaxTreeNode
{
	public Statement(int line, int pos, int index)
	{
		super(line, pos, index);
	}
	
	public abstract Object accept(Visitor v);
}

