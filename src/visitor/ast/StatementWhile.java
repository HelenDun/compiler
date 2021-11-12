package visitor.ast;

import visitor.Visitor;

public class StatementWhile extends Statement
{
    private Expression m_expression;
    private Block m_block;

    public StatementWhile(int line, int pos, int index, Expression expression, Block block)
    {
        super(line, pos, index);
        m_expression = expression;
        m_block = block;
    }

    public Expression getExpression()
    {
        return m_expression;
    }

    public Block getBlock()
    {
        return m_block;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}