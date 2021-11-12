package visitor.ast;

import visitor.Visitor;

public class LiteralBoolean extends Literal 
{
    private boolean m_boolean;

    public LiteralBoolean(int line, int pos, int index, boolean b)
    {
        super(line, pos, index);
        m_boolean = b;
    }

    public Object getValue()
    {
        return m_boolean;
    }

    public Type getType()
    {
        return Type.Type_Boolean;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }

    public String toString()
    {
        if (m_boolean)
            return "TRUE";
        return "FALSE";
    }
}