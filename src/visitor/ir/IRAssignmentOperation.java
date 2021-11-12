package visitor.ir;

import visitor.IRVisitor;
import visitor.ast.Operator;
import visitor.ast.Type;

public class IRAssignmentOperation extends IRAssignment
{
    private Operator m_operator;
    private int m_register_right;
    private int m_register_left;

    public IRAssignmentOperation(int register_assign, int register_array, Type type, int register_right, int register_left, Operator operator)
    {
        super(register_assign, register_array, type);
        m_register_right = register_right;
        m_register_left = register_left;
        m_operator = operator;
    }

    public Operator getOperator()
    {
        return m_operator;
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
        str += getType().toChar();
        str += m_operator.toString();
        str += " T";
        str += String.valueOf(m_register_right);
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }

}
