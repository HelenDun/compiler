DIR_SRC= .
DIR_AST= $(DIR_SRC)/ast
DIR_TESTS= $(DIR_SRC)/tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid

GNAME= ulActions
GSRC= $(GNAME).g

all: grammar compiler test

grammar: $(GSRCS)
	java org.antlr.Tool -fo . $(GSRC)

compile: compile_ast compile_root

compile_root:
	javac *.java

compile_ast:
	javac $(DIR_AST)/*.java

clean: clean_root clean_ast clean_test

clean_root:
	rm -f *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens

clean_ast:
	rm -f $(DIR_AST)/*.class

clean_test:
	rm -f $(DIR_TESTS_RUNNABLE)/*_ppv.ul $(DIR_TESTS_PARSABLE)/*_ppv.ul $(DIR_TESTS_INVALID)/*_ppv.ul

test: test_runnable test_parsable test_invalid

# java Compiler ./tests/runnable/$(*).ul
test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
test_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)*.ul), echo; echo $(file) ; java Compiler $(file);)
