package visitor.ir;

import java.lang.String;

public abstract class IRAssignment extends IRStatement
{
    public IRAssignment(int register)
    {
        super(register);
    }

    protected String __toString()
    {
        String str = "T";
        str += String.valueOf(getRegister());
        str += " := ";
        return str;
    }

    public abstract String toString();
}
