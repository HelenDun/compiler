package visitor.ir;

import visitor.ast.Type;

public class IRAssignmentArray extends IRAssignment
{
    private int m_size;
    private Type m_type;

    public IRAssignmentArray(int register, int size, Type type)
    {
        super(register);
        m_size = size;
        m_type = type;
    }

    public int getSize()
    {
        return m_size;
    }

    public Type getType()
    {
        return m_type;
    }

    public String toString()
    {
        String str = __toString();
        str += "NEWARRAY ";
        str += m_type.toChar();
        str += ' ';
        str += String.valueOf(m_size);
        return str;
    }
}
