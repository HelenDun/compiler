package ast;

public class Variable extends AbstractSyntaxTreeNode
{
    CompoundType m_compound_type;
    Identifier m_identifier;

    public Variable(int line, int pos, int index, CompoundType ct, Identifier id)
    {
        super(line, pos, index);
        m_compound_type = ct;
        m_identifier = id;
    }

    public CompoundType get_compound_type()
    {
        return m_compound_type;
    }

    public Identifier get_identifier()
    {
        return m_identifier;
    }

	public Object accept(Visitor v)
	{
        return v.visit(this);
	}
}