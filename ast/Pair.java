package ast;

public class Pair<T1, T2>
{
    public T1 m_first;
    public T2 m_second;

    public Pair(T1 first, T2 second)
    {
        m_first = first;
        m_second = second;
    }

    public T1 getFirst()
    {
        return m_first;
    }

    public T2 getSecond()
    {
        return m_second;
    }
}
