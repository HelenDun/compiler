package visitor.ast;

import visitor.Visitor;

public class StatementExpression extends Statement
{
    private Expression m_expression;

    public StatementExpression(int line, int pos, int index, Expression expression)
    {
        super(line, pos, index);
        m_expression = expression;
    }

    public Expression getExpression()
    {
        return m_expression;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}