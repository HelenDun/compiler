DIR_SRC= .
DIR_AST= $(DIR_SRC)/ast
DIR_TESTS= $(DIR_SRC)/tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid

GNAME= ulActions
GSRC= $(GNAME).g

.PHONY: all grammar compiler test clean

all: grammar compiler test

grammar: $(GSRCS)
	java org.antlr.Tool -fo . $(GSRC)

compile: compile_ast compile_root

compile_root:
	javac *.java

compile_ast:
	javac $(DIR_AST)/*.java

clean: clean_root clean_ast

clean_root:
	rm -f *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens

clean_ast:
	rm -f $(DIR_AST)/*.class

test: test_parser

test_parser: grammar_tests_runnable grammar_tests_parsable grammar_tests_invalid

# java Compiler ./tests/runnable/$(*).ul
test_parser_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
test_parser_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
test_parser_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)*.ul), echo; echo $(file) ; java Compiler $(file);)
