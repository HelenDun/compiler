package ast;
public class StatementEmpty extends Statement
{
    public StatementEmpty(int line, int pos, int index)
    {
        super(line, pos, index);
    }

	public Object accept(Visitor v)
    {
        return v.visit(this);
    }
}