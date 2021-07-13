package ast;

import java.lang.String;

public class Element
{
    Type m_type;
    int m_array_size; // if <= 0, then not an array
    String m_name;
    int m_register;

    public Element(Type type, int array_size, String name)
    {
        m_type = type;
        m_array_size = array_size;
        m_name = name;
        m_register = -1;
    }

    public Element(Type type, int array_size, String name, int register)
    {
        m_type = type;
        m_array_size = array_size;
        m_name = name;
        m_register = register;
    }

    public Type get_type()
    {
        return m_type;
    }

    public boolean is_array()
    {
        return m_array_size > 0;
    }

    public int get_array_size()
    {
        return m_array_size;
    }

    public String get_name()
    {
        return m_name;
    }

    public int getRegister()
    {
        return m_register;
    }

    public boolean compare(Element other)
    {
        return m_array_size == other.get_array_size()
            && m_type == other.get_type()
            && m_name == other.get_name();
    }
}
