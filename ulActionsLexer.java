// $ANTLR 3.5.2 ulActions.g 2021-07-13 23:54:11

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ulActionsLexer extends Lexer {
	public static final int EOF=-1;
	public static final int ADDITION=4;
	public static final int ASSIGN=5;
	public static final int BOOLEAN=6;
	public static final int BRACKET_LEFT=7;
	public static final int BRACKET_RIGHT=8;
	public static final int CHAR=9;
	public static final int CHARACTER=10;
	public static final int CHARACTERCONSTANT=11;
	public static final int COLON=12;
	public static final int COMMA=13;
	public static final int COMMENT=14;
	public static final int CURLY_LEFT=15;
	public static final int CURLY_RIGHT=16;
	public static final int DIGIT=17;
	public static final int ELSE=18;
	public static final int EQUALS=19;
	public static final int EXCLAMATION=20;
	public static final int FALSE=21;
	public static final int FLOAT=22;
	public static final int FLOATCONSTANT=23;
	public static final int ID=24;
	public static final int IF=25;
	public static final int INT=26;
	public static final int INTEGERCONSTANT=27;
	public static final int LESS_THAN=28;
	public static final int LETTER=29;
	public static final int MULTIPLY=30;
	public static final int NEWLINE=31;
	public static final int NONZERO=32;
	public static final int PAREN_LEFT=33;
	public static final int PAREN_RIGHT=34;
	public static final int PERIOD=35;
	public static final int PRINT=36;
	public static final int PRINTLN=37;
	public static final int QUOTE_DOUBLE=38;
	public static final int QUOTE_SINGLE=39;
	public static final int RETURN=40;
	public static final int SEMICOLON=41;
	public static final int SPACE=42;
	public static final int STRING=43;
	public static final int STRINGCONSTANT=44;
	public static final int SUBTRACTION=45;
	public static final int TAB=46;
	public static final int TRUE=47;
	public static final int UNDERSCORE=48;
	public static final int VOID=49;
	public static final int WHILE=50;
	public static final int WS=51;
	public static final int ZERO=52;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ulActionsLexer() {} 
	public ulActionsLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ulActionsLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "ulActions.g"; }

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:216:5: ( 'int' )
			// ulActions.g:216:7: 'int'
			{
			match("int"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:217:7: ( 'float' )
			// ulActions.g:217:9: 'float'
			{
			match("float"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "CHAR"
	public final void mCHAR() throws RecognitionException {
		try {
			int _type = CHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:218:6: ( 'char' )
			// ulActions.g:218:8: 'char'
			{
			match("char"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHAR"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:219:8: ( 'string' )
			// ulActions.g:219:10: 'string'
			{
			match("string"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException {
		try {
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:220:9: ( 'boolean' )
			// ulActions.g:220:11: 'boolean'
			{
			match("boolean"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN"

	// $ANTLR start "VOID"
	public final void mVOID() throws RecognitionException {
		try {
			int _type = VOID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:221:6: ( 'void' )
			// ulActions.g:221:8: 'void'
			{
			match("void"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VOID"

	// $ANTLR start "TRUE"
	public final void mTRUE() throws RecognitionException {
		try {
			int _type = TRUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:223:6: ( 'true' )
			// ulActions.g:223:8: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TRUE"

	// $ANTLR start "FALSE"
	public final void mFALSE() throws RecognitionException {
		try {
			int _type = FALSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:224:7: ( 'false' )
			// ulActions.g:224:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FALSE"

	// $ANTLR start "IF"
	public final void mIF() throws RecognitionException {
		try {
			int _type = IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:225:4: ( 'if' )
			// ulActions.g:225:6: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF"

	// $ANTLR start "ELSE"
	public final void mELSE() throws RecognitionException {
		try {
			int _type = ELSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:226:6: ( 'else' )
			// ulActions.g:226:8: 'else'
			{
			match("else"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELSE"

	// $ANTLR start "WHILE"
	public final void mWHILE() throws RecognitionException {
		try {
			int _type = WHILE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:227:7: ( 'while' )
			// ulActions.g:227:9: 'while'
			{
			match("while"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WHILE"

	// $ANTLR start "PRINT"
	public final void mPRINT() throws RecognitionException {
		try {
			int _type = PRINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:228:7: ( 'print' )
			// ulActions.g:228:9: 'print'
			{
			match("print"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINT"

	// $ANTLR start "PRINTLN"
	public final void mPRINTLN() throws RecognitionException {
		try {
			int _type = PRINTLN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:229:9: ( 'println' )
			// ulActions.g:229:11: 'println'
			{
			match("println"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINTLN"

	// $ANTLR start "RETURN"
	public final void mRETURN() throws RecognitionException {
		try {
			int _type = RETURN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:230:8: ( 'return' )
			// ulActions.g:230:10: 'return'
			{
			match("return"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RETURN"

	// $ANTLR start "ASSIGN"
	public final void mASSIGN() throws RecognitionException {
		try {
			int _type = ASSIGN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:232:8: ( '=' )
			// ulActions.g:232:10: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASSIGN"

	// $ANTLR start "SEMICOLON"
	public final void mSEMICOLON() throws RecognitionException {
		try {
			int _type = SEMICOLON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:233:11: ( ';' )
			// ulActions.g:233:13: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SEMICOLON"

	// $ANTLR start "COMMA"
	public final void mCOMMA() throws RecognitionException {
		try {
			int _type = COMMA;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:234:7: ( ',' )
			// ulActions.g:234:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMA"

	// $ANTLR start "PAREN_LEFT"
	public final void mPAREN_LEFT() throws RecognitionException {
		try {
			int _type = PAREN_LEFT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:236:12: ( '(' )
			// ulActions.g:236:14: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PAREN_LEFT"

	// $ANTLR start "PAREN_RIGHT"
	public final void mPAREN_RIGHT() throws RecognitionException {
		try {
			int _type = PAREN_RIGHT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:237:13: ( ')' )
			// ulActions.g:237:15: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PAREN_RIGHT"

	// $ANTLR start "BRACKET_LEFT"
	public final void mBRACKET_LEFT() throws RecognitionException {
		try {
			int _type = BRACKET_LEFT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:238:14: ( '[' )
			// ulActions.g:238:16: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BRACKET_LEFT"

	// $ANTLR start "BRACKET_RIGHT"
	public final void mBRACKET_RIGHT() throws RecognitionException {
		try {
			int _type = BRACKET_RIGHT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:239:15: ( ']' )
			// ulActions.g:239:17: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BRACKET_RIGHT"

	// $ANTLR start "CURLY_LEFT"
	public final void mCURLY_LEFT() throws RecognitionException {
		try {
			int _type = CURLY_LEFT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:240:12: ( '{' )
			// ulActions.g:240:14: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CURLY_LEFT"

	// $ANTLR start "CURLY_RIGHT"
	public final void mCURLY_RIGHT() throws RecognitionException {
		try {
			int _type = CURLY_RIGHT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:241:13: ( '}' )
			// ulActions.g:241:15: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CURLY_RIGHT"

	// $ANTLR start "INTEGERCONSTANT"
	public final void mINTEGERCONSTANT() throws RecognitionException {
		try {
			int _type = INTEGERCONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:244:5: ( ZERO | NONZERO ( DIGIT )* )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0=='0') ) {
				alt2=1;
			}
			else if ( ((LA2_0 >= '1' && LA2_0 <= '9')) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// ulActions.g:244:7: ZERO
					{
					mZERO(); 

					}
					break;
				case 2 :
					// ulActions.g:245:7: NONZERO ( DIGIT )*
					{
					mNONZERO(); 

					// ulActions.g:245:15: ( DIGIT )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// ulActions.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop1;
						}
					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGERCONSTANT"

	// $ANTLR start "STRINGCONSTANT"
	public final void mSTRINGCONSTANT() throws RecognitionException {
		try {
			int _type = STRINGCONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:249:5: ( QUOTE_DOUBLE ( CHARACTER )+ QUOTE_DOUBLE )
			// ulActions.g:249:7: QUOTE_DOUBLE ( CHARACTER )+ QUOTE_DOUBLE
			{
			mQUOTE_DOUBLE(); 

			// ulActions.g:249:20: ( CHARACTER )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= ' ' && LA3_0 <= '!')||LA3_0==','||LA3_0=='.'||(LA3_0 >= '0' && LA3_0 <= ':')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||LA3_0=='_'||(LA3_0 >= 'a' && LA3_0 <= '{')||LA3_0=='}') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// ulActions.g:
					{
					if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||input.LA(1)==','||input.LA(1)=='.'||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= '{')||input.LA(1)=='}' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			mQUOTE_DOUBLE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRINGCONSTANT"

	// $ANTLR start "CHARACTERCONSTANT"
	public final void mCHARACTERCONSTANT() throws RecognitionException {
		try {
			int _type = CHARACTERCONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:253:5: ( QUOTE_SINGLE CHARACTER QUOTE_SINGLE )
			// ulActions.g:253:7: QUOTE_SINGLE CHARACTER QUOTE_SINGLE
			{
			mQUOTE_SINGLE(); 

			mCHARACTER(); 

			mQUOTE_SINGLE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHARACTERCONSTANT"

	// $ANTLR start "FLOATCONSTANT"
	public final void mFLOATCONSTANT() throws RecognitionException {
		try {
			int _type = FLOATCONSTANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:257:5: ( INTEGERCONSTANT PERIOD INTEGERCONSTANT )
			// ulActions.g:257:7: INTEGERCONSTANT PERIOD INTEGERCONSTANT
			{
			mINTEGERCONSTANT(); 

			mPERIOD(); 

			mINTEGERCONSTANT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOATCONSTANT"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:261:5: ( ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )* )
			// ulActions.g:261:7: ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// ulActions.g:261:29: ( LETTER | DIGIT | UNDERSCORE )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// ulActions.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop4;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:265:5: ( ( SPACE | TAB | NEWLINE )+ )
			// ulActions.g:265:7: ( SPACE | TAB | NEWLINE )+
			{
			// ulActions.g:265:7: ( SPACE | TAB | NEWLINE )+
			int cnt5=0;
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '\t' && LA5_0 <= '\n')||LA5_0=='\r'||LA5_0==' ') ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// ulActions.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt5 >= 1 ) break loop5;
					EarlyExitException eee = new EarlyExitException(5, input);
					throw eee;
				}
				cnt5++;
			}

			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:269:5: ( '//' (~ NEWLINE )* ( NEWLINE | EOF ) )
			// ulActions.g:269:7: '//' (~ NEWLINE )* ( NEWLINE | EOF )
			{
			match("//"); 

			// ulActions.g:269:12: (~ NEWLINE )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\t')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '\uFFFF')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// ulActions.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop6;
				}
			}

			// ulActions.g:269:22: ( NEWLINE | EOF )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0=='\n'||LA7_0=='\r') ) {
				alt7=1;
			}

			else {
				alt7=2;
			}

			switch (alt7) {
				case 1 :
					// ulActions.g:269:23: NEWLINE
					{
					mNEWLINE(); 

					}
					break;
				case 2 :
					// ulActions.g:269:33: EOF
					{
					match(EOF); 

					}
					break;

			}

			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "EQUALS"
	public final void mEQUALS() throws RecognitionException {
		try {
			int _type = EQUALS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:272:8: ( '==' )
			// ulActions.g:272:10: '=='
			{
			match("=="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQUALS"

	// $ANTLR start "LESS_THAN"
	public final void mLESS_THAN() throws RecognitionException {
		try {
			int _type = LESS_THAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:273:11: ( '<' )
			// ulActions.g:273:13: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LESS_THAN"

	// $ANTLR start "ADDITION"
	public final void mADDITION() throws RecognitionException {
		try {
			int _type = ADDITION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:274:10: ( '+' )
			// ulActions.g:274:12: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ADDITION"

	// $ANTLR start "SUBTRACTION"
	public final void mSUBTRACTION() throws RecognitionException {
		try {
			int _type = SUBTRACTION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:275:13: ( '-' )
			// ulActions.g:275:15: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUBTRACTION"

	// $ANTLR start "MULTIPLY"
	public final void mMULTIPLY() throws RecognitionException {
		try {
			int _type = MULTIPLY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulActions.g:276:10: ( '*' )
			// ulActions.g:276:12: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MULTIPLY"

	// $ANTLR start "SPACE"
	public final void mSPACE() throws RecognitionException {
		try {
			// ulActions.g:278:16: ( ' ' )
			// ulActions.g:278:18: ' '
			{
			match(' '); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SPACE"

	// $ANTLR start "TAB"
	public final void mTAB() throws RecognitionException {
		try {
			// ulActions.g:279:14: ( '\\t' )
			// ulActions.g:279:16: '\\t'
			{
			match('\t'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TAB"

	// $ANTLR start "NEWLINE"
	public final void mNEWLINE() throws RecognitionException {
		try {
			// ulActions.g:280:18: ( '\\n' | '\\r' )
			// ulActions.g:
			{
			if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NEWLINE"

	// $ANTLR start "ZERO"
	public final void mZERO() throws RecognitionException {
		try {
			// ulActions.g:282:15: ( '0' )
			// ulActions.g:282:17: '0'
			{
			match('0'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ZERO"

	// $ANTLR start "NONZERO"
	public final void mNONZERO() throws RecognitionException {
		try {
			// ulActions.g:283:18: ( '1' .. '9' )
			// ulActions.g:
			{
			if ( (input.LA(1) >= '1' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NONZERO"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// ulActions.g:284:16: ( ZERO | NONZERO )
			// ulActions.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "QUOTE_DOUBLE"
	public final void mQUOTE_DOUBLE() throws RecognitionException {
		try {
			// ulActions.g:286:23: ( '\"' )
			// ulActions.g:286:25: '\"'
			{
			match('\"'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "QUOTE_DOUBLE"

	// $ANTLR start "QUOTE_SINGLE"
	public final void mQUOTE_SINGLE() throws RecognitionException {
		try {
			// ulActions.g:287:23: ( '\\'' )
			// ulActions.g:287:25: '\\''
			{
			match('\''); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "QUOTE_SINGLE"

	// $ANTLR start "UNDERSCORE"
	public final void mUNDERSCORE() throws RecognitionException {
		try {
			// ulActions.g:288:21: ( '_' )
			// ulActions.g:288:23: '_'
			{
			match('_'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNDERSCORE"

	// $ANTLR start "COLON"
	public final void mCOLON() throws RecognitionException {
		try {
			// ulActions.g:289:16: ( ':' )
			// ulActions.g:289:18: ':'
			{
			match(':'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COLON"

	// $ANTLR start "PERIOD"
	public final void mPERIOD() throws RecognitionException {
		try {
			// ulActions.g:290:17: ( '.' )
			// ulActions.g:290:19: '.'
			{
			match('.'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PERIOD"

	// $ANTLR start "EXCLAMATION"
	public final void mEXCLAMATION() throws RecognitionException {
		try {
			// ulActions.g:291:22: ( '!' )
			// ulActions.g:291:24: '!'
			{
			match('!'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EXCLAMATION"

	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException {
		try {
			// ulActions.g:292:17: ( 'a' .. 'z' | 'A' .. 'Z' )
			// ulActions.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LETTER"

	// $ANTLR start "CHARACTER"
	public final void mCHARACTER() throws RecognitionException {
		try {
			// ulActions.g:294:5: ( LETTER | DIGIT | COMMA | PERIOD | UNDERSCORE | CURLY_LEFT | CURLY_RIGHT | SPACE | EXCLAMATION | COLON )
			// ulActions.g:
			{
			if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||input.LA(1)==','||input.LA(1)=='.'||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= '{')||input.LA(1)=='}' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHARACTER"

	@Override
	public void mTokens() throws RecognitionException {
		// ulActions.g:1:8: ( INT | FLOAT | CHAR | STRING | BOOLEAN | VOID | TRUE | FALSE | IF | ELSE | WHILE | PRINT | PRINTLN | RETURN | ASSIGN | SEMICOLON | COMMA | PAREN_LEFT | PAREN_RIGHT | BRACKET_LEFT | BRACKET_RIGHT | CURLY_LEFT | CURLY_RIGHT | INTEGERCONSTANT | STRINGCONSTANT | CHARACTERCONSTANT | FLOATCONSTANT | ID | WS | COMMENT | EQUALS | LESS_THAN | ADDITION | SUBTRACTION | MULTIPLY )
		int alt8=35;
		alt8 = dfa8.predict(input);
		switch (alt8) {
			case 1 :
				// ulActions.g:1:10: INT
				{
				mINT(); 

				}
				break;
			case 2 :
				// ulActions.g:1:14: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 3 :
				// ulActions.g:1:20: CHAR
				{
				mCHAR(); 

				}
				break;
			case 4 :
				// ulActions.g:1:25: STRING
				{
				mSTRING(); 

				}
				break;
			case 5 :
				// ulActions.g:1:32: BOOLEAN
				{
				mBOOLEAN(); 

				}
				break;
			case 6 :
				// ulActions.g:1:40: VOID
				{
				mVOID(); 

				}
				break;
			case 7 :
				// ulActions.g:1:45: TRUE
				{
				mTRUE(); 

				}
				break;
			case 8 :
				// ulActions.g:1:50: FALSE
				{
				mFALSE(); 

				}
				break;
			case 9 :
				// ulActions.g:1:56: IF
				{
				mIF(); 

				}
				break;
			case 10 :
				// ulActions.g:1:59: ELSE
				{
				mELSE(); 

				}
				break;
			case 11 :
				// ulActions.g:1:64: WHILE
				{
				mWHILE(); 

				}
				break;
			case 12 :
				// ulActions.g:1:70: PRINT
				{
				mPRINT(); 

				}
				break;
			case 13 :
				// ulActions.g:1:76: PRINTLN
				{
				mPRINTLN(); 

				}
				break;
			case 14 :
				// ulActions.g:1:84: RETURN
				{
				mRETURN(); 

				}
				break;
			case 15 :
				// ulActions.g:1:91: ASSIGN
				{
				mASSIGN(); 

				}
				break;
			case 16 :
				// ulActions.g:1:98: SEMICOLON
				{
				mSEMICOLON(); 

				}
				break;
			case 17 :
				// ulActions.g:1:108: COMMA
				{
				mCOMMA(); 

				}
				break;
			case 18 :
				// ulActions.g:1:114: PAREN_LEFT
				{
				mPAREN_LEFT(); 

				}
				break;
			case 19 :
				// ulActions.g:1:125: PAREN_RIGHT
				{
				mPAREN_RIGHT(); 

				}
				break;
			case 20 :
				// ulActions.g:1:137: BRACKET_LEFT
				{
				mBRACKET_LEFT(); 

				}
				break;
			case 21 :
				// ulActions.g:1:150: BRACKET_RIGHT
				{
				mBRACKET_RIGHT(); 

				}
				break;
			case 22 :
				// ulActions.g:1:164: CURLY_LEFT
				{
				mCURLY_LEFT(); 

				}
				break;
			case 23 :
				// ulActions.g:1:175: CURLY_RIGHT
				{
				mCURLY_RIGHT(); 

				}
				break;
			case 24 :
				// ulActions.g:1:187: INTEGERCONSTANT
				{
				mINTEGERCONSTANT(); 

				}
				break;
			case 25 :
				// ulActions.g:1:203: STRINGCONSTANT
				{
				mSTRINGCONSTANT(); 

				}
				break;
			case 26 :
				// ulActions.g:1:218: CHARACTERCONSTANT
				{
				mCHARACTERCONSTANT(); 

				}
				break;
			case 27 :
				// ulActions.g:1:236: FLOATCONSTANT
				{
				mFLOATCONSTANT(); 

				}
				break;
			case 28 :
				// ulActions.g:1:250: ID
				{
				mID(); 

				}
				break;
			case 29 :
				// ulActions.g:1:253: WS
				{
				mWS(); 

				}
				break;
			case 30 :
				// ulActions.g:1:256: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 31 :
				// ulActions.g:1:264: EQUALS
				{
				mEQUALS(); 

				}
				break;
			case 32 :
				// ulActions.g:1:271: LESS_THAN
				{
				mLESS_THAN(); 

				}
				break;
			case 33 :
				// ulActions.g:1:281: ADDITION
				{
				mADDITION(); 

				}
				break;
			case 34 :
				// ulActions.g:1:290: SUBTRACTION
				{
				mSUBTRACTION(); 

				}
				break;
			case 35 :
				// ulActions.g:1:302: MULTIPLY
				{
				mMULTIPLY(); 

				}
				break;

		}
	}


	protected DFA8 dfa8 = new DFA8(this);
	static final String DFA8_eotS =
		"\1\uffff\13\31\1\56\10\uffff\2\57\11\uffff\1\31\1\63\13\31\4\uffff\1\57"+
		"\1\77\1\uffff\13\31\1\uffff\2\31\1\115\2\31\1\120\1\121\1\122\3\31\1\126"+
		"\1\127\1\uffff\2\31\3\uffff\1\132\1\134\1\31\2\uffff\1\136\1\31\1\uffff"+
		"\1\31\1\uffff\1\141\1\uffff\1\142\1\143\3\uffff";
	static final String DFA8_eofS =
		"\144\uffff";
	static final String DFA8_minS =
		"\1\11\1\146\1\141\1\150\1\164\2\157\1\162\1\154\1\150\1\162\1\145\1\75"+
		"\10\uffff\2\56\11\uffff\1\164\1\60\1\157\1\154\1\141\1\162\1\157\1\151"+
		"\1\165\1\163\2\151\1\164\4\uffff\1\56\1\60\1\uffff\1\141\1\163\1\162\1"+
		"\151\1\154\1\144\2\145\1\154\1\156\1\165\1\uffff\1\164\1\145\1\60\1\156"+
		"\1\145\3\60\1\145\1\164\1\162\2\60\1\uffff\1\147\1\141\3\uffff\2\60\1"+
		"\156\2\uffff\1\60\1\156\1\uffff\1\156\1\uffff\1\60\1\uffff\2\60\3\uffff";
	static final String DFA8_maxS =
		"\1\175\1\156\1\154\1\150\1\164\2\157\1\162\1\154\1\150\1\162\1\145\1\75"+
		"\10\uffff\1\56\1\71\11\uffff\1\164\1\172\1\157\1\154\1\141\1\162\1\157"+
		"\1\151\1\165\1\163\2\151\1\164\4\uffff\1\71\1\172\1\uffff\1\141\1\163"+
		"\1\162\1\151\1\154\1\144\2\145\1\154\1\156\1\165\1\uffff\1\164\1\145\1"+
		"\172\1\156\1\145\3\172\1\145\1\164\1\162\2\172\1\uffff\1\147\1\141\3\uffff"+
		"\2\172\1\156\2\uffff\1\172\1\156\1\uffff\1\156\1\uffff\1\172\1\uffff\2"+
		"\172\3\uffff";
	static final String DFA8_acceptS =
		"\15\uffff\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\2\uffff\1\31\1\32\1"+
		"\34\1\35\1\36\1\40\1\41\1\42\1\43\15\uffff\1\37\1\17\1\30\1\33\2\uffff"+
		"\1\11\13\uffff\1\1\15\uffff\1\3\2\uffff\1\6\1\7\1\12\3\uffff\1\2\1\10"+
		"\2\uffff\1\13\1\uffff\1\14\1\uffff\1\4\2\uffff\1\16\1\5\1\15";
	static final String DFA8_specialS =
		"\144\uffff}>";
	static final String[] DFA8_transitionS = {
			"\2\32\2\uffff\1\32\22\uffff\1\32\1\uffff\1\27\4\uffff\1\30\1\17\1\20"+
			"\1\37\1\35\1\16\1\36\1\uffff\1\33\1\25\11\26\1\uffff\1\15\1\34\1\14\3"+
			"\uffff\32\31\1\21\1\uffff\1\22\1\uffff\1\31\1\uffff\1\31\1\5\1\3\1\31"+
			"\1\10\1\2\2\31\1\1\6\31\1\12\1\31\1\13\1\4\1\7\1\31\1\6\1\11\3\31\1\23"+
			"\1\uffff\1\24",
			"\1\41\7\uffff\1\40",
			"\1\43\12\uffff\1\42",
			"\1\44",
			"\1\45",
			"\1\46",
			"\1\47",
			"\1\50",
			"\1\51",
			"\1\52",
			"\1\53",
			"\1\54",
			"\1\55",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\60",
			"\1\60\1\uffff\12\61",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\62",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\1\64",
			"\1\65",
			"\1\66",
			"\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"\1\73",
			"\1\74",
			"\1\75",
			"\1\76",
			"",
			"",
			"",
			"",
			"\1\60\1\uffff\12\61",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"",
			"\1\100",
			"\1\101",
			"\1\102",
			"\1\103",
			"\1\104",
			"\1\105",
			"\1\106",
			"\1\107",
			"\1\110",
			"\1\111",
			"\1\112",
			"",
			"\1\113",
			"\1\114",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\1\116",
			"\1\117",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\1\123",
			"\1\124",
			"\1\125",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"",
			"\1\130",
			"\1\131",
			"",
			"",
			"",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\13\31\1\133\16\31",
			"\1\135",
			"",
			"",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\1\137",
			"",
			"\1\140",
			"",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"\12\31\7\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
			"",
			"",
			""
	};

	static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
	static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
	static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
	static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
	static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
	static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
	static final short[][] DFA8_transition;

	static {
		int numStates = DFA8_transitionS.length;
		DFA8_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
		}
	}

	protected class DFA8 extends DFA {

		public DFA8(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 8;
			this.eot = DFA8_eot;
			this.eof = DFA8_eof;
			this.min = DFA8_min;
			this.max = DFA8_max;
			this.accept = DFA8_accept;
			this.special = DFA8_special;
			this.transition = DFA8_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( INT | FLOAT | CHAR | STRING | BOOLEAN | VOID | TRUE | FALSE | IF | ELSE | WHILE | PRINT | PRINTLN | RETURN | ASSIGN | SEMICOLON | COMMA | PAREN_LEFT | PAREN_RIGHT | BRACKET_LEFT | BRACKET_RIGHT | CURLY_LEFT | CURLY_RIGHT | INTEGERCONSTANT | STRINGCONSTANT | CHARACTERCONSTANT | FLOATCONSTANT | ID | WS | COMMENT | EQUALS | LESS_THAN | ADDITION | SUBTRACTION | MULTIPLY );";
		}
	}

}
