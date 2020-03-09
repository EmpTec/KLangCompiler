package de.hsrm.compiler.Klang.nodes.statements;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class ReturnStatement extends Statement {

    public Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public ReturnStatement() {
      this.expression = null;    
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}