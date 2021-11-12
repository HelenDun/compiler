package visitor.ir;

import visitor.IRVisitor;
import visitor.ast.Type;

public class IRAssignmentArray extends IRAssignment
{
    private int m_size;

    // T0 := NEWARRAY I 3;
    public IRAssignmentArray(int register, int size, Type type)
    {
        super(register, -1, type);
        m_size = size;
    }

    public int getSize()
    {
        return m_size;
    }

    public String toString()
    {
        String str = __toString();
        str += "NEWARRAY ";
        str += getType().toChar();
        str += ' ';
        str += String.valueOf(m_size);
        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
