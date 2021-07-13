package visitor.ast;

public enum Operator
{
    Operator_Multiply,
    Operator_Addition,
    Operator_Subtraction,
    Operator_Less_Than,
    Operator_Equals,
    Operator_MAX;

    public String toString()
	{
		switch (this)
		{
			case Operator_Multiply:
				return "*";
			case Operator_Subtraction:
				return "-";
			case Operator_Addition:
				return "+";
			case Operator_Less_Than:
				return "<";
			case Operator_Equals:
				return "==";
			default:
				break;
		}
		return null;
	}
}