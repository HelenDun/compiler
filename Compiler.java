
import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import static ast.Type.*;
import static ast.Operator.*;

public class Compiler {
	public static void main (String[] args) throws Exception {
		ANTLRInputStream input;

		if (args.length == 0) 
		{
			System.out.println("Usage: Compiler filename.ul [-ppv]");
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

			if (args[1] == "-ppv")
			{
				PrettyPrintVisitor ppv = new PrettyPrintVisitor();
				String sOutput = p.accept(ppv).toString();
	
				String ulPathname = args[0];
				ulPathname = ulPathname.substring(0, ulPathname.length()-2);
				ulPathname += "_ppv.ul";
	
				FileWriter output = new FileWriter(ulPathname);
				output.write(sOutput);
				output.close();
			}
		}
		catch (RecognitionException e )	
		{
			// Oopsies
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
