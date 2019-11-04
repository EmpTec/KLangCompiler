package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class IfStatement extends Statement {

    public Expression cond;
    public Block then;
    public Block alt;

    public IfStatement(Expression cond, Block then, Block alt) {
        this.cond = cond;
        this.then = then;
        this.alt = alt;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}