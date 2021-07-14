
import org.antlr.runtime.*;
import visitor.*;
import visitor.ast.Program;
import visitor.ir.IRProgram;
import java.io.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		boolean wrongNumArgs = (args.length == 0 || args.length > 2);
		boolean ppvFlag = false;
		boolean tcvFlag = false;
		boolean irFlag = false;
		if (args.length == 2)
		{
			ppvFlag = args[1].equals("-ppv");
			tcvFlag = args[1].equals("-tcv");
			irFlag = args[1].equals("-ir");
		}

		if (wrongNumArgs || (args.length == 2 && !(ppvFlag || tcvFlag || irFlag)))
		{
			System.out.println("Usage: Compiler filename.ul [-ppv|-tcv|-ir]");
			return;
		}
		else 
		{
			input = new ANTLRInputStream(new FileInputStream(args[0]));
		}

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
	
				String ulPathname = args[0];
				ulPathname = ulPathname.substring(0, ulPathname.length()-3);
				ulPathname += "_ppv.ul";
	
				FileWriter output = new FileWriter(ulPathname);
				output.write(sOutput);
				output.close();

				System.out.println(sOutput);
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
				
				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(args[0], env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);
				System.out.println(irprogram.toString());
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
