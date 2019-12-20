package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class LTEExpression extends BinaryExpression {
    public LTEExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}