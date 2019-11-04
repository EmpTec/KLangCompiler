// Generated from /home/marvin/Documents/university/compiler/klang/src/main/antlr4/de/hsrm/compiler/Klang/Klang.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KlangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT=1, IF=2, ELSE=3, SCOL=4, OBRK=5, CBRK=6, MULT=7, ADD=8, SUB=9, MOD=10, 
		INTEGER_LITERAL=11, WS=12;
	public static final int
		RULE_parse = 0, RULE_block = 1, RULE_braced_block = 2, RULE_statement = 3, 
		RULE_print = 4, RULE_if_statement = 5, RULE_expression = 6, RULE_atom = 7;
	public static final String[] ruleNames = {
		"parse", "block", "braced_block", "statement", "print", "if_statement", 
		"expression", "atom"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'print'", "'if'", "'else'", "';'", "'{'", "'}'", "'*'", "'+'", 
		"'-'", "'%'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PRINT", "IF", "ELSE", "SCOL", "OBRK", "CBRK", "MULT", "ADD", "SUB", 
		"MOD", "INTEGER_LITERAL", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Klang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KlangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ParseContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PRINT || _la==IF) {
				{
				{
				setState(18);
				statement();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Braced_blockContext extends ParserRuleContext {
		public TerminalNode OBRK() { return getToken(KlangParser.OBRK, 0); }
		public TerminalNode CBRK() { return getToken(KlangParser.CBRK, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Braced_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_braced_block; }
	}

	public final Braced_blockContext braced_block() throws RecognitionException {
		Braced_blockContext _localctx = new Braced_blockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_braced_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(OBRK);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PRINT || _la==IF) {
				{
				{
				setState(25);
				statement();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(CBRK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			setState(35);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				print();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				if_statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(KlangParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SCOL() { return getToken(KlangParser.SCOL, 0); }
		public PrintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print; }
	}

	public final PrintContext print() throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_print);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(PRINT);
			setState(38);
			expression();
			setState(39);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_statementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(KlangParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<Braced_blockContext> braced_block() {
			return getRuleContexts(Braced_blockContext.class);
		}
		public Braced_blockContext braced_block(int i) {
			return getRuleContext(Braced_blockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(KlangParser.ELSE, 0); }
		public If_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
	}

	public final If_statementContext if_statement() throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_if_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(IF);
			setState(42);
			expression();
			setState(43);
			braced_block();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(44);
				match(ELSE);
				setState(45);
				braced_block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AdditiveExpressionContext extends ExpressionContext {
		public Token op;
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public TerminalNode ADD() { return getToken(KlangParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(KlangParser.SUB, 0); }
		public AdditiveExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ModuloExpressionContext extends ExpressionContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public TerminalNode MOD() { return getToken(KlangParser.MOD, 0); }
		public ModuloExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class UnaryNegateExpressionContext extends ExpressionContext {
		public TerminalNode SUB() { return getToken(KlangParser.SUB, 0); }
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public UnaryNegateExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AtomExpressionContext extends ExpressionContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public AtomExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MultiplicationExpressionContext extends ExpressionContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public TerminalNode MULT() { return getToken(KlangParser.MULT, 0); }
		public MultiplicationExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expression);
		int _la;
		try {
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new MultiplicationExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				atom();
				setState(49);
				match(MULT);
				setState(50);
				atom();
				}
				break;
			case 2:
				_localctx = new AdditiveExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				atom();
				setState(53);
				((AdditiveExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((AdditiveExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(54);
				atom();
				}
				break;
			case 3:
				_localctx = new ModuloExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				atom();
				setState(57);
				match(MOD);
				setState(58);
				atom();
				}
				break;
			case 4:
				_localctx = new UnaryNegateExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(60);
				match(SUB);
				setState(61);
				atom();
				}
				break;
			case 5:
				_localctx = new AtomExpressionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(62);
				atom();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntAtomContext extends AtomContext {
		public TerminalNode INTEGER_LITERAL() { return getToken(KlangParser.INTEGER_LITERAL, 0); }
		public IntAtomContext(AtomContext ctx) { copyFrom(ctx); }
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_atom);
		try {
			_localctx = new IntAtomContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(INTEGER_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\16F\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\3\7\3\26"+
		"\n\3\f\3\16\3\31\13\3\3\4\3\4\7\4\35\n\4\f\4\16\4 \13\4\3\4\3\4\3\5\3"+
		"\5\5\5&\n\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\5\7\61\n\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bB\n\b\3\t\3\t\3\t"+
		"\2\2\n\2\4\6\b\n\f\16\20\2\3\3\2\n\13\2E\2\22\3\2\2\2\4\27\3\2\2\2\6\32"+
		"\3\2\2\2\b%\3\2\2\2\n\'\3\2\2\2\f+\3\2\2\2\16A\3\2\2\2\20C\3\2\2\2\22"+
		"\23\5\4\3\2\23\3\3\2\2\2\24\26\5\b\5\2\25\24\3\2\2\2\26\31\3\2\2\2\27"+
		"\25\3\2\2\2\27\30\3\2\2\2\30\5\3\2\2\2\31\27\3\2\2\2\32\36\7\7\2\2\33"+
		"\35\5\b\5\2\34\33\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37!"+
		"\3\2\2\2 \36\3\2\2\2!\"\7\b\2\2\"\7\3\2\2\2#&\5\n\6\2$&\5\f\7\2%#\3\2"+
		"\2\2%$\3\2\2\2&\t\3\2\2\2\'(\7\3\2\2()\5\16\b\2)*\7\6\2\2*\13\3\2\2\2"+
		"+,\7\4\2\2,-\5\16\b\2-\60\5\6\4\2./\7\5\2\2/\61\5\6\4\2\60.\3\2\2\2\60"+
		"\61\3\2\2\2\61\r\3\2\2\2\62\63\5\20\t\2\63\64\7\t\2\2\64\65\5\20\t\2\65"+
		"B\3\2\2\2\66\67\5\20\t\2\678\t\2\2\289\5\20\t\29B\3\2\2\2:;\5\20\t\2;"+
		"<\7\f\2\2<=\5\20\t\2=B\3\2\2\2>?\7\13\2\2?B\5\20\t\2@B\5\20\t\2A\62\3"+
		"\2\2\2A\66\3\2\2\2A:\3\2\2\2A>\3\2\2\2A@\3\2\2\2B\17\3\2\2\2CD\7\r\2\2"+
		"D\21\3\2\2\2\7\27\36%\60A";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}