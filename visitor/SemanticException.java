package visitor;

import java.util.Vector;

public class SemanticException extends RuntimeException
{
    private Environment<ElementFunction> m_function_environment;
    private Environment<Element> m_variable_environment;
    private ElementFunction m_current_function;

    SemanticException(String errorMsg, Environment<ElementFunction> func_env, Environment<Element> var_env, ElementFunction curr_func)
    {
        super(errorMsg);
        m_function_environment = func_env;
        m_variable_environment = var_env;
        m_current_function = curr_func;
    }

    public String toStringFunction(ElementFunction ef)
    {
        String env = "" + ef.getType();
        env += " " + ef.getName();
        env += "(";
        for (Element var : ef.getParameters())
        {
            env += var.getType();
            if (var.isArray())
                env += "[]";
            env += " " + var.getName() + ", ";
        }
        env += ")\n";
        return env;
    }

    public String getCurrentFunction()
    {
        String str = "CURRENT FUNCTION\n" + toStringFunction(m_current_function);
        return str;
    }

    public String getFunctionEnvironment()
    {
        String env = "FUNCTION ENVIRONMENT\n";
        for (Pair<String,ElementFunction> p : m_function_environment.get())
            env += toStringFunction(p.getSecond());
        return env;
    }

    public String getVariableEnvironment()
    {
        String env = "VARIABLE ENVIRONMENT\n";
        for (Pair<String,Element> p : m_variable_environment.get())
        {
            Element ef = p.getSecond();
            env += ef.getType();
            if (ef.isArray())
                env += "[]";
            env += " " + ef.getName() + "\n";
        }
        return env;
    }
}
