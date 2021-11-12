DIR_SRC= ./src
DIR_VST= $(DIR_SRC)/visitor
DIR_AST= $(DIR_VST)/ast
DIR_IR= $(DIR_VST)/ir
DIR_TESTS= ./tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid
DIR_OUTPUT= ./output
DIR_BUILD= ./build

all: grammar compile

GNAME= $(DIR_SRC)/hdActions
grammar:
	java -cp '.:antlr.jar' org.antlr.Tool -fo $(DIR_SRC) $(GNAME).g

compile:
	javac -cp '.:antlr.jar' -d $(DIR_BUILD) $(DIR_SRC)/*.java $(DIR_VST)/*.java $(DIR_AST)/*.java $(DIR_IR)/*.java

clean: clean_grammar clean_build clean_output 
clean_output: 
	rm -r $(DIR_OUTPUT)/*
clean_build: 
	rm -r $(DIR_BUILD)/*
clean_grammar: 
	rm -f $(DIR_SRC)/*.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens

# java -cp 'antlr.jar:build' Compiler ./tests/runnable/some_file.hd -gr
grammar_test: grammar_test_runnable grammar_test_parsable grammar_test_invalid
grammar_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.hd), echo; echo $(file); java -cp 'antlr.jar:build' Compiler $(file) -gr;)
grammar_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -gr;)
grammar_test_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -gr;)

# java -cp 'antlr.jar:build' Compiler ./tests/runnable/some_file.hd -rp
rp_test: rp_test_runnable rp_test_parsable rp_test_ultimate
rp_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -rp;)
rp_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -rp;)
rp_test_ultimate:
	java -cp 'antlr.jar:build' Compiler ./tests/runnable/test_41_pretty_print_ugly.hd -rp
	java -cp 'antlr.jar:build' Compiler ./tests/runnable/test_42_pretty_print_expected.hd -rp
	diff $(DIR_OUTPUT)/test_42_pretty_print_expected.rp ./tests/runnable/test_42_pretty_print_expected.hd
	diff $(DIR_OUTPUT)/test_41_pretty_print_ugly.rp ./tests/runnable/test_42_pretty_print_expected.hd

# java -cp 'antlr.jar:build' Compiler ./tests/runnable/some_file.hd -tc
tc_test: tc_test_runnable tc_test_parsable
tc_test_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -tc;)
tc_test_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.hd), echo; echo $(file) ; java -cp 'antlr.jar:build' Compiler $(file) -tc;)

# java -cp 'antlr.jar:build' Compiler ./tests/runnable/some_file.hd -ir
ir_test:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.hd), echo; echo $(file); java -cp 'antlr.jar:build' Compiler $(file) -ir;)

# java -cp 'antlr.jar:build' Compiler ./tests/runnable/some_file.hd -ja
ja_test:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.hd), echo; echo $(file); java -cp 'antlr.jar:build' Compiler $(file) -ja;)
	
ja_executable:
	$(foreach file, $(wildcard $(DIR_OUTPUT)/*.j), echo; echo $(file); java -jar jasmin-2.4/jasmin.jar -d $(DIR_OUTPUT) $(file);)

