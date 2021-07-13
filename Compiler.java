
import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import static ast.Type.*;
import static ast.Operator.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		boolean wrongNumArgs = (args.length == 0 || args.length > 2);
		boolean ppvFlag = false;
		if (args.length == 2)
		{
			ppvFlag = args[1].equals("-ppv");
		}

		if (wrongNumArgs || (args.length == 2 && !ppvFlag))
		{
			System.out.println("Usage: Compiler filename.ul [-ppv|-tcv]");
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
			else
			{
				VisitorType tcv = new VisitorType();
				p.accept(tcv);
				
			}
		}
		catch (RecognitionException e)	
		{
			// Oopsies
		}
		catch (SemanticException e)
		{
			System.out.println(e.getMessage());
			/*
			System.out.println("");
			System.out.println(e.getCurrentFunction());
			System.out.println(e.getFunctionEnvironment());
			System.out.println(e.getVariableEnvironment());
			e.printStackTrace();
			*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
