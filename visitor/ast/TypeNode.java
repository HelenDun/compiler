package visitor.ast;

import visitor.Visitor;

public class TypeNode extends AbstractSyntaxTreeNode 
{    
    private Type m_type;

    // this constructor is for VisitorType 
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

    public Type getType()
    {
        return m_type;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}

}
