package ast;

public class ExpressionIdentifier extends Expression
{
    Identifier m_identifier;
    Expression m_array_index;

    public ExpressionIdentifier(int line, int pos, int index, Identifier id, Expression array_index)
    {
        super(line, pos, index);
        m_identifier = id;
        m_array_index = array_index;
    }

    public Identifier get_identifier()
    {
        return m_identifier;
    }

    public Expression get_array_index()
    {
        return m_array_index;
    }

    public boolean is_array()
    {
        return m_array_index != null;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
