// Generated from de/hsrm/compiler/Klang/Klang.g4 by ANTLR 4.5
package de.hsrm.compiler.Klang;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link KlangParser}.
 */
public interface KlangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link KlangParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(KlangParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(KlangParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(KlangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(KlangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(KlangParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(KlangParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(KlangParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(KlangParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(KlangParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(KlangParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void enterCondition_block(KlangParser.Condition_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void exitCondition_block(KlangParser.Condition_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void enterStat_block(KlangParser.Stat_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void exitStat_block(KlangParser.Stat_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(KlangParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(KlangParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link KlangParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(KlangParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link KlangParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(KlangParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(KlangParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(KlangParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(KlangParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(KlangParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpr(KlangParser.MultiplicationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpr(KlangParser.MultiplicationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(KlangParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(KlangParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(KlangParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(KlangParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(KlangParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(KlangParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowExpr(KlangParser.PowExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowExpr(KlangParser.PowExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(KlangParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(KlangParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(KlangParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(KlangParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(KlangParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link KlangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(KlangParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(KlangParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(KlangParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberAtom(KlangParser.NumberAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberAtom(KlangParser.NumberAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(KlangParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(KlangParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(KlangParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(KlangParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringAtom(KlangParser.StringAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringAtom(KlangParser.StringAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nilAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNilAtom(KlangParser.NilAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nilAtom}
	 * labeled alternative in {@link KlangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNilAtom(KlangParser.NilAtomContext ctx);
}