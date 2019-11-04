package de.hsrm.compiler.Klang.nodes.expressions;

public abstract class UnaryExpression extends Expression {
    public Expression lhs;

    public UnaryExpression(Expression lhs) {
        this.lhs = lhs;
    }

}