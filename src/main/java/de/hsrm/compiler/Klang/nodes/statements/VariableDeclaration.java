package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class VariableDeclaration extends Statement {
    public String name;
    public Expression expression;
    public boolean initialized = false; // Whether or not this variable has been initialized

    public VariableDeclaration(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public VariableDeclaration(String name) {
        this(name, null);
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}