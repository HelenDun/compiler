package visitor;

import java.util.Vector;
import visitor.ir.*;
import visitor.ast.Type;

public class IRVisitorJasmin extends IRVisitor
{
    private String m_name_program;

    private String __toStringLabel(int label)
    {
        return 'L' + String.valueOf(label);
    }

    private String __toStringType(Type type)
    {
        if (type.equals(Type.Type_String))
        {
            return "Ljava/lang/String;";
        }
        return String.valueOf(type.toChar());
    }

    private char __toCharType(Type type, boolean isArray)
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
        m_name_program = program.getName();
        str += m_name_program;
        str += ".ul\n";

        // .class public test_01_example
        str += ".class public ";
        str += m_name_program;
        str += '\n';

        // .super java/lang/Object
        str += ".super java/lang/Object\n\n";

        // .method public static factorial(I)I
        for (IRFunction function : program.getFunctions())
        {
            str += function.accept(this).toString();
        }

        // boilerplate
        /*
        .method public static main([Ljava/lang/String;)V
            .limit locals 1
            .limit stack 4
            invokestatic test_01_example/__main()V
            return
        .end method

        .method public <init>()V
            aload_0
            invokenonvirtual java/lang/Object/<init>()V
            return
        .end method
        */
        str += ".method public static main([Ljava/lang/String;)V\n";
        str += "    .limit locals 1\n";
        str += "    .limit stack 4\n";
        str += "    invokestatic ";
        str += m_name_program;
        str += "/__main()V\n";

        str += "    return\n";
        str += ".end method\n";
        str += '\n';
        str += ".method public <init>()V";
        str += "    aload_0\n";
        str += "    invokenonvirtual java/lang/Object/<init>()V\n";
        str += "    return\n";
        str += ".end method\n";

