package ast;

public class CompoundType extends AbstractSyntaxTreeNode
{
    TypeNode m_type_node;
    int m_array_size; // if <= 0, then not an array

    public CompoundType(int line, int pos, int index, TypeNode type_node, int array_size)
    {
        super(line, pos, index);
        m_type_node = type_node;
        m_array_size = array_size;
    }

    public TypeNode get_type_node()
    {
        return m_type_node;
    }

    public int get_array_size()
    {
        return m_array_size;
    }

    public boolean is_array()
    {
        return m_array_size > 0;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}