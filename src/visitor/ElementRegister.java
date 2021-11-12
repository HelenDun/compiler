package visitor;

import visitor.ast.Type;

public class ElementRegister extends Element
{
    private int m_register;
    
    public ElementRegister(Type type, int array_size, String name, int register)
    {
        super(type, array_size, name);
        m_register = register;
    }

    public int getRegister()
    {
        return m_register;
    }

    public boolean compare(ElementRegister other)
    {
        return getArraySize() == other.getArraySize()
            && getType() == other.getType()
            && getName() == other.getName()
            && getRegister() == other.getRegister();
    }
}
