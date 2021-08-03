package visitor.ir;

import java.lang.String;
import java.util.Vector;

import visitor.IRVisitor;

public class IRProgram extends IRNode
{
    private String m_name;
    private Vector<IRFunction> m_functions;
    
    public IRProgram(String name)
    {
        m_name = name;
        m_functions = new Vector<IRFunction>();
    }

    public String getName()
    {
        return m_name;
    }

    public Vector<IRFunction> getFunctions()
    {
        return m_functions;
    }

    public void addFunction(IRFunction function)
    {
        m_functions.add(function);
    }

    public String toString()
    {
        // PROG test_01_example
        String str = "PROG ";
        str += m_name;
        str += '\n';

        // FUNC factorial(I)I
        for (IRFunction function : m_functions)
        {
            str += function.toString();
            str += '\n';
        }

        return str;
    }

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }
}
