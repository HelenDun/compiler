package visitor.ast;

import visitor.Visitor;

public class LiteralCharacter extends Literal 
{
    private char m_char;

    public LiteralCharacter(int line, int pos, int index, char c)
    {
        super(line, pos, index);
        m_char = c;
    }

    public Object getValue()
    {
        return m_char;
    }

    public Type getType()
    {
        return Type.Type_Char;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }

    public String toString()
    {
        return '\'' + String.valueOf(m_char) + '\'';
    }
}