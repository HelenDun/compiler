package visitor.ast;

import visitor.Visitor;

public abstract class AbstractSyntaxTreeNode 
{
    private int m_line;
    private int m_pos;
    private int m_index;

    public AbstractSyntaxTreeNode(int line, int pos, int index)
    {
        m_line = line;
        m_pos = pos;
        m_index = index;
    }

    public int getLine()
    {
        return m_line;
    }

    public int getCharPositionInLine()
    {
        return m_pos;
    }

    public int getTokenIndex()
    {
        return m_index;
    }

	public abstract Object accept(Visitor v);
}
