package visitor.ir;

import java.lang.String;
import java.util.Vector;
import visitor.IRVisitor;
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

    public Vector<IRDeclaration> getDeclarations()
    {
        return m_declarations;
    }

    public Vector<IRStatement> getStatements()
    {
        return m_statements;
    }

    public Type getType()
    {
        return m_type;
    }

    public String getName()
    {
        return m_name;
    }

    public Vector<Pair<Type,Boolean>> getParameters()
    {
        return m_parameters;
    }

    public boolean isArray()
    {
        return m_is_array;
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

    public Object accept(IRVisitor visitor)
    {
        return visitor.visit(this);
    }

    public String toString()
    {
        // function header
        // ex. FUNC factorial(I)I
        String str = "FUNC ";
        str += m_name;

        // parameters
        str += '(';
        for (Pair<Type,Boolean> parameter : m_parameters)
        {
            if (parameter.getSecond())
                str += 'A';
            str += parameter.getFirst().toChar();
        }
        str += ')';

        // return type
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

        // always end with a return
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
