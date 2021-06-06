DIR_SRC= .
DIR_GRAMMAR= $(DIR_SRC)/grammar
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

compiler:
	javac *.java

clean:
	rm *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens

test: grammar_tests

grammar_tests: grammar_tests_runnable grammar_tests_parsable grammar_tests_invalid

# java Compiler ./tests/runnable/$(*).ul
grammar_tests_runnable:
	$(foreach file, $(wildcard $(DIR_TESTS_RUNNABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
grammar_tests_parsable:
	$(foreach file, $(wildcard $(DIR_TESTS_PARSABLE)*.ul), echo; echo $(file) ; java Compiler $(file);)
grammar_tests_invalid:
	$(foreach file, $(wildcard $(DIR_TESTS_INVALID)*.ul), echo; echo $(file) ; java Compiler $(file);)
