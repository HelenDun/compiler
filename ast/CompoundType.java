
enum Type 
{
    Type_Int,
    Type_Float,
    Type_Char,
    Type_String,
    Type_Boolean,
    Type_Void,
    Type_MAX,
}

public class CompoundType extends AbstractSyntaxTreeNode
{
    Type m_type;
    int m_array_size; // if <= 0, then not an array

    public CompoundType(int line, int pos, int index, Type type, int array_size)
    {
        super(line, pos, index);
        m_type = type;
        m_array_size = array_size;
    }

    public Type get_type()
    {
        return m_type;
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