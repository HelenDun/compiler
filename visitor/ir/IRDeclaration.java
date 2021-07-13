package visitor.ir;

import visitor.ast.Type;

public class IRDeclaration extends IRNode
{
    private int m_register;
    private Type m_type;
    private boolean m_is_array;
    private String m_name;
    private boolean m_is_parameter;

    public IRDeclaration(int register, Type type, boolean array_size, String name, boolean is_parameter)
    {
        m_register = register;
        m_type = type;
        m_is_array = array_size;
        m_name = name;
        m_is_parameter = is_parameter;
    }

    public int getRegister()
    {
        return m_register;
    }

    public Type getType()
    {
        return m_type;
    }

    public String getName()
    {
        return m_name;
    }

    public boolean isVariable()
    {
        return m_name != null;
    }

    public boolean isArray()
    {
        return m_is_array;
    }

    public boolean isParameter()
    {
        return m_is_parameter;
    }

    public String toString()
    {
        String str = "TEMP ";
        str += String.valueOf(m_register);
        str += ':';
        if (m_is_array)
            str += 'A';
        str += m_type.toChar();
        
        if (isVariable())
        {
            str += " [";
            if (isParameter())
                str += 'P';
            else
                str += 'L';
            str += "(\"";
            str += m_name;
            str += "\")]";
        }

        str += ";";
        return str;
    }
}
