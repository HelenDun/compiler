package visitor.ast;

import java.util.Vector;
import visitor.Visitor;

public class FunctionDeclaration extends AbstractSyntaxTreeNode
{
    private CompoundType m_compound_type;
    private Identifier m_identifier;
    private Vector<Variable> m_parameters;

    public FunctionDeclaration(int line, int pos, int index, CompoundType compound_type, Identifier identifier, Vector<Variable> parameters)
    {
        super(line, pos, index);
        m_compound_type = compound_type;
        m_identifier = identifier;
        m_parameters = parameters;
        if (m_parameters == null)
            m_parameters = new Vector<Variable>();
    }

    public CompoundType getCompoundType()
    {
        return m_compound_type;
    }

    public Identifier getIdentifier()
    {
        return m_identifier;
    }

    public Vector<Variable> getParameters()
    {
        return m_parameters;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}