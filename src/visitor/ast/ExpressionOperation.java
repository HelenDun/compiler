package visitor.ast;

import visitor.Visitor;

public class ExpressionOperation extends Expression
{
    private Operator m_operator;
    private Expression m_expression1;
    private Expression m_expression2;

    public ExpressionOperation(int line, int pos, int index, Operator op, Expression e1, Expression e2)
    {
        super(line, pos, index);
        m_operator = op;
        m_expression1 = e1;
        m_expression2 = e2;
    }

    public Operator getOperator()
    {
        return m_operator;
    }

    public Expression getExpressionFirst()
    {
        return m_expression1;
    }

    public Expression getExpressionSecond()
    {
        return m_expression2;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
