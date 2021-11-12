package visitor.ast;

import visitor.Visitor;

public class Variable extends AbstractSyntaxTreeNode
{
    private CompoundType m_compound_type;
    private Identifier m_identifier;

    public Variable(int line, int pos, int index, CompoundType ct, Identifier id)
    {
        super(line, pos, index);
        m_compound_type = ct;
        m_identifier = id;
    }

    public CompoundType getCompoundType()
    {
        return m_compound_type;
    }

    public Identifier getIdentifier()
    {
        return m_identifier;
    }

	public Object accept(Visitor v)
	{
        return v.visit(this);
	}
}