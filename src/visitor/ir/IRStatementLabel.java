package visitor.ir;

import java.lang.String;
import visitor.IRVisitor;

public class IRStatementLabel extends IRStatement
{
    private int m_number;

    public IRStatementLabel(int number)
    {
        super(-1);
        m_number = number;
    }

    public int getLabel()
    {
        return m_number;
    }

    public String toString()
    {
        String str = "L";
        str += String.valueOf(m_number);
        str += ':';
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
