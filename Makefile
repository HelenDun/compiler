DIR_SRC= .
DIR_AST= $(DIR_SRC)/ast
DIR_TESTS= $(DIR_SRC)/tests
DIR_TESTS_RUNNABLE= $(DIR_TESTS)/runnable
DIR_TESTS_PARSABLE= $(DIR_TESTS)/parsable
DIR_TESTS_INVALID= $(DIR_TESTS)/invalid

GNAME= ulActions
GSRC= $(GNAME).g

all: grammar compile g_t ppv_t

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
g_t: g_t_r g_t_p g_t_i
g_t_r: # grammar test runnable
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file);)
g_t_p: # grammar test parsable
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file);)
g_t_i: # grammar test invalid
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)/*.ul), echo; echo $(file) ; java Compiler $(file);)

# java Compiler ./tests/runnable/some_file.ul -ppv
ppv_t: ppv_t_r ppv_t_p
ppv_t_r:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv;)
ppv_t_p:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)/*.ul), echo; echo $(file) ; java Compiler $(file) -ppv;)