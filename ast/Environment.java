package ast;

import java.lang.String;
import java.util.Vector;
import java.util.Stack;

public class Environment<T1>
{
    Vector<Pair<String,T1>> m_variables;
    Stack<int> m_scopes;

    public Environment()
    {
        m_variables = new Vector<Pair<String,T1>>();
        m_scopes = new Vector<int>();
        m_scopes.push(0);
    }

    public void add(String name, T1 content)
    {
        Pair<String,T1> p = new Pair<String,T1>(name, content);
        m_variables.add(p);
        m_scopes.push(m_scopes.pop() + 1);
    }

    public void update(String name, T1 content)
    {
        Pair<String,T1> found = null;
        for (int i = 0; i < m_variables.size(); ++i)
        {
            Pair<String,T1> variable = m_variables.elementAt(i);
            if (variable.getFirst().equals(name))
            {
                found = variable;
                break;
            }
        }

        if (found == null)
            this.add(name, content);
        else
            found.setSecond(content);
    }

    public T1 find(String name)
    {
        for (Pair<String,T1> variable : m_variables)
        {
            if (variable.getFirst().equals(name))
            {
                return variable.getSecond();
            }
        }
        return null;
    }

    public void enscope()
    {
        m_scopes.push(m_scopes.peek());
    }

    public void descope()
    {
        m_scopes.pop();
        m_variables.removeRange(m_scopes.peek(), m_variables.size());
    }
}
