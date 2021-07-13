package visitor.ast;

import visitor.Visitor;

public class StatementIfElse extends Statement
{
    private Expression m_expression;
    private Block m_block1;
    private Block m_block2;

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

    public Block getBlockFirst()
    {
        return m_block1;
    }

    public Block getBlockSecond()
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