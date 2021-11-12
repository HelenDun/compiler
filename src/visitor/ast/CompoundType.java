package visitor.ast;

import visitor.Visitor;

public class CompoundType extends AbstractSyntaxTreeNode
{
    private TypeNode m_type_node;
    private int m_array_size; // if <= 0, then not an array

    public CompoundType(int line, int pos, int index, TypeNode type_node, int array_size)
    {
        super(line, pos, index);
        m_type_node = type_node;
        m_array_size = array_size;
    }

    public TypeNode getTypeNode()
    {
        return m_type_node;
    }

    public int getArraySize()
    {
        return m_array_size;
    }

    public boolean isArray()
    {
        return m_array_size > 0;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}