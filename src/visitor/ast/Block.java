package visitor.ast;

import java.util.Vector;
import visitor.Visitor;

public class Block extends AbstractSyntaxTreeNode
{
    private Vector<Statement> m_statements;

    public Block (int line, int pos, int index)
    {
        super(line, pos, index);
        m_statements = new Vector<Statement>();
    }

    public void addStatement(Statement statement)
    {
        m_statements.add(statement);
        return;
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
