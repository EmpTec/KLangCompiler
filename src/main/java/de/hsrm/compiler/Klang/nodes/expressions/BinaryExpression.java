package de.hsrm.compiler.Klang.nodes.expressions;

public abstract class BinaryExpression extends Expression {

    public Expression lhs;
    public Expression rhs;

    public BinaryExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}