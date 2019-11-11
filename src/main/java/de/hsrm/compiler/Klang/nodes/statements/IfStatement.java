package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class IfStatement extends Statement {

    public Expression cond;
    public Block then;
    public Block alt;
    public IfStatement elif;

    public IfStatement(Expression cond, Block then, Block alt, IfStatement elif) {
        this.cond = cond;
        this.then = then;
        this.alt = alt;
        this.elif = elif;
    }

    public IfStatement(Expression cond, Block then, Block alt) {
        this(cond, then, alt, null);
    }

    public IfStatement(Expression cond, Block then, IfStatement elif) {
        this(cond, then, null, elif);
    }

    public IfStatement(Expression cond, Block then) {
        this(cond, then, null, null);
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}