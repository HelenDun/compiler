package visitor;

import java.lang.String;
import java.util.Vector;

import visitor.ast.Type;

public class ElementFunction extends Element
{
    private Vector<Element> m_parameters;
    
    public ElementFunction(Type type, int array_size, String name)
    {
        super(type, array_size, name);
        m_parameters = new Vector<Element>();
    }

    public Vector<Element> getParameters()
    {
        return m_parameters;
    }

    public void addParameter(Element element)
    {
        m_parameters.add(element);
    }

    public boolean compare(ElementFunction other)
    {
        Vector<Element> parameters = other.getParameters();
        
        boolean is_same = getArraySize() == other.getArraySize()
                       && getType() == other.getType()
                       && getName() == other.getName()
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
