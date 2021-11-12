package visitor.ir;

import java.lang.String;
import visitor.IRVisitor;
import visitor.ast.Type;

public class IRStatementReturn extends IRStatement
{
    private Type m_type;
    private boolean m_isArray;

    public IRStatementReturn()
    {
        super(-1);
        m_isArray = false;
    }

    public IRStatementReturn(int register, Type type, boolean isArray)
    {
        super(register);
        m_type = type;
        m_isArray = isArray;
    }

    public Type getType()
    {
        return m_type;
    }

    public boolean isReturn()
    {
        return true;
    }

    public boolean isArray()
    {
        return m_isArray;
    }

    public void setRegister(int register)
    {
        m_register = register;
    }

    public void setType(Type type)
    {
        m_type = type;
    }

    public void setArray()
    {
        m_isArray = !m_isArray;
    }

    public void setArray(boolean isArray)
    {
        m_isArray = isArray;
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

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
