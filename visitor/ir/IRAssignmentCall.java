package visitor.ir;

import visitor.ast.Type;
import java.lang.String;
import java.util.Vector;

public class IRAssignmentCall extends IRAssignment
{
    String m_name;
    Type m_type;
    Vector<Integer> m_parameter_registers;

    public IRAssignmentCall(int register, String name, Type type)
    {
        super(register);
        m_name = name;
        m_type = type;
        m_parameter_registers = new Vector<Integer>();
    }

    public String getName()
    {
        return m_name;
    }

    public Type getType()
    {
        return m_type;
    }

    public Vector<Integer> getParameterRegisters()
    {
        return m_parameter_registers;
    }

    public void addParameterRegister(int register)
    {
        m_parameter_registers.add(register);
    }

    public String toString()
    {
        String str = __toString();
        str += "CALL ";
        str += m_name;
        str += '(';
        for (int register : m_parameter_registers)
        {
            str += 'T';
            str += String.valueOf(register);
        }
        str += ')';
        return str;
    }
    
}
