
enum Operator
{
    Operator_Multiply,
    Operator_Addition,
    Operator_Subtraction,
    Operator_Less_Than,
    Operator_Equals,
    Operator_MAX
}

public class ExpressionOperation extends Expression
{
    Operator m_operator;
    Expression m_expression1;
    Expression m_expression2;

    public ExpressionOperation(int line, int pos, int index, Operator op, Expression e1, Expression e2)
    {
        super(line, pos, index);
        m_operator = op;
        m_expression1 = e1;
        m_expression2 = e2;
    }

    public Operator get_operator()
    {
        return m_operator;
    }

    public Expression get_expression1()
    {
        return m_expression1;
    }

    public Expression get_expression2()
    {
        return m_expression2;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
