package visitor;

import java.util.Vector;
import visitor.ir.*;
import visitor.ast.Type;

public class IRVisitorJasmin extends IRVisitor
{
    private final String TAB = "    ";
    private final String NEWLINETAB = "\n    ";
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
        return 'O' + String.valueOf(label);
    }

    private String __toStringInstructionLoad(Type type, bool isArray, int register)
    {
        String str = TAB;
        str += __toCharInstructionType(type, isArray);
        str += "load ";
        str += String.valueOf(register);
        str += '\n';
        return str;
    }

    private String __toStringInstructionStore(Type type, bool isArray, int register)
    {
        String str = TAB;
        str += __toCharInstructionType(type, isArray);
        str += "store ";
        str += String.valueOf(register);
        str += '\n';
        return str;
    }

    private String __toStringType(Type type)
    {
        if (type.equals(Type.Type_String))
        {
            return "Ljava/lang/String;";
        }
        return String.valueOf(type.toChar());
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

        String str_init = ".method public <init>()V";
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
        if (function.isArray())
        {
            str += '[';
        }
        str += __toStringType(function.getType());
        str += '\n';

        int label_start = __getLabelFunc();
        int label_end = __getLabelFunc();
        String str_label_start = __toStringLabelFunc(label_start);
        String str_label_end = __toStringLabelFunc(label_end);

        // .limit locals 8
        String str_decs = TAB;
        str_decs += ".limit locals ";
        str_decs += String.valueOf(function.getDeclarations().size());
        str_decs += '\n';

        // declarations
        // ex. .var 0 is n I from L_0 to L_1
        String str_range = ' ' + str_label_start + " to " + str_label_end;
        for (IRDeclaration declaration : function.getDeclarations())
        {
            String var = TAB;
            var += ".var ";
            var += String.valueOf(declaration.getRegister());
            var += " is ";
            var += declaration.getName();
            var += ' ';

            if (declaration.isArray())
            {
                var += '[';
            }

            var += declaration.getType().toChar();
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
        str_inits = __tabBlock(str_inits);
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
        if (statements.size() > 0 && !statements.lastElement().isReturn() && function.getType() == Type.Type_Void)
        {
            IRStatementReturn ret = new IRStatementReturn();
            str += ret.accept(this).toString();
        }

        // label for variable declaration
        str += __toStringLabel(str_label_end);
        str += '\n';
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
        if (declaration.isArray() || declaration.getType() == Type.Type_String)
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

        str += __toStringLabel(sg.getLabel());
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
        str += __toStringType(type);
        str += ");\n";
        
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
        str += TAB;
        str += "newarray ";
        str += aa.getType().toString();
        str += '\n';

        // astore 0
        str += __toStringInstructionStore(aa.getType(), true, aa.getRegister());
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
            str += __toStringType(pair.getFirst());
        }
        str += ')';

        // return type
        str += __toCharInstructionType(ac.getType(), ac.isArray());
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
        String str = "    ldc ";
        str += String.valueOf(ac.getLiteral());
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

        // iload 8
        String str = __toStringInstructionLoad(type, false, ao.getRegisterLeft());

        // iload 10
        str += __toStringInstructionLoad(type, false, ao.getRegisterRight());

        if (type == Type.Type_Boolean)
        {
            /*
            ;		T8 := T6 Z== T7;
            ixor
            ldc 1
            ixor
            */

        }
        else if (type == Type.Type_String)
        {

        }
        else // (type == Type.Type_Integer || type == Type.Type_Char || type == Type.Type_Float)
        {
            str += TAB;
            str += char_type;
            if (op == Operator.Operator_Addition)
            {
                str += "add";
            }
            else if (op == Operator.Operator_Subtraction)
            {
                str += "sub";
            }
            else if (op == Operator.Operator_Multiply)
            {
                str += "mul";
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
            str += '\n';
        }
        str += __toStringInstructionStore(type_result, false, String.valueOf(ao.getRegister()));
        return str;
    }

	public Object visit(IRAssignmentRegister ar)
    {
        /*
        ;		T0 := T3;
        fload 3
        fstore 0
        ;		T0[T2] := T1;
        aload 0
        iload 2
        iload 1
        iastore
        ;		T8 := T0[T7];
        aload 0
        iload 7
        iaload
        istore 8
        */

        String str = "";
        String register_lhs = String.valueOf(ar.getRegister());
        String register_rhs = String.valueOf(ar.getRegisterRight());
        char type = __toCharInstructionType(ar.getType(), false);

        // T0[T2] := T1;
        if (ar.isArray())
        {
            // aload 0
            str += "    aload ";
            str += register_lhs;
            str += '\n';

            // iload 2
            str += "    iload ";
            str += String.valueOf(ar.getArrayIndex());
            str += '\n';

            // iload 1
            str += "    ";
            str += type;
            str += "load ";
            str += register_rhs;
            str += '\n';

            // iastore
            str += "    ";
            str += type;
            str += "astore\n";
        }

        // T8 := T0[T7];
        else if (ar.isRegisterRightArray())
        {
            // aload 0
            str += "    aload ";
            str += register_rhs;
            str += '\n';

            // iload 7
            str += "    iload ";
            str += String.valueOf(ar.getRegisterRightArray());
            str += '\n';

            // iaload
            str += type;
            str += "    aload\n";

            // istore 8
            str += "    ";
            str += type;
            str += "store ";
            str += register_lhs;
        }

        // T0 := T3;
        else
        {
	        // fload 3
            str += "    ";
            str += type;
            str += "load ";
            str += register_rhs;
            str += '\n';

	        // fstore 0
            str += "    ";
            str += type;
            str += "store ";
            str += register_lhs;
        }
        str += '\n';
        return str;
    }
}
