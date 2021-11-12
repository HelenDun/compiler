package visitor.ir;

import java.lang.String;
import visitor.ast.Type;

public abstract class IRAssignment extends IRStatement
{
    private int m_register_array;
    private Type m_type;

    public IRAssignment(int register, int array_index, Type type)
    {
        super(register);
        m_register_array = array_index;
        m_type = type;
    }

    public int getArrayIndex()
    {
        return m_register_array;
    }

    public Type getType()
    {
        return m_type;
    }

    public boolean isArray()
    {
        return m_register_array > 0;
    }

    protected String __toString()
    {
        String str = "T";
        str += String.valueOf(getRegister());
        if(isArray())
        {
            str += "[T";
            str += String.valueOf(m_register_array);
            str += ']';
        }
        str += " := ";
        return str;
    }

    public abstract String toString();
}
