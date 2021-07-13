package visitor.ast;

import java.lang.String;

public enum Type
{
    Type_Int,
    Type_Float,
    Type_Char,
    Type_String,
    Type_Boolean,
    Type_Void,
    Type_MAX;

    public String toString()
	{
		switch (this)
		{
			case Type_Int:
				return "int";
			case Type_Float:
				return "float";
			case Type_Char:
				return "char";
			case Type_String:
				return "string";
			case Type_Boolean:
				return "boolean";
			case Type_Void:
				return "void";
			default:
				break;
		}
		return null;
	}
    
    public char toChar()
    {
		switch (this)
		{
			case Type_Int:
				return 'I';
			case Type_Float:
				return 'F';
			case Type_Char:
				return 'C';
			case Type_String:
				return 'U';
			case Type_Boolean:
				return 'Z';
			case Type_Void:
				return 'V';
			default:
				break;
		}
		return ' ';
    }
}