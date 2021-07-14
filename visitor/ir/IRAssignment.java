package visitor.ir;

import java.lang.String;

public abstract class IRAssignment extends IRStatement
{
    private int m_array_index;

    public IRAssignment(int register, int array_index)
    {
        super(register);
        m_array_index = array_index;
    }

    public int getArrayIndex()
    {
        return m_array_index;
    }

    public boolean isArray()
    {
        return m_array_index > 0;
    }

    protected String __toString()
    {
        String str = "T";
        str += String.valueOf(getRegister());
        if(isArray())
        {
            str += '[';
            str += String.valueOf(m_array_index);
            str += ']';
        }
        str += " := ";
        return str;
    }

    public abstract String toString();
}
