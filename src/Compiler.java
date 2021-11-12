
import org.antlr.runtime.*;
import visitor.*;
import visitor.ast.Program;
import visitor.ir.IRProgram;
import java.io.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		// grammar, reprint, type-check, intermediate representation, jasmin file
		boolean grFlag, rpFlag, tcFlag, irFlag, jaFlag;
		boolean wrongNumArgs = (args.length == 0 || args.length > 2);

		// check for flags
		if (args.length == 2)
		{
			grFlag = args[1].equals("-gr");
			rpFlag = args[1].equals("-rp");
			tcFlag = args[1].equals("-tc");
			irFlag = args[1].equals("-ir");
			jaFlag = args[1].equals("-ja");
		}

		// check the arguments are correct
		if (wrongNumArgs || (args.length == 2 && !(rpFlag || tcFlag || irFlag || jaFlag)))
		{
			System.out.println("Usage: Compiler <filename> [-gr|-rp|-tc|-ir|-ja]");
			return;
		}

		// get program name and the path to the file
		String ulPathname;
		String pathname;
		String filename;
		ulPathname = args[0];
		pathname = ulPathname.substring(0, ulPathname.length() - 3);
		filename = pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length());

		// parse file with Antlr grammar
		input = new ANTLRInputStream(new FileInputStream(ulPathname));
		ulActionsLexer lexer = new ulActionsLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ulActionsParser parser = new ulActionsParser(tokens);

		try 
		{
			Program p = parser.program();
			String output = null;
			String extension = null;

			// grammar
			if (grFlag)
			{
				// do nothing
			}

			// reprint
			else if (rpFlag)
			{
				VisitorPrettyPrint rpv = new VisitorPrettyPrint();
				output = p.accept(rpv).toString();
				extension = ".rp";
			}

			// type-check
			else if (tcFlag)
			{
				VisitorType tcv = new VisitorType();
				p.accept(tcv);
			}

			// intermediate representation
			else if (irFlag)
			{
				// type check and get the environment of the file
				VisitorType tcv = new VisitorType();
				Environment<ElementFunction> env_func = (Environment<ElementFunction>) p.accept(tcv);

				// convert the file into an intermediate representation
				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(filename, env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);

				output = irprogram.toString();
				extension = ".ir";
			}

			// jasmin file (default output)
			else
			{
				// type check and get the environment of the file
				VisitorType tcv = new VisitorType();
				Environment<ElementFunction> env_func = (Environment<ElementFunction>) p.accept(tcv);

				// create an intermediate representation of the file
				VisitorIntermediateRepresentation irv = new VisitorIntermediateRepresentation(filename, env_func);
				IRProgram irprogram = (IRProgram) p.accept(irv);

				// create a jasmin file from the intermediate representation
				IRVisitorJasmin jav = new IRVisitorJasmin();

				output = jav.visit(irprogram).toString();
				extension = ".j";
			}

			if (output != null)
			{
				FileWriter out = new FileWriter("../output/" + filename + extension);
				out.write(output);
				out.close();
			}
		}
		catch (RecognitionException e)	
		{
			// Oopsies
		}
		catch (SemanticException e)
		{
			System.out.println(e.getMessage());
			if (tcFlag)
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
