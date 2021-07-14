package visitor.ir;

import java.lang.String;

public class IRStatementReturn extends IRStatement
{
    public IRStatementReturn()
    {
        super(-1);
    }

    public IRStatementReturn(int register)
    {
        super(register);
    }

    public void setRegister(int register)
    {
        m_register = register;
    }

    public String toString()
    {
        String str = "RETURN";
        if (isRegister())
        {
            str += " T";
            str += String.valueOf(getRegister());
        }
        return str;
    }
}
