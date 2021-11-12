package visitor;

import java.util.Vector;
import visitor.ir.*;
import visitor.ast.Type;
import visitor.ast.Operator;

public class IRVisitorJasmin extends IRVisitor
{
    private final String TAB = "    ";
    private String m_name;
    private int m_counter_label_func;
    private int m_counter_label_op;

    private int __getLabelFunc()
    {
        return m_counter_label_func++;
    }

    private int __getLabelOp()
    {
        return m_counter_label_op++;
    }

    private String __toStringLabel(String label)
    {
        label += ":\n";
        return label;
    }

    private String __toStringLabelRegular(int label)
    {
        return 'L' + String.valueOf(label);
    }

    private String __toStringLabelFunction(int label)
    {
        return 'F' + String.valueOf(label);
    }

    private String __toStringLabelOperation(int label)
    {
        return "OP" + String.valueOf(label);
    }

    private String __toStringInstructionLoad(Type type, boolean isArray, int register)
    {
        String str = TAB;
        str += __toCharInstructionType(type, isArray);
        str += "load ";
        str += String.valueOf(register);
        str += '\n';
        return str;
    }

    private String __toStringInstructionStore(Type type, boolean isArray, int register)
    {
        String str = TAB;
        str += __toCharInstructionType(type, isArray);
        str += "store ";
        str += String.valueOf(register);
        str += '\n';
        return str;
    }

    private String __toStringType(Type type, boolean isArray)
    {
        String str = "";
        if (isArray)
        {
            str += '[';
        }

        if (type.equals(Type.Type_String))
        {
            str += "Ljava/lang/String;";
        }
        else
        {
            str += type.toChar();
        }
        return str;
    }

    private char __toCharInstructionType(Type type, boolean isArray)
    {
        if (isArray || type == Type.Type_String)
        {
            return 'a';
        }
        else if (type == Type.Type_Float)
        {
            return 'f';
        }
        return 'i';
    }

    public IRVisitorJasmin()
    {
    }

	public Object visit(IRProgram program)
    {
        // .source test_01_example.ul
        String str = ".source ";
        m_name = program.getName();
        str += m_name;
        str += ".ul\n";

        // .class public test_01_example
        str += ".class public ";
        str += m_name;
        str += '\n';

        // .super java/lang/Object
        str += ".super java/lang/Object\n\n";

        // .method public static factorial(I)I
        for (IRFunction function : program.getFunctions())
        {
            str += function.accept(this).toString();
        }

        // boilerplate
        String str_main = ".method public static main([Ljava/lang/String;)V\n";
        str_main += TAB;
        str_main += ".limit locals 1\n";
        str_main += TAB;
        str_main += ".limit stack 4\n";
        str_main += TAB;
        str_main += "invokestatic ";
        str_main += m_name;
        str_main += "/__main()V\n";
        str_main += TAB;
        str_main += "return\n";
        str_main += ".end method\n\n";
        str += str_main;

        String str_init = ".method public <init>()V\n";
        str_init += TAB;
        str_init += "aload_0\n";
        str_init += TAB;
        str_init += "invokenonvirtual java/lang/Object/<init>()V\n";
        str_init += TAB;
        str_init += "return\n";
        str_init += ".end method\n";
        str += str_init;

        return str;
    }

	public Object visit(IRFunction function)
    {
        // .method public static factorial(I)I
        String str = ".method public static __";
        str += function.getName();

        // parameters
        str += '(';
        for (Pair<Type,Boolean> parameter : function.getParameters())
        {
            if (parameter.getSecond())
                str += '[';
            str += parameter.getFirst().toChar();
        }
        str += ')';

        // return type
        str += __toStringType(function.getType(), function.isArray());
        str += '\n';

        int label_start = __getLabelFunc();
        int label_end = __getLabelFunc();
        String str_label_start = __toStringLabelFunction(label_start);
        String str_label_end = __toStringLabelFunction(label_end);

        // .limit locals 8
        String str_decs = TAB;
        str_decs += ".limit locals ";
        str_decs += String.valueOf(function.getDeclarations().size());
        str_decs += '\n';

        // declarations
        // ex. .var 0 is n I from L_0 to L_1
        String str_range = " from " + str_label_start + " to " + str_label_end;
        for (IRDeclaration declaration : function.getDeclarations())
        {
            String var = TAB;
            var += ".var ";
            var += String.valueOf(declaration.getRegister());
            var += " is ";
            var += declaration.getName();
            var += ' ';

            var += __toStringType(declaration.getType(), declaration.isArray());
            var += str_range;
            var += '\n';
            str_decs += var;
        }

        // .limit stack 16
        str_decs += TAB;
        str_decs += ".limit stack 16\n";
        str += str_decs;

        // label for variable declaration
        str += __toStringLabel(str_label_start);

        // initialization of declarations
        String str_inits = "";
        for (IRDeclaration declaration : function.getDeclarations())
        {
            str_inits += declaration.accept(this).toString();
        }
        str += str_inits;

        // statements
        String str_stats = "";
        Vector<IRStatement> statements = function.getStatements();
        for (IRStatement statement : statements)
        {
            str_stats += statement.accept(this).toString();
        }
        str += str_stats;

        // always end with a return
        Type type_ret = function.getType();
        if (type_ret == Type.Type_Void)
        {
            str += TAB;
            str += "return\n";
        }
        else if (type_ret == Type.Type_Float)
        {
            // ldc 0
            str += TAB;
            str += "ldc 0.0\n";

            // ireturn
            str += TAB;
            str += "freturn\n";
        }
        else
        {
            // ldc 0
            str += TAB;
            str += "ldc 0\n";

            // ireturn
            str += TAB;
            str += "ireturn\n";
        }

        // label for variable declaration
        str += __toStringLabel(str_label_end);
        str += ".end method\n\n";
        return str;
    }

	public Object visit(IRDeclaration declaration)
    {
        /*
        // for arrays and strings
            aconst_null 
            astore 0
        // for int, char and bool
            ldc 0
            istore 1
        // for float
            ldc 0.0
            fstore 5
        */
        if (declaration.isParameter())
        {
            return "";
        }

        String str = TAB;
        Type type = declaration.getType();
        if (declaration.isArray() || type == Type.Type_String)
        {
            str += "aconst_null\n";
        }
        else if (type == Type.Type_Float)
        {
            str += "ldc 0.0\n";
        }
        else
        {
            str += "ldc 0\n";
        }
        str += __toStringInstructionStore(type, declaration.isArray(), declaration.getRegister());
        return str;
    }


	public Object visit(IRStatementGoto sg)
    {
        String str = "";

        // iload 2
        // ifne L0
        if (sg.isRegister())
        {
            str += __toStringInstructionLoad(Type.Type_Boolean, false, sg.getRegister());
            str += TAB;
            str += "ifne ";
        }
        
        // goto L1
        else
        {
            str += TAB;
            str += "goto ";
        }

        str += __toStringLabelRegular(sg.getLabel());
        str += '\n';
        return str;
    }
    
	public Object visit(IRStatementLabel sl)
    {
        return __toStringLabel(__toStringLabelRegular(sl.getLabel()));
    }

	public Object visit(IRStatementPrint sp)
    {
        /*
        // print
            getstatic java/lang/System/out Ljava/io/PrintStream;
            iload 12
            invokevirtual java/io/PrintStream/print(I)V
            
        // println
            getstatic java/lang/System/out Ljava/io/PrintStream;
            aload 13
            invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
        */
        Type type = sp.getType();

        // load print function
        String str = "    getstatic java/lang/System/out Ljava/io/PrintStream;\n";

        // load parameter
        str += __toStringInstructionLoad(sp.getType(), false, sp.getRegister());

        // invoke print function
        str += TAB;
        str += "invokevirtual java/io/PrintStream/print";
        if (sp.isNewline())
        {
            str += "ln";
        }

        // parameter
        str += '(';
        str += __toStringType(type, false);
        str += ")V\n";
        
        return str;
    }

	public Object visit(IRStatementReturn sr)
    {
        String str = "";

        if (sr.isRegister())
        {
            // iload 1
            // ireturn
            str += __toStringInstructionLoad(sr.getType(), sr.isArray(), sr.getRegister());
            str += TAB;
            str += __toCharInstructionType(sr.getType(), sr.isArray());
        }
        else
        {
            str += TAB;
        }

        str += "return\n";
        return str;
    }


	public Object visit(IRAssignmentArray aa)
    {
        // ldc 3
        String str = TAB;
        str += "ldc ";
        str += String.valueOf(aa.getSize());
        str += '\n';

        // newarray int
        Type type = aa.getType();
        str += TAB;
        if (type == Type.Type_String)
        {
            str += "anewarray java/lang/String\n";
        }
        else
        {
            str += "newarray ";
            str += type.toString();
            str += '\n';
        }

        // astore 0
        str += __toStringInstructionStore(type, true, aa.getRegister());
        return str;
    }

	public Object visit(IRAssignmentCall ac)
    {
        /*
        // T3 := CALL factorial(T5);
        iload 5
        invokestatic test_01_example/factorial(I)I
        istore 3
        */
        String str = "";

        for (Pair<Type, Integer> pair : ac.getParameterRegisters())
        {
            str += __toStringInstructionLoad(pair.getFirst(), false, pair.getSecond());
        }

        // invoke function
        str += TAB;
        str += "invokestatic ";
        str += m_name;
        str += "/__";
        str += ac.getName();

        // parameters
        str += '(';
        for (Pair<Type, Integer> pair : ac.getParameterRegisters())
        {
            str += __toStringType(pair.getFirst(), false);
        }
        str += ')';

        // return type
        str += __toStringType(ac.getType(), ac.isArray());
        str += '\n';

        if (!ac.getType().equals(Type.Type_Void))
        {
            str += __toStringInstructionStore(ac.getType(), ac.isArray(), ac.getRegister());
        }

        return str;
    }

	public Object visit(IRAssignmentConstant ac)
    {
        /*
        // T1 := 1;
        ldc 1
        istore 1
        */
        String str = TAB;
        str += "ldc ";

        Type type = ac.getType();
        if (type == Type.Type_Boolean)
        {
            boolean bool = (boolean) ac.getLiteral().getValue();
            int bool_to_int = (bool) ? 1 : 0;
            str += String.valueOf(bool_to_int);
        }
        else if (type == Type.Type_Char)
        {
            char c = (char) ac.getLiteral().getValue();
            int char_to_int = c;
            str += String.valueOf(char_to_int);
        }
        else
        {
            str += ac.getLiteral().toString();
        }

        str += '\n';
        str += __toStringInstructionStore(ac.getType(), false, ac.getRegister());
        return str;
    }

	public Object visit(IRAssignmentOperation ao)
    {
        // T11 := T8 I- T10;
        Operator op = ao.getOperator();
        Type type = ao.getType();
        Type type_result = type;
        if (op == Operator.Operator_Equals || op == Operator.Operator_Less_Than)
        {
            type_result = Type.Type_Boolean;
        }
        char char_type = __toCharInstructionType(type, false);
        String str = "";

        if (type == Type.Type_String)
        {
            if (op == Operator.Operator_Addition)
            {
                /*
                something like this?
                getstatic java/lang/String Ljava/lang/String;
                aload 0
                invokevirtual java/lang/String/concat(Ljava/lang/String;);
                */

                str += __toStringInstructionLoad(type, false, ao.getRegisterLeft());
                str += __toStringInstructionLoad(type, false, ao.getRegisterRight());

            }
            else if (op == Operator.Operator_Equals)
            {
                str += "ifeq ";
            }
            else
            {
                str += "iflt ";
            }
            return str;
        }
        else if (type == Type.Type_Boolean && op == Operator.Operator_Equals)
        {
            str += __toStringInstructionLoad(type, false, ao.getRegisterLeft());
            str += __toStringInstructionLoad(type, false, ao.getRegisterRight());
            str += TAB;
            str += "ixor\n";
            str += TAB;
            str += "ldc 1\n";
            str += TAB;
            str += "ixor\n";
        }
        else // (type == Type.Type_Integer || type == Type.Type_Char || type == Type.Type_Float)
        {
            str += __toStringInstructionLoad(type, false, ao.getRegisterLeft());
            str += __toStringInstructionLoad(type, false, ao.getRegisterRight());
            str += TAB;
            str += char_type;
            if (op == Operator.Operator_Addition)
            {
                str += "add\n";
            }
            else if (op == Operator.Operator_Subtraction)
            {
                str += "sub\n";
            }
            else if (op == Operator.Operator_Multiply)
            {
                str += "mul\n";
            }
            else // equal or less than
            {
                int label_op_1 = __getLabelOp();
                int label_op_2 = __getLabelOp();
                String str_label_op_1 = __toStringLabelOperation(label_op_1);
                String str_label_op_2 = __toStringLabelOperation(label_op_2);

                // isub
                str += "sub\n";

                // ifeq OP1 || iflt OP1
                str += TAB;
                if (op == Operator.Operator_Equals)
                {
                    str += "ifeq ";
                }
                else
                {
                    str += "iflt ";
                }
                str += str_label_op_1;
                str += '\n';

                // ldc 0
                str += TAB;
                str += "ldc 0\n";

                // goto OP2
                str += TAB;
                str += "goto ";
                str += str_label_op_2;
                str += '\n';

                // OP1: 
                str += __toStringLabel(str_label_op_1);

                // ldc 1
                str += TAB;
                str += "ldc 1\n";

                // OP2:
                str += __toStringLabel(str_label_op_2);
            }
        }
        
        str += __toStringInstructionStore(type_result, false, ao.getRegister());
        return str;
    }

	public Object visit(IRAssignmentRegister ar)
    {
        String str = "";
        int register_lhs = ar.getRegister();
        int register_rhs = ar.getRegisterRight();
        Type type = ar.getType();
        char char_type = __toCharInstructionType(type, false);

        // T0[T2] := T1;
        if (ar.isArray())
        {
            // aload 0
            str += __toStringInstructionLoad(type, true, register_lhs);

            // iload 2
            str += __toStringInstructionLoad(Type.Type_Int, false, ar.getArrayIndex());

            // iload 1
            str += __toStringInstructionLoad(type, false, register_rhs);

            // iastore
            str += TAB;
            str += char_type;
            str += "astore\n";
        }

        // T8 := T0[T7];
        else if (ar.isRegisterRightArray())
        {
            // aload 0
            str += __toStringInstructionLoad(type, true, register_rhs);

            // iload 7
            str += __toStringInstructionLoad(Type.Type_Int, false, ar.getRegisterRightArray());

            // iaload
            str += TAB;
            str += char_type;
            str += "aload\n";

            // istore 8
            str += __toStringInstructionStore(type, false, register_lhs);
        }

        // T0 := T3;
        else
        {
	        // fload 3
            str += __toStringInstructionLoad(type, false, register_rhs);

	        // fstore 0
            str += __toStringInstructionStore(type, false, register_lhs);
        }
        str += '\n';
        return str;
    }
}
