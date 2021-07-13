package visitor.ir;

import java.lang.String;

public class IRStatementGoto extends IRStatement
{
    private int m_label;

    public IRStatementGoto(int label)
    {
        m_label = label;
    }

    public int getLabel()
    {
        return m_label;
    }

    public String toString()
    {
        String str = "GOTO L";
        str += String.valueOf(m_label);
        return str;
    }
}
