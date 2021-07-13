package ast;

import java.lang.String;
import java.util.Vector;

public class ElementFunction extends Element
{
    Vector<Element> m_parameters;
    
    public ElementFunction(Type type, int array_size, String name)
    {
        super(type, array_size, name);
        m_parameters = new Vector<Element>();
    }

    public Vector<Element> get_parameters()
    {
        return m_parameters;
    }

    public void add_parameter(Element element)
    {
        m_parameters.add(element);
    }

    public boolean compare(ElementFunction other)
    {
        Vector<Element> parameters = other.get_parameters();
        
        boolean is_same = m_array_size == other.get_array_size()
            && m_type == other.get_type()
            && m_name == other.get_name()
            && m_parameters.size() == parameters.size();
            
        if (is_same)
        {
            for (int i = 0; i < m_parameters.size(); ++i)
            {
                if (m_parameters.elementAt(i) != parameters.elementAt(i))
                    return false;
            }
        }
        return is_same;
    }
}
