package visitor.ir;

import visitor.IRVisitor;
import visitor.ast.Operator;
import visitor.ast.Type;

public class IRAssignmentOperation extends IRAssignment
{
    private Operator m_operator;
    private int m_register_right;
    private int m_register_left;
    private int m_label_1;
    private int m_label_2;

    public IRAssignmentOperation(int register_assign, int register_array, Type type, int register_right, int register_left, Operator operator)
    {
        super(register_assign, register_array, type);
        m_register_right = register_right;
        m_register_left = register_left;
        m_operator = operator;
        m_label_1 = -1;
        m_label_2 = -1;
    }

    public IRAssignmentOperation(int register_assign, int register_array, int register_right, int register_left, int label_1, int label_2)
    {
        super(register_assign, register_array, Type.Type_Boolean);
        m_register_right = register_right;
        m_register_left = register_left;
        m_operator = Operator.Operator_Less_Than;
        m_label_1 = label_1;
        m_label_2 = label_2;
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

    public int getLabel1()
    {
        return m_label_1;
    }

    public int getLabel2()
    {
        return m_label_2;
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
