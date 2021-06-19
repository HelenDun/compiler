package ast;

import java.util.Vector;

public class SemanticException extends RuntimeException
{
    private Vector<EnvironmentElementFunction> m_function_environment;
    private Vector<EnvironmentElement> m_variable_environment;
    private EnvironmentElementFunction m_current_function;

    SemanticException(String errorMsg, Vector<EnvironmentElementFunction> func_env, Vector<EnvironmentElement> var_env, EnvironmentElementFunction curr_func)
    {
        super(errorMsg);
        m_function_environment = func_env;
        m_variable_environment = var_env;
        m_current_function = curr_func;
    }

    public String toStringFunction(EnvironmentElementFunction ef)
    {
        String env = "" + ef.get_type();
        env += " " + ef.get_name();
        env += "(";
        for (EnvironmentElement var : ef.get_parameters())
        {
            env += var.get_type();
            if (var.is_array())
                env += "[]";
            env += " " + var.get_name() + ", ";
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
        for (EnvironmentElementFunction ef : m_function_environment)
            env += toStringFunction(ef);
        return env;
    }

    public String getVariableEnvironment()
    {
        String env = "VARIABLE ENVIRONMENT\n";
        for (EnvironmentElement ef : m_variable_environment)
        {
            env += ef.get_type() + " " + ef.get_name() + "\n";
            if (ef.is_array())
                env += "[]";
        }
        return env;
    }
}
