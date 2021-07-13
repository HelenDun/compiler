package visitor.ir;

import java.lang.String;

public class IRStatementReturn extends IRStatement
{
    private int m_register;

    public IRStatementReturn()
    {
        m_register = -1;
    }

    public IRStatementReturn(int register)
    {
        m_register = register;
    }

    public int getRegister()
    {
        return m_register;
    }

    public boolean isRegister()
    {
        return m_register >= 0;
    }

    public String toString()
    {
        String str = "RETURN";
        if (isRegister())
        {
            str += " T";
            str += String.valueOf(m_register);
        }
        return str;
    }
}
