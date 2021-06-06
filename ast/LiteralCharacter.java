package ast;

public class LiteralCharacter extends Literal 
{
    char m_char;

    public LiteralCharacter(int line, int pos, int index, char c)
    {
        super(line, pos, index);
        m_char = c;
    }

    public char get_value()
    {
        return m_char;
    }
    
	public Object accept(Visitor v)
    {
		return v.visit(this);
    }
}