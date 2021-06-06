package ast;
public class StatementWhile extends Statement
{
    Expression m_expression;
    Block m_block;

    public StatementWhile(int line, int pos, int index, Expression expression, Block block)
    {
        super(line, pos, index);
        m_expression = expression;
        m_block = block;
    }

    public Expression get_expression()
    {
        return m_expression;
    }

    public Block get_block()
    {
        return m_block;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}