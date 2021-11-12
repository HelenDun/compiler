DIR_SRC= ./src
DIR_SRC_VST= $(DIR_SRC)/visitor
DIR_SRC_AST= $(DIR_VST)/ast
DIR_SRC_IR= $(DIR_VST)/ir
DIR_TESTS= ./tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid
DIR_OUTPUT= ./output
DIR_BUILD= ./build

all: grammar compile

GNAME= $(DIR_SRC)/ulActions
grammar:
	java org.antlr.Tool -fo . $(GNAME).g

compile: compile_ir compile_ast compile_vst compile_root
compile_root:
	javac -d $(DIR_BUILD) *.java
compile_vst:
	javac -d $(DIR_BUILD) $(DIR_VST)/*.java
compile_ast:
	javac -d $(DIR_BUILD) $(DIR_AST)/*.java
compile_ir:
	javac -d $(DIR_BUILD) $(DIR_IR)/*.java

clean: clean_output clean_build clean_grammar
clean_output: rm $(DIR_OUTPUT)/*
clean_build: rm $(DIR_BUILD)/*
clean_grammar: rm $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens

# java Compiler ./tests/runnable/some_file.ul
grammar_test: grammar_test_runnable grammar_test_parsable grammar_test_invalid
grammar_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)/Compiler $(file) -gr; echo;)
grammar_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -gr; echo;)
grammar_test_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -gr; echo;)

# java Compiler ./tests/runnable/some_file.ul -rp
rp_test: rp_test_runnable rp_test_parsable rp_test_ultimate
rp_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -rp; echo;)
rp_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -rp; echo;)
rp_test_ultimate:
	java $(DIR_BUILD)Compiler ./tests/runnable/test_41_pretty_print_ugly.ul -rp
	java $(DIR_BUILD)Compiler ./tests/runnable/test_42_pretty_print_expected.ul -rp
	diff $(DIR_OUTPUT)/test_42_pretty_print_expected.rp ./tests/runnable/test_42_pretty_print_expected.ul
	diff $(DIR_OUTPUT)/test_41_pretty_print_ugly.rp ./tests/runnable/test_42_pretty_print_expected.ul

tc_test: tc_test_runnable tc_test_parsable
tc_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -tc; echo;)
tc_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java $(DIR_BUILD)Compiler $(file) -tc; echo;)

ir_test:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file); java $(DIR_BUILD)Compiler $(file) -ir; echo;)

ja_test:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file); java $(DIR_BUILD)Compiler $(file) -ja; echo;)
	
ja_executable:
	$(foreach file, $(wildcard $(DIR_OUTPUT)/*.j), echo; echo $(file); java -jar jasmin-2.4/jasmin.jar $(file); echo;)

