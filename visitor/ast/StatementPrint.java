package visitor.ast;

import visitor.Visitor;

public class StatementPrint extends Statement
{
    private Expression m_expression;
    private boolean m_is_newline;

    public StatementPrint(int line, int pos, int index, Expression expression, boolean is_newline)
    {
        super(line, pos, index);
        m_expression = expression;
        m_is_newline = is_newline;
    }

    public Expression getExpression()
    {
        return m_expression;
    }

    public boolean isNewline()
    {
        return m_is_newline;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}