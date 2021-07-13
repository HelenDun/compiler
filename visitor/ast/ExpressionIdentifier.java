package visitor.ast;

import visitor.Visitor;

public class ExpressionIdentifier extends Expression
{
    private Identifier m_identifier;
    private Expression m_array_index;

    public ExpressionIdentifier(int line, int pos, int index, Identifier id, Expression array_index)
    {
        super(line, pos, index);
        m_identifier = id;
        m_array_index = array_index;
    }

    public Identifier getIdentifier()
    {
        return m_identifier;
    }

    public Expression getArrayIndex()
    {
        return m_array_index;
    }

    public boolean isArray()
    {
        return m_array_index != null;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
