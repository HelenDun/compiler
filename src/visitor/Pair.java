package visitor;

public class Pair<T1, T2>
{
    private  T1 m_first;
    private  T2 m_second;

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

    public void setFirst(T1 first)
    {
        m_first = first;
    }

    public void setSecond(T2 second)
    {
        m_second = second;
    }
}
