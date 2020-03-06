package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class FieldAssignment extends Statement {

    public String varName;
    public String structName;
    public String[] path;
    public Expression expression;

    public FieldAssignment(String varName, String structName, String[] path, Expression expression) {
        this.varName = varName;
        this.structName = structName;
        this.path = path;
        this.expression = expression;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}