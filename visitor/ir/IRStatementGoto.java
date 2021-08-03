package visitor.ir;

import java.lang.String;
import visitor.IRVisitor;

public class IRStatementGoto extends IRStatement
{
    private int m_label;

    public IRStatementGoto(int label)
    {
        super(-1);
        m_label = label;
    }

    public IRStatementGoto(int register, int label)
    {
        super(register);
        m_label = label;
    }

    public int getLabel()
    {
        return m_label;
    }

    public String toString()
    {
        String str = "";
        if (isRegister())
        {
            str += "IF T";
            str += String.valueOf(getRegister());
            str += ' ';
        }
        str += "GOTO L";
        str += String.valueOf(m_label);
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
