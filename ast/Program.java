package ast;
import java.util.Vector;

public class Program extends AbstractSyntaxTreeNode
{
	Vector<Function> m_functions;
	
	public Program(int line, int pos, int index)
	{
		super(line, pos, index);
		m_functions = new Vector<Function>();
	}
	
	public void addFunction(Function s)
	{	
		m_functions.add(s);
	}
	
	public Vector<Function> getFunctions()
	{
		return m_functions;
	}
	
	public Function getFunction(int index)
	{
		if (index >= getFunctionCount())
			return null;
		return m_functions.elementAt(index);
	}
	
	public int getFunctionCount()
	{
		return m_functions.size();
	}
	
	public Object accept(Visitor v)
	{
		return v.visit(this);
	}
}