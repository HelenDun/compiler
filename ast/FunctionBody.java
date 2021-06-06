import java.util.Vector;

public class FunctionBody extends AbstractSyntaxTreeNode
{
    Vector<Variable> m_variables;
    Vector<Statement> m_statements;

    public FunctionBody(int line, int pos, int index, Vector<Variable> variables, Vector<Statement> statements)
    {
        super(line, pos, index);
        m_variables = variables;
        m_statements = statements;
    }

    public Vector<Variable> get_variables()
    {
        return m_variables;
    }

    public Vector<Statement> get_statements()
    {
        return m_statements;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}