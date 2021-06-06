import java.util.Vector;

public class FunctionDeclaration extends AbstractSyntaxTreeNode
{
    CompoundType m_compound_type;
    Identifier m_identifier;
    Vector<Variable> m_parameters;

    public FunctionDeclaration(int line, int pos, int index, CompoundType compound_type, Identifier identifier, Vector<Variable> parameters)
    {
        super(line, pos, index);
        m_compound_type = compound_type;
        m_identifier = identifier;
        m_parameters = parameters;
    }

    public CompoundType get_compound_type()
    {
        return m_compound_type;
    }

    public Identifier get_identifier()
    {
        return m_identifier;
    }

    public Vector<Variable> get_parameters()
    {
        return m_parameters;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}