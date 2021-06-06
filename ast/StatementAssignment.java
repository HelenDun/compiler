
public class StatementAssignment extends Statement
{
    Identifier m_identifier;
    Expression m_array_index; // if null, then "id = expr", else "id[index] = expr"
    Expression m_expression;

    public StatementAssignment(int line, int pos, int index, Identifier identifier, Expression array_index, Expression expression)
    {
        super(line, pos, index);
        m_identifier = identifier;
        m_array_index = array_index;
        m_expression = expression;
    }

    public Identifier get_identifier()
    {
        return m_identifier;
    }

    public Expression get_array_index()
    {
        return m_array_index;
    }

    public Expression get_expression()
    {
        return m_expression;
    }

    public boolean is_array()
    {
        return m_array_index != null;
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}