
public class StatementPrint extends Statement
{
    Expression m_expression;
    boolean m_is_newline;

    public StatementPrint(int line, int pos, int index, Expression expression, boolean is_newline)
    {
        super(line, pos, index);
        m_expression = expression;
        m_is_newline = is_newline;
    }

    public Expression get_expression()
    {
        return m_expression;
    }

    public boolean is_newline()
    {
        return m_is_newline;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}