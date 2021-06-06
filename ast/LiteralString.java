import java.lang.String;

public class LiteralString extends Literal 
{
    String m_str;

    public LiteralString(int line, int pos, int index, String str)
    {
        super(line, pos, index);
        m_str = str;
    }

    public String get_value()
    {
        return m_str;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }
}
