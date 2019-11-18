package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class VariableAssignment extends Statement {

    public String name;
    public Expression expression;

    public VariableAssignment(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}