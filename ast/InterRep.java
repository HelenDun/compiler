package ast;

import java.util.String;

public InterRep
{
    Environment<int> m_str_to_register;
    String m_string;

    InterRep(String programName)
    {
    }

    void addFunctionDeclaration(String functionName)
    {
    }

    void addVariableDeclaration()
    {

    }

    void addConstant() // value
    {

    }

    void addAssignment()
    {

    }

    void addPrint(boolean isNewline)
    {

    }

    void addJump(int marker, boolean isConditional, int conditionalRegister)
    {
        if (isConditional)
        {
            s += "IF T";
            s += String(conditionalRegister);
        }
        s += "GOTO L";
        s += String(marker);
        s += ";\n";
    }

    void addMarker(int marker)
    {
        s += 'L';
        s += String(marker);
        s += ":;\n";
    }

    String toString()
    {
        return s;
    }
}

