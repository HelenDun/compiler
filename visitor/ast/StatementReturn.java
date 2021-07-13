package visitor.ast;

import visitor.Visitor;

public class StatementReturn extends Statement
{
    private Expression m_expression;

    public StatementReturn(int line, int pos, int index, Expression expression)
    {
        super(line, pos, index);
        m_expression = expression;
    }

    public Expression getExpression()
    {
        return m_expression;
    }

    public boolean hasExpression()
    {
        return m_expression != null;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}