package visitor.ast;

import java.util.Vector;
import visitor.Visitor;

public class FunctionBody extends AbstractSyntaxTreeNode
{
    private Vector<Variable> m_variables;
    private Vector<Statement> m_statements;

    public FunctionBody(int line, int pos, int index, Vector<Variable> variables, Vector<Statement> statements)
    {
        super(line, pos, index);
        m_variables = variables;
        m_statements = statements;
    }

    public Vector<Variable> getVariables()
    {
        return m_variables;
    }

    public Vector<Statement> getStatements()
    {
        return m_statements;
    }

	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}