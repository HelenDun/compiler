
public abstract class AbstractSyntaxTreeNode 
{
    int m_line;
    int m_pos;
    int m_index;

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
    public int get_line()
    {
        return m_line;
    }

    public int getCharPositionInLine()
    {
        return m_pos;
    }
    public int get_pos()
    {
        return m_pos;
    }

    public int getTokenIndex()
    {
        return m_index;
    }
    public int get_token()
    {
        return m_index;
    }

	public abstract Object accept(Visitor v);
}
