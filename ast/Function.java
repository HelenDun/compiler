package ast;

public class Function extends AbstractSyntaxTreeNode
{
    FunctionDeclaration m_function_declaration;
    FunctionBody m_function_body;

    public Function(int line, int pos, int index, FunctionDeclaration function_declaration, FunctionBody function_body)
    {
        super(line, pos, index);
        m_function_declaration = function_declaration;
        m_function_body = function_body;
    }

    public FunctionDeclaration get_function_declaration()
    {
        return m_function_declaration;
    }

    public FunctionBody get_function_body()
    {
        return m_function_body;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}