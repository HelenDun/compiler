package visitor.ast;

import java.util.Vector;
import visitor.Visitor;

public class ExpressionFunction extends Expression
{
    private Identifier m_identifier;
    private Vector<Expression> m_parameters;

    public ExpressionFunction(int line, int pos, int index, Identifier id, Vector<Expression> parameters)
    {
        super(line, pos, index);
        m_identifier = id;
        m_parameters = parameters;
        if (m_parameters == null)
            m_parameters = new Vector<Expression>();
    }

    public Identifier getIdentifier()
    {
        return m_identifier;
    }

    public Vector<Expression> getParameters()
    {
        return m_parameters;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}