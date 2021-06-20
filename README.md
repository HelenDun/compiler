
Compiler for the Unnamed Language

Author: Helen Dun
VNum: V00912482
Email: dunhelen@uvic.ca


**How to Run the Compiler:**

1. Ensure antlr.jar is installed on your system.
2. Turn the ulActions.g into usable coding using the following command:
    make grammar
2. Compile all files using the following commands:
    make compile
3. Run the Compiler program using the following command:
    java Compiler test_file [-ppv|-tcv]
Note. For step 3, run 'make test' for an automated testing.


**Tests**

There are 3 categories of tests: runnable, parsable and invalid. Runnable tests are .ul programs that can be parsed by the grammar and compiled by the Compiler. Parsable tests are .ul programs that can be parsed by the grammar but will throw an error during compilation due to issues such as type-checking. Invalid tests are false .ul programs that cannot be parsed by the grammar and are not within the Unnamed Language (and so can also not be compiled).

To test the parsing ability of the Compiler, either run the 'make grammar_test' command or run the Compiler program without the ppv command like so:
    java Compiler test_file.ul

To test the pretty print visitor ability of the Compiler, either run the 'make ppv_test' command to create pretty print vistor versions of all parsable test cases, or run the Compiler program with the ppv command like so:
    java Compiler test_file.ul -ppv

To test the type check visitor ability of the Compiler, either run the 'make tcv_test' command to out put any type-check errors for all parsable test cases, or run the Compiler program with the tcv command like so:
    java Compiler test_file.ul -tcv


**The Makefile**

The Makefile has several commands:
    make grammar
    make compile
    make test
    make clean

The 'grammar' command reads in the ulActions.g file and creates Lexer.java, Parser.java and .tokens files for the grammar.

The 'compile' command uses the 'compile_root' and 'compile_ast' commands. 'compile_root' will compile all files in the top-level directory such as Compiler.java. 'compile_ast' will compile all files in the ast/ folder, which contains the Visitor classes and Abstract Syntax Tree classes.

The 'test' command uses the 'grammar_test', 'ppv_test', 'tcv_test' commands. 'grammar_test' tests whether the Compiler program can parse .ul programs. 'ppv_test' tests whether the Compiler program can read in a parsable .ul program, create an Abstract Syntax Tree, and re-output the .ul program in the PrettyPrintVisitor format. 'tcv_test' tests whether the Compiler can type-check an input .ul program correctly.

The 'clean' command removes all files created by the previous commands.