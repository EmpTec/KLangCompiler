package de.hsrm.compiler.Klang;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvalVisitor extends KlangBaseVisitor<Value> {

    // used to compare floating point numbers
    public static final double SMALL_VALUE = 0.00000000001;

    // store variables (there's only one global scope!)
    private Map<String, Value> memory = new HashMap<String, Value>();

    // assignment/id overrides
    @Override
    public Value visitAssignment(KlangParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        Value value = this.visit(ctx.expr());
        return memory.put(id, value);
    }

    @Override
    public Value visitIdAtom(KlangParser.IdAtomContext ctx) {
        String id = ctx.getText();
        Value value = memory.get(id);
        if(value == null) {
            throw new RuntimeException("no such variable: " + id);
        }
        return value;
    }

    // atom overrides
    @Override
    public Value visitStringAtom(KlangParser.StringAtomContext ctx) {
        String str = ctx.getText();
        // strip quotes
        str = str.substring(1, str.length() - 1).replace("\"\"", "\"");
        return new Value(str);
    }

    @Override
    public Value visitNumberAtom(KlangParser.NumberAtomContext ctx) {
        return new Value(Double.valueOf(ctx.getText()));
    }

    @Override
    public Value visitBooleanAtom(KlangParser.BooleanAtomContext ctx) {
        return new Value(Boolean.valueOf(ctx.getText()));
    }

    @Override
    public Value visitNilAtom(KlangParser.NilAtomContext ctx) {
        return new Value(null);
    }

    // expr overrides
    @Override
    public Value visitParExpr(KlangParser.ParExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public Value visitPowExpr(KlangParser.PowExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(Math.pow(left.asDouble(), right.asDouble()));
    }

    @Override
    public Value visitUnaryMinusExpr(KlangParser.UnaryMinusExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(-value.asDouble());
    }

    @Override
    public Value visitNotExpr(KlangParser.NotExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(!value.asBoolean());
    }

    @Override
    public Value visitMultiplicationExpr(@NotNull KlangParser.MultiplicationExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case KlangParser.MULT:
                return new Value(left.asDouble() * right.asDouble());
            case KlangParser.DIV:
                return new Value(left.asDouble() / right.asDouble());
            case KlangParser.MOD:
                return new Value(left.asDouble() % right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + KlangParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitAdditiveExpr(@NotNull KlangParser.AdditiveExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case KlangParser.PLUS:
                return left.isDouble() && right.isDouble() ?
                        new Value(left.asDouble() + right.asDouble()) :
                        new Value(left.asString() + right.asString());
            case KlangParser.MINUS:
                return new Value(left.asDouble() - right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + KlangParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitRelationalExpr(@NotNull KlangParser.RelationalExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case KlangParser.LT:
                return new Value(left.asDouble() < right.asDouble());
            case KlangParser.LTEQ:
                return new Value(left.asDouble() <= right.asDouble());
            case KlangParser.GT:
                return new Value(left.asDouble() > right.asDouble());
            case KlangParser.GTEQ:
                return new Value(left.asDouble() >= right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + KlangParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitEqualityExpr(@NotNull KlangParser.EqualityExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case KlangParser.EQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) < SMALL_VALUE) :
                        new Value(left.equals(right));
            case KlangParser.NEQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) >= SMALL_VALUE) :
                        new Value(!left.equals(right));
            default:
                throw new RuntimeException("unknown operator: " + KlangParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitAndExpr(KlangParser.AndExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() && right.asBoolean());
    }

    @Override
    public Value visitOrExpr(KlangParser.OrExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() || right.asBoolean());
    }

    // log override
    @Override
    public Value visitLog(KlangParser.LogContext ctx) {
        Value value = this.visit(ctx.expr());
        System.out.println(value);
        return value;
    }

    // if override
    @Override
    public Value visitIf_stat(KlangParser.If_statContext ctx) {

        List<KlangParser.Condition_blockContext> conditions =  ctx.condition_block();

        boolean evaluatedBlock = false;

        for(KlangParser.Condition_blockContext condition : conditions) {

            Value evaluated = this.visit(condition.expr());

            if(evaluated.asBoolean()) {
                evaluatedBlock = true;
                // evaluate this block whose expr==true
                this.visit(condition.stat_block());
                break;
            }
        }

        if(!evaluatedBlock && ctx.stat_block() != null) {
            // evaluate the else-stat_block (if present == not null)
            this.visit(ctx.stat_block());
        }

        return Value.VOID;
    }

    // while override
    @Override
    public Value visitWhile_stat(KlangParser.While_statContext ctx) {

        Value value = this.visit(ctx.expr());

        while(value.asBoolean()) {

            // evaluate the code block
            this.visit(ctx.stat_block());

            // evaluate the expression
            value = this.visit(ctx.expr());
        }

        return Value.VOID;
    }
}