package visitor.ir;

import visitor.IRVisitor;
import visitor.ast.Type;

public class IRAssignmentRegister extends IRAssignment
{
    private int m_register_right;
    private int m_register_right_array;

    // T4[T5] := T6[T7];
    public IRAssignmentRegister(int register_assign, int register_array, Type type, int register_right, int register_right_array)
    {
        super(register_assign, register_array, type);
        m_register_right = register_right;
        m_register_right_array = register_right_array;
    }

    public int getRegisterRight()
    {
        return m_register_right;
    }

    public int getRegisterRightArray()
    {
        return m_register_right_array;
    }

    public boolean isRegisterRightArray()
    {
        return m_register_right_array > 0;
    }

    // T4[T5] := T6[T7];
    public String toString()
    {
        String str = __toString();
        str += 'T';
        str += String.valueOf(m_register_right);
        if (isRegisterRightArray())
        {
            str += "[T";
            str += String.valueOf(m_register_right_array);
            str += ']';
        }
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }

}
