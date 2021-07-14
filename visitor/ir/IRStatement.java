package visitor.ir;

import java.lang.String;

public abstract class IRStatement extends IRNode
{
    protected int m_register;

    public IRStatement(int register)
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

    public abstract String toString();
}
