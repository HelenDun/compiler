package visitor;

import java.lang.String;

import visitor.ast.Type;

public class Element
{
    private Type m_type;
    private int m_array_size; // if <= 0, then not an array
    private String m_name;

    public Element(Type type, int array_size, String name)
    {
        m_type = type;
        m_array_size = array_size;
        m_name = name;
    }

    public Element(Type type, int array_size, String name, int register)
    {
        m_type = type;
        m_array_size = array_size;
        m_name = name;
    }

    public Type getType()
    {
        return m_type;
    }

    public boolean isArray()
    {
        return m_array_size > 0;
    }

    public int getArraySize()
    {
        return m_array_size;
    }

    public String getName()
    {
        return m_name;
    }

    public boolean compare(Element other)
    {
        return m_array_size == other.getArraySize()
            && m_type == other.getType()
            && m_name == other.getName();
    }
}
