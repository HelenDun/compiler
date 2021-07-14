package visitor.ir;

public class IRAssignmentRegister extends IRAssignment
{
    private int m_register_right;
    private int m_register_right_array;

    public IRAssignmentRegister(int register_assign, int register_array, int register_right, int register_right_array)
    {
        super(register_assign, register_array);
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

    public String toString()
    {
        String str = __toString();
        str += 'T';
        str += String.valueOf(m_register_right);
        return str;
    }
}
