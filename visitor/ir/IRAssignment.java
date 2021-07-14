package visitor.ir;

import java.lang.String;

public abstract class IRAssignment extends IRStatement
{
    private int m_register_array;

    public IRAssignment(int register, int array_index)
    {
        super(register);
        m_register_array = array_index;
    }

    public int getArrayIndex()
    {
        return m_register_array;
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
