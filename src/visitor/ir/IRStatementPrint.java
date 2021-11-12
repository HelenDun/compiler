package visitor.ir;

import java.lang.String;
import visitor.IRVisitor;
import visitor.ast.Type;

public class IRStatementPrint extends IRStatement
{
    private Type m_type;
    private boolean m_is_newline;

    public IRStatementPrint(int register, Type type, boolean is_newline)
    {
        super(register);
        m_type = type;
        m_is_newline = is_newline;
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
        str += String.valueOf(getRegister());
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
