package ast;

public abstract class Expression extends AbstractSyntaxTreeNode
{
	int m_num_parentheses;
	public Expression(int line, int pos, int index)
	{
		super(line, pos, index);
		m_num_parentheses = 1;
	}
	public void increment_num_parentheses()
	{
		m_num_parentheses += 1;
	}
	public int get_num_parentheses()
	{
		return m_num_parentheses;
	}
	public abstract Object accept(Visitor v);
}

