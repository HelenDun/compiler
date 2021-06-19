package ast;

import java.util.Vector;

public class SemanticException extends RuntimeException
{
    private Vector<EnvironmentElementFunction> m_function_environment;
    private Vector<EnvironmentElement> m_variable_environment;

    SemanticException(String errorMsg, Vector<EnvironmentElementFunction> func_env, Vector<EnvironmentElement> var_env)
    {
        super(errorMsg);
        m_function_environment = func_env;
        m_variable_environment = var_env;
    }

    public String getFunctionEnvironment()
    {
        String env = "FUNCTION ENVIRONMENT\n";

        for (EnvironmentElementFunction ef : m_function_environment)
        {
            env += ef.get_type();
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
        }
        return env;
    }

    public String getVariableEnvironment()
    {
        String env = "VARIABLE ENVIRONMENT\n";
        for (EnvironmentElement ef : m_variable_environment)
            env += ef.get_type() + " " + ef.get_name() + "\n";
        return env;
    }
}
