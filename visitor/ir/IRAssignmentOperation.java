package visitor.ir;

import visitor.ast.Operator;
import visitor.ast.Type;

public class IRAssignmentOperation extends IRAssignment
{
    private Operator m_operator;
    private Type m_type;
    private int m_register_right;
    private int m_register_left;

    public IRAssignmentOperation(int register_assign, int register_right, int register_left, Operator operator, Type type)
    {
        super(register_assign);
        m_register_right = register_right;
        m_register_left = register_left;
        m_operator = operator;
        m_type = type;
    }

    public Operator getOperator()
    {
        return m_operator;
    }

    public Type getType()
    {
        return m_type;
    }

    public int getRegisterRight()
    {
        return m_register_right;
    }

    public int getRegisterLeft()
    {
        return m_register_left;
    }

    public String toString()
    {
        String str = __toString();
        str += 'T';
        str += String.valueOf(m_register_left);
        str += ' ';
        str += m_type.toChar();
        str += m_operator.toString();
        str += " T";
        str += String.valueOf(m_register_right);
        return str;
    }
}
