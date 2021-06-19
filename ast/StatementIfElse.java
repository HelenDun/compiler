package ast;
public class StatementIfElse extends Statement
{
    Expression m_expression;
    Block m_block1;
    Block m_block2;

    public StatementIfElse(int line, int pos, int index, Expression expression, Block block1, Block block2)
    {
        super(line, pos, index);
        m_expression = expression;
        m_block1 = block1;
        m_block2 = block2;
    }

    public Expression getExpression()
    {
        return m_expression;
    }

    public Block getBlock1()
    {
        return m_block1;
    }

    public Block getBlock2()
    {
        return m_block2;
    }

    public boolean hasElse()
    {
        return m_block2 != null;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}