package ast;

public class LiteralBoolean extends Literal 
{
    boolean m_boolean;

    public LiteralBoolean(int line, int pos, int index, boolean b)
    {
        super(line, pos, index);
        m_boolean = b;
    }

    public boolean get_value()
    {
        return m_boolean;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }
}