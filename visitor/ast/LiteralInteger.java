package visitor.ast;

import visitor.Visitor;

public class LiteralInteger extends Literal 
{
    private int m_int;

    public LiteralInteger(int line, int pos, int index, int i)
    {
        super(line, pos, index);
        m_int = i;
    }

    public int getValue()
    {
        return m_int;
    }

    public Type getType()
    {
        return Type.Type_Int;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }

    public String toString()
    {
        return String.valueOf(m_int);
    }
}