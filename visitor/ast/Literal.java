package visitor.ast;

import visitor.Visitor;

public abstract class Literal extends Expression
{
	public Literal(int line, int pos, int index)
	{
		super(line, pos, index);
	}
	
	public abstract Type getType();
	public abstract Object accept(Visitor v);
}

