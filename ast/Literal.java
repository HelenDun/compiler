package ast;

public abstract class Literal extends Expression
{
	public Literal(int line, int pos, int index)
	{
		super(line, pos, index);
	}
	//public abstract void* get_value();
	public abstract Object accept(Visitor v);
}

