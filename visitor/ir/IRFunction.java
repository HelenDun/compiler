package visitor.ir;

import java.lang.String;
import java.util.Vector;
import visitor.Pair;
import visitor.ast.Type;

public class IRFunction extends IRNode
{
    private Vector<IRDeclaration> m_declarations;
    private Vector<IRStatement> m_statements;

    private Type m_type;
    private boolean m_is_array;
    private String m_name;
    private Vector<Pair<Type,Boolean>> m_parameters;
    
    public IRFunction(Type type, boolean is_array, String name)
    {
        m_declarations = new Vector<IRDeclaration>();
        m_statements = new Vector<IRStatement>();
        m_type = type;
        m_is_array = is_array;
        m_name = name;
        m_parameters = new Vector<Pair<Type,Boolean>>();
    }

    public Type getType()
    {
        return m_type;
    }

    public boolean getIsArray()
    {
        return m_is_array;
    }
    public String getName()
    {
        return m_name;
    }

    public Vector<Pair<Type,Boolean>> getParameters()
    {
        return m_parameters;
    }

    public void addParameter(Type type, boolean is_array)
    {
        Pair<Type,Boolean> pair = new Pair<Type,Boolean>(type, is_array);
        m_parameters.add(pair);
    }

    public void addDeclaration(IRDeclaration declaration)
    {
        m_declarations.add(declaration);
    }

    public void addStatement(IRStatement statement)
    {
        m_statements.add(statement);
    }

    public IRDeclaration findDeclaration(String name)
    {
        for (IRDeclaration declaration : m_declarations)
        {
            if (declaration.getName() == null)
                continue;
            if (declaration.getName().equals(name))
                return declaration;
        }
        return null;
    }

    public String toString()
    {
        // function header
        String str = "FUNC ";
        str += m_name;
        str += '(';
        for (Pair<Type,Boolean> parameter : m_parameters)
        {
            if (parameter.getSecond())
                str += 'A';
            str += parameter.getFirst().toChar();
        }
        str += ')';
        if (m_is_array)
            str += 'A';
        str += m_type.toChar();
        str += "\n{\n";

        // declarations
        for (IRDeclaration declaration : m_declarations)
        {
            str += declaration.toString();
            str += '\n';
        }

        // statements
        for (IRStatement statement : m_statements)
        {
            str += statement.toString();
            str += ";\n";
        }

        if (m_statements.size() > 0 && !m_statements.lastElement().isReturn() && this.getType() == Type.Type_Void)
        {
            IRStatementReturn ret = new IRStatementReturn();
            str += ret.toString();
            str += ";\n";
        }

        str += "}\n";
        return str;
    }
}
