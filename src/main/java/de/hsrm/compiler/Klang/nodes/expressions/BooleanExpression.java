package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class BooleanExpression extends Expression {
    public boolean value;

    public BooleanExpression(boolean value) {
        this.value = value;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}