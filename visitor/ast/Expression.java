package visitor.ast;

import visitor.Visitor;

public abstract class Expression extends AbstractSyntaxTreeNode
{
	private int m_num_parentheses;

	public Expression(int line, int pos, int index)
	{
		super(line, pos, index);
		m_num_parentheses = 0;
	}

	public void incParentheses()
	{
		m_num_parentheses += 1;
	}
	
	public int getParentheses()
	{
		return m_num_parentheses;
	}
	public abstract Object accept(Visitor v);
}

