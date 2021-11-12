package visitor.ast;

import visitor.Visitor;

public abstract class Literal extends Expression
{
	public Literal(int line, int pos, int index)
	{
		super(line, pos, index);
	}
	
	public abstract String toString();
	public abstract Type getType();
	public abstract Object getValue();
	public abstract Object accept(Visitor v);
}

