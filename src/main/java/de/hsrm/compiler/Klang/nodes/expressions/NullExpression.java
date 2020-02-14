package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class NullExpression extends Expression {

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}