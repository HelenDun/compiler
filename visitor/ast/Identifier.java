package visitor.ast;

import java.lang.String;
import visitor.Visitor;

public class Identifier extends AbstractSyntaxTreeNode
{
    private String m_name;

    public Identifier(int line, int pos, int index, String name)
    {
        super(line, pos, index);
        m_name = name;
    }

    public String getName()
    {
        return m_name;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
