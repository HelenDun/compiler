package ast;
import java.lang.String;

public class Identifier extends AbstractSyntaxTreeNode
{
    String m_name;

    public Identifier(int line, int pos, int index, String name)
    {
        super(line, pos, index);
        m_name = name;
    }

    public String get_name()
    {
        return m_name;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
