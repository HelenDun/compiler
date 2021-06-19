package ast;

public class TypeNode extends AbstractSyntaxTreeNode 
{    
    Type m_type;

    // for TypeVisitor
    public TypeNode(Type type)
    {
        super(0,0,0);
        m_type = type;
    }

    public TypeNode(int line, int pos, int index, Type type)
    {
        super(line, pos, index);
        m_type = type;
    }

    public Type get_type()
    {
        return m_type;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}
