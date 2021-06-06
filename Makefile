DIR_SRC= .
DIR_AST= $(DIR_SRC)/ast
DIR_TESTS= $(DIR_SRC)/tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid

GNAME= ulActions
GSRC= $(GNAME).g

all: grammar compile

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

# java Compiler ./tests/runnable/some_file.ul
grammar_test: grammar_test_runnable grammar_test_parsable grammar_test_invalid
grammar_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file);)
grammar_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file);)
grammar_test_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)/*.ul), echo; echo $(file) ; java Compiler $(file);)

# java Compiler ./tests/runnable/some_file.ul -ppv
ppv_test: ppv_test_runnable ppv_test_parsable ppv_test_ultimate
ppv_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv;)
ppv_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv;)
ppv_test_ultimate:
	java Compiler ./tests/runnable/test_41_pretty_print_ugly.ul -ppv
	java Compiler ./tests/runnable/test_42_pretty_print_expected.ul -ppv
	diff ./tests/runnable/test_42_pretty_print_expected_ppv.ul ./tests/runnable/test_42_pretty_print_expected.ul
	diff ./tests/runnable/test_41_pretty_print_ugly_ppv.ul ./tests/runnable/test_42_pretty_print_expected.ul