package visitor.ast;

import visitor.Visitor;

public class Function extends AbstractSyntaxTreeNode
{
    private FunctionDeclaration m_function_declaration;
    private FunctionBody m_function_body;

    public Function(int line, int pos, int index, FunctionDeclaration function_declaration, FunctionBody function_body)
    {
        super(line, pos, index);
        m_function_declaration = function_declaration;
        m_function_body = function_body;
    }

    public FunctionDeclaration getFunctionDeclaration()
    {
        return m_function_declaration;
    }

    public FunctionBody getFunctionBody()
    {
        return m_function_body;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}