package ast;
import java.util.Vector;

public class Block extends AbstractSyntaxTreeNode
{
    Vector<Statement> m_statements;

    public Block (int line, int pos, int index)
    {
        super(line, pos, index);
        m_statements = new Vector<Statement>();
    }

    public void add_statement(Statement statement)
    {
        m_statements.add(statement);
        return;
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
