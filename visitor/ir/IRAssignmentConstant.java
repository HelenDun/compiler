package visitor.ir;

import visitor.ast.Type;
import visitor.ast.Literal;

public class IRAssignmentConstant extends IRAssignment 
{
    Literal m_value;

    public IRAssignmentConstant(int register, int register_array, Literal value)
    {
        super(register, register_array);
        m_value = value;
    }

    public Type getType()
    {
        return m_value.getType();
    }

    public Literal getLiteral()
    {
        return m_value;
    }

    public String toString()
    {
        String str = __toString();
        str += m_value.toString();
        return str;
    }
}
