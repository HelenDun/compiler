package visitor.ast;

import java.lang.String;
import visitor.Visitor;

public class LiteralString extends Literal 
{
    private String m_str;

    public LiteralString(int line, int pos, int index, String str)
    {
        super(line, pos, index);
        m_str = str;
    }

    public String getValue()
    {
        return m_str;
    }

    public Type getType()
    {
        return Type.Type_String;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }

    public String toString()
    {
        return '"' + m_str + '"';
    }
}
