
import org.antlr.runtime.*;
import visitor.*;
import visitor.ast.Program;
import visitor.ir.IRProgram;
import java.io.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		boolean wrongNumArgs = (args.length == 0 || args.length > 2);
		boolean gFlag = false;
		boolean ppvFlag = false;
		boolean tcvFlag = false;
		boolean irFlag = false;
		boolean jFlag = false;
		if (args.length == 2)
		{
			gFlag = gFlag | args[1].equals("-g");
			ppvFlag = ppvFlag | args[1].equals("-ppv");
			tcvFlag = tcvFlag | args[1].equals("-tcv");
			irFlag = irFlag | args[1].equals("-ir");
			jFlag = jFlag | args[1].equals("-j");
		}
		if (wrongNumArgs || (args.length == 2 && !(gFlag || ppvFlag || tcvFlag || irFlag || jFlag)))
		{
			System.out.println("Usage: Compiler filename.ul [-ppv|-tcv|-ir]");
			return;
		}

		// get program name and the path to the file
		String ulPathname;
		String pathname;
		String filename;
		ulPathname = args[0];
		pathname = ulPathname.substring(0, ulPathname.length() - 3);
		filename = pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length());

		input = new ANTLRInputStream(new FileInputStream(ulPathname));

		ulActionsLexer lexer = new ulActionsLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ulActionsParser parser = new ulActionsParser(tokens);

		try 
		{
			Program p = parser.program();

			if (ppvFlag)
			{
				VisitorPrettyPrint ppv = new VisitorPrettyPrint();
				String sOutput = p.accept(ppv).toString();
	
				FileWriter output = new FileWriter(pathname + ".ppv");
				output.write(sOutput);
				output.close();
			}
			else if (tcvFlag)
			{
				VisitorType tcv = new VisitorType();
				p.accept(tcv);
			}
			else if (irFlag)
			{
				VisitorType tcv = new VisitorType();
				Environment<ElementFunction> env_func = (Environment<ElementFunction>) p.accept(tcv);

				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(filename, env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);
				String sOutput = irprogram.toString();
				
				FileWriter output = new FileWriter(pathname + ".ir");
				output.write(sOutput);
				output.close();
			}
			else if (jFlag)
			{
				VisitorType tcv = new VisitorType();
				Environment<ElementFunction> env_func = (Environment<ElementFunction>) p.accept(tcv);

				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(filename, env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);

				IRVisitorJasmin jv = new IRVisitorJasmin();
				String sOutput = jv.visit(irprogram).toString();
				
				FileWriter output = new FileWriter(pathname + ".j");
				output.write(sOutput);
				output.close();
			}
			else if (!gFlag)
			{
				VisitorType tcv = new VisitorType();
				Environment<ElementFunction> env_func = (Environment<ElementFunction>) p.accept(tcv);

				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(filename, env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);

				IRVisitorJasmin jv = new IRVisitorJasmin();
				System.out.println(jv.visit(irprogram).toString());
			}
		}
		catch (RecognitionException e)	
		{
			// Oopsies
		}
		catch (SemanticException e)
		{
			System.out.println(e.getMessage());
			if (tcvFlag)
			{
				System.out.println("");
				System.out.println(e.getCurrentFunction());
				System.out.println(e.getFunctionEnvironment());
				System.out.println(e.getVariableEnvironment());
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
