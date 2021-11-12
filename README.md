
***Compiler for the HD Language***

*Author: Helen Dun*

*Email: helen@dun.org*

**Pre-Requirements**
- Unix Operating System
- Java

**How to Run the Compiler**

To compile, run the command:

    make all

To execute, run the command:

    java  -cp 'antlr.jar:build' Compiler <file_name> [-gr|-rp|-tc|-ir|-ja]

When no tag parameters are added, the Compiler executable will default use the -ja tag and output a .j file. 

The -gr tag stands for 'grammar' and will output any errors with the grammar of the file. It will output nothing if the grammar does not encounter any errors.

The -rp tag stands for 'reprint' and will output a nicely formatted version of the input file. This  is not a part of the compilation process but is used to check that the grammar works as desired.

The -tc tag stands for 'type-check' and will output any errors with the types of variables and operations. It will output nothing if the type-check does not encounter any errors. 

The -ir tag stands for 'intermediate representation' and will output a .ir file. The .ir files are similar to assembly code and are for checking that the intermediate representation is correct.

The -ja tag stands for 'jasmin' and will output a .j file. The external Jasmin program can take the .j file as input and output an executable java file. Thus the Jackets language can finally be executed in the JVM.



**Tests**

There are 3 categories of test files: runnable, parsable and invalid. Runnable tests are .hd programs that can be parsed by the grammar and compiled by the Compiler. Parsable tests are .hd programs that can be parsed by the grammar but will throw an error during compilation due to issues such as type-checking. Invalid tests are false .hd programs that cannot be parsed by the grammar and are not within the Jackets Language (and so can also not be compiled).

The Makefile has many automated unit tests that use the different tag parameters. Make sure to always 'make clean' and 'make all' before running the unit tests.

For the 'make grammar_test' command, the grammar should output mostly nothing on runnable and parsable test files but fail on invalid test files.

For the 'make rp_test' command, the Compiler should output reprinted versions of all runnable and parsable tests into the output folder.

For the 'make tc_test' command, the Compiler should output nothing for all runnable tests and errors for all parsable tests.

For the 'make ir_test' and 'make ja_test' commands, the Compiler should output .ir and .j files for all runnable tests into the output folder.

For the 'make ja_executable' command, the Jasmin executable should output java executable files for all .j files in the output folder.

For full compilation testing, the user should run the 'make ja_test' and 'make ja_executable' commands in that order.

**Acknowledgements**
This project uses the "compiler-compiler" Antlr v3.0 (https://www.antlr3.org/) and the Jasmin program (https://github.com/jasmin-lang/jasmin/wiki). I take no credit for the antlr.jar file or jasmin-2.4 folder in my project.
