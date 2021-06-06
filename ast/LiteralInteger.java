
public class LiteralInteger extends Literal 
{
    int m_int;

    public LiteralInteger(int line, int pos, int index, int i)
    {
        super(line, pos, index);
        m_int = i;
    }

    public int get_value()
    {
        return m_int;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }
}