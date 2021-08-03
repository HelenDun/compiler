package visitor.ir;

import visitor.IRVisitor;
import visitor.ast.Type;
import java.lang.String;
import java.util.Vector;
import visitor.Pair;

public class IRAssignmentCall extends IRAssignment
{
    String m_name;
    Vector<Pair<Type, Integer>> m_parameter_registers;

    public IRAssignmentCall(int register, int array_register, String name, Type type)
    {
        super(register, array_register, type);
        m_name = name;
        m_parameter_registers = new Vector<Pair<Type, Integer>>();
    }

    public String getName()
    {
        return m_name;
    }
    public Vector<Pair<Type, Integer>> getParameterRegisters()
    {
        return m_parameter_registers;
    }

    public void addParameterRegister(Type type, int register)
    {
        m_parameter_registers.add(new Pair<Type, Integer>(type, register));
    }

    public String toString()
    {
        String str = "";
        if (getType() != Type.Type_Void)
            str = __toString();
        str += "CALL ";
        str += m_name;
        str += '(';
        for (Pair<Type, Integer> pair : m_parameter_registers)
        {
            str += 'T';
            str += String.valueOf(pair.getSecond());
        }
        str += ')';
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
