package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class DestructorCall extends Statement {

    public String name;

    public DestructorCall(String name) {
        this.name = name;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}