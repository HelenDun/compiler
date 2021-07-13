package visitor.ir;

import java.lang.String;
import visitor.ast.Type;

public class IRStatementPrint extends IRStatement
{
    private int m_register;
    private Type m_type;
    private boolean m_is_newline;

    public IRStatementPrint(int register, Type type, boolean is_newline)
    {
        m_register = register;
        m_type = type;
        m_is_newline = is_newline;
    }

    public int getRegister()
    {
        return m_register;
    }

    public Type getType()
    {
        return m_type;
    }

    public boolean isNewline()
    {
        return m_is_newline;
    }

    public String toString()
    {
        String str = "PRINT";
        if (isNewline())
            str += "LN";
        str += m_type.toChar();
        str += " T";
        str += String.valueOf(m_register);
        return str;
    }
}
