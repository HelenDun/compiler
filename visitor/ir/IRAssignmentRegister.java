package visitor.ir;

public class IRAssignmentRegister extends IRAssignment
{
    private int m_register_address;

    public IRAssignmentRegister(int register_assigned, int register_address)
    {
        super(register_assigned);
        m_register_address = register_address;
    }

    public int getRegisterAddress()
    {
        return m_register_address;
    }

    public String toString()
    {
        String str = __toString();
        str += 'T';
        str += String.valueOf(m_register_address);
        return str;
    }
}