        return str;
    }

	public Object visit(IRFunction function)
    {
        // .method public static factorial(I)I
        String str = ".method public static ";

        String name = function.getName();
        if (name.equals("main"))
        {
            name = "__main";
        }
        str += name;

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
            str += '[';
        str += __toStringType(function.getType());
        str += '\n';

        // .limit locals 8
        str += ".limit locals ";
        str += String.valueOf(function.getDeclarations().size());
        str += '\n';

        // declarations
        // ex. .var 0 is n I from L_0 to L_1
        String from = __toStringLabel(function.getLabelBegin()) + " to " + __toStringLabel(function.getLabelEnd());
        for (IRDeclaration declaration : function.getDeclarations())
        {
            // .var 0 is n I from L_0 to L_1
            String var = ".var ";
            var += String.valueOf(declaration.getRegister());
            var += "is ";
            var += declaration.getName();
            var += ' ';

            if (declaration.isArray())
            {
                var += '[';
            }

            var += declaration.getType().toChar();
            var += " from ";
            var += declaration.toStringJasmin();
            var += from;
            var += '\n';
            str += var;
        }

        // .limit stack 16
        str += ".limit stack 16\n";

        // label for variable declaration
        IRStatementLabel label_begin = new IRStatementLabel(function.getLabelBegin());
        str += label_begin.accept(this).toString();

        // initialization of declarations
        for (IRDeclaration declaration : function.getDeclarations())
        {
            str += declaration.accept(this).toString();
        }

        // statements
        Vector<IRStatement> statements = function.getStatements();
        for (IRStatement statement : statements)
        {
            str += statement.accept(this).toString();
        }

        // always end with a return
        if (statements.size() > 0 && !statements.lastElement().isReturn() && function.getType() == Type.Type_Void)
        {
            IRStatementReturn ret = new IRStatementReturn();
            str += ret.accept(this).toString();
        }

        // label for variable declaration
        IRStatementLabel label_end = new IRStatementLabel(function.getLabelEnd());
        str += label_end.accept(this).toString();
        
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
        String str = "";
        Type type = declaration.getType();
        if (declaration.isArray() || type == Type.Type_String)
        {
            str += "aconst_null\na";
        }
        else if (type == Type.Type_Float)
        {
            str += "ldc 0.0\nf";
        }
        else
        {
            str += "ldc 0\ni";
        }

        str += "store ";
        str += String.valueOf(declaration.getRegister());
        str += '\n';

        return str;
    }


	public Object visit(IRStatementGoto sg)
    {
        String str = "";

        // iload 2
        // ifne L0
        if (sg.isRegister())
        {
            str += "iload ";
            str += String.valueOf(sg.getRegister());
            str += "\nifne ";
        }
        
        // goto L1
        else
        {
            str += "goto ";
        }

        str += __toStringLabel(sg.getLabel());
        str += '\n';
        return str;
    }
    
	public Object visit(IRStatementLabel sl)
    {
        String str = __toStringLabel(sl.getLabel());
        str += ":\n";
        return str;
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
        String str = "getstatic java/lang/System/out Ljava/io/PrintStream;\n";

        // load parameter
        if (type == Type.Type_String)
        {
            str += 'a';
        }
        else if (type == Type.Type_Float)
        {
            str += 'f';
        }
        else
        {
            str += 'i';
        }

        // invoke print function
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
            char type = __toCharType(sr.getType(), sr.isArray());
            str += type;
            str += "load ";
            str += String.valueOf(sr.getRegister());
            str += '\n';
            str += type;
        }

        str += "return\n";
        return str;
    }


	public Object visit(IRAssignmentArray aa)
    {
        /*
        ldc 3
        newarray int
        astore 0
        */
        String str = "ldc ";
        str += String.valueOf(aa.getSize());
        str += "\nnewarray ";
        str += aa.getType().toString();
        str += "\nastore ";
        str += String.valueOf(aa.getRegister());
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
        char type = __toCharType(ac.getType(), ac.isArray());

        for (Pair<Type, Integer> pair : ac.getParameterRegisters())
        {
            str += __toCharType(pair.getFirst(), false);
            str += "load ";
            str += String.valueOf(pair.getSecond());
            str += '\n';
        }

        // invoke function
        str += "invokestatic ";
        str += m_name_program;
        str += '/';
        str += ac.getName();

        // parameters
        str += '(';
        for (Pair<Type, Integer> pair : ac.getParameterRegisters())
        {
            str += __toStringType(pair.getFirst());
        }
        str += ')';

        // return type
        str += type;
        str += '\n';

        if (!ac.getType().equals(Type.Type_Void))
        {
            str += type;
            str += "store ";
            str += ac.getRegister();
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
        String str = "ldc ";
        str += String.valueOf(ac.getLiteral());
        str += '\n';
        str += __toCharType(ac.getType(), false);
        str += "store ";
        str += String.valueOf(ac.getRegister());
        str += '\n';
        return str;
    }

	public Object visit(IRAssignmentOperation ao)
    {
        // T11 := T8 I- T10;
        char type = __toCharType(ao.getType(), false);

        // iload 8
        String str = "";
        str += type;
        str += "load ";
        str += String.valueOf(ao.getRegisterLeft());
        str += '\n';

        // iload 10
        str += type;
        str += "load ";
        str += String.valueOf(ao.getRegisterRight());
        str += '\n';

        // isub
        str += type;
        switch (ao.getOperator())
        {
            case Operator_Addition:
            {
                str += "add\n";
                break;
            }
            case Operator_Subtraction:
            {
                str += "sub\n";
                break;
            }
            case Operator_Multiply:
            {
                str += "mul\n";
                break;
            }
            /*
            ;		T8 := T6 Z== T7;
            ixor
            ldc 1
            ixor
            */
            case Operator_Equals:
            {
                str += "xor\n";
                str += "ldc 1";
                str += "ixor\n";
                break;
            }
            /*
            isub
            iflt L_2
            ldc 0
            goto L_3
        L_2:
            ldc 1
        L_3:
            istore 4
            */
            case Operator_Less_Than:
            {
                String label_1 = __toStringLabel(ao.getLabel1());
                String label_2 = __toStringLabel(ao.getLabel2());
                str += "sub\n";
                str += "iflt ";
                str += label_1;
                str += "\nldc 0";
                str += "\ngoto ";
                str += label_2;
                str += '\n';
                str += label_1;
                str += ":\nldc 1\n";
                str += label_2;
                str += ":\n";
                break;
            }
            default:
                break;
        }
        str += type;
        str += "store ";
        str += String.valueOf(ao.getRegister());
        str += '\n';
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
        char type = __toCharType(ar.getType(), false);

        // T0[T2] := T1;
        if (ar.isArray())
        {
            // aload 0
            str += "aload ";
            str += register_lhs;
            str += '\n';

            // iload 2
            str += "iload ";
            str += String.valueOf(ar.getArrayIndex());
            str += '\n';

            // iload 1
            str += type;
            str += "load ";
            str += register_rhs;
            str += '\n';

            // iastore
            str += type;
            str += "astore\n";
        }

        // T8 := T0[T7];
        else if (ar.isRegisterRightArray())
        {
            // aload 0
            str += "aload ";
            str += register_rhs;
            str += '\n';

            // iload 7
            str += "iload ";
            str += String.valueOf(ar.getRegisterRightArray());
            str += '\n';

            // iaload
            str += type;
            str += "aload\n";

            // istore 8
            str += type;
            str += "store ";
            str += register_lhs;
        }

        // T0 := T3;
        else
        {
	        // fload 3
            str += type;
            str += "load ";
            str += register_rhs;
            str += '\n';

	        // fstore 0
            str += type;
            str += "store ";
            str += register_lhs;
        }
        str += '\n';
        return str;
    }
}
