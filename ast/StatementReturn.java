package ast;
public class StatementReturn extends Statement
{
    Expression m_expression;

    public StatementReturn(int line, int pos, int index, Expression expression)
    {
        super(line, pos, index);
        m_expression = expression;
    }

    public Expression get_expression()
    {
        return m_expression;
    }

    public boolean is_return_value()
    {
        return m_expression != null;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}