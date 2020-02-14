package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class ConstructorCall extends Expression {

    public String structName;
    public Expression[] args;

    public ConstructorCall(String structName, Expression[] args) {
        this.structName = structName;
        this.args = args;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}