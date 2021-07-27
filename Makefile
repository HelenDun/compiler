DIR_SRC= .
DIR_VST= $(DIR_SRC)/visitor
DIR_AST= $(DIR_VST)/ast
DIR_IR= $(DIR_VST)/ir
DIR_TESTS= $(DIR_SRC)/tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid
PPV_FILE= _ppv

GNAME= ulActions
GSRC= $(GNAME).g

all: grammar compile

grammar: $(GSRCS)
	java org.antlr.Tool -fo . $(GSRC)

compile: compile_ir compile_ast compile_vst compile_root
compile_root:
	javac *.java
compile_vst:
	javac $(DIR_VST)/*.java
compile_ast:
	javac $(DIR_AST)/*.java
compile_ir:
	javac $(DIR_IR)/*.java

clean: clean_root clean_vst clean_ast clean_ir clean_test
clean_root:
	rm -f *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens
clean_vst:
	rm -f $(DIR_VST)/*.class
clean_ast:
	rm -f $(DIR_AST)/*.class
clean_ir:
	rm -f $(DIR_IR)/*.class
clean_test: clean_test_ppv clean_test_ir
clean_test_ppv:
	rm -f $(DIR_TESTS_RUNNABLE)/*.ppv $(DIR_TESTS_PARSABLE)/*.ppv
clean_test_ir:
	rm -f $(DIR_TESTS_RUNNABLE)/*.ir $(DIR_TESTS_RUNNABLE)/*.j $(DIR_TESTS_RUNNABLE)/*.class

test: grammar_test ppv_test tcv_test ir_test

# java Compiler ./tests/runnable/some_file.ul
grammar_test: grammar_test_runnable grammar_test_parsable grammar_test_invalid
grammar_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -g; echo;)
grammar_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -g; echo;)
grammar_test_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)/*.ul), echo; echo $(file) ; java Compiler $(file) -g; echo;)

# java Compiler ./tests/runnable/some_file.ul -ppv
ppv_test: ppv_test_runnable ppv_test_parsable ppv_test_ultimate
ppv_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv; echo;)
ppv_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv; echo;)
ppv_test_ultimate:
	java Compiler ./tests/runnable/test_41_pretty_print_ugly.ul -ppv
	java Compiler ./tests/runnable/test_42_pretty_print_expected.ul -ppv
	diff ./tests/runnable/test_42_pretty_print_expected_ppv.ul ./tests/runnable/test_42_pretty_print_expected.ul
	diff ./tests/runnable/test_41_pretty_print_ugly_ppv.ul ./tests/runnable/test_42_pretty_print_expected.ul

tcv_test: tcv_test_runnable tcv_test_parsable
tcv_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -tcv; echo;)
tcv_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -tcv; echo;)

ir_test: clean_test_ir ir_test_compiler ir_test_codegen ir_test_jasmin
ir_test_compiler:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file); java Compiler $(file) -ir; echo;)
ir_test_codegen:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ir), echo; echo $(file); ./codegen --file=$(file) > $(file).j; echo;)
ir_test_jasmin:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.j), echo; echo $(file); java -jar ~/jasmin-2.4/jasmin.jar $(file); echo;)
