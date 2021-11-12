package visitor.ast;

import visitor.Visitor;

public class StatementAssignment extends Statement
{
    private Identifier m_identifier;
    private Expression m_array_index; // if null, then "id = expr", else "id[index] = expr"
    private Expression m_expression;

    public StatementAssignment(int line, int pos, int index, Identifier identifier, Expression array_index, Expression expression)
    {
        super(line, pos, index);
        m_identifier = identifier;
        m_array_index = array_index;
        m_expression = expression;
    }

    public Identifier getIdentifier()
    {
        return m_identifier;
    }

    public Expression getArrayIndex()
    {
        return m_array_index;
    }

    public Expression getExpression()
    {
        return m_expression;
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