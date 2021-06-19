package ast;
import java.util.Vector;

public class ExpressionFunction extends Expression
{
    Identifier m_identifier;
    Vector<Expression> m_parameters;

    public ExpressionFunction(int line, int pos, int index, Identifier id, Vector<Expression> parameters)
    {
        super(line, pos, index);
        m_identifier = id;
        m_parameters = parameters;
        if (m_parameters == null)
            m_parameters = new Vector<Expression>();
    }

    public Identifier get_identifier()
    {
        return m_identifier;
    }

    public Vector<Expression> get_parameters()
    {
        return m_parameters;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}