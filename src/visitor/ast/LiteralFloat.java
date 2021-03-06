package visitor.ast;

import visitor.Visitor;

public class LiteralFloat extends Literal 
{
    private float m_float;

    public LiteralFloat(int line, int pos, int index, float f)
    {
        super(line, pos, index);
        m_float = f;
    }

    public Object getValue()
    {
        return m_float;
    }

    public Type getType()
    {
        return Type.Type_Float;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }

    public String toString()
    {
        return String.valueOf(m_float);
    }
}