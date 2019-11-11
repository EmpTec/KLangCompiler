package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.Value;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

public class EvalVisitor implements Visitor<Value> {

    @Override
    public Value visit(IntegerExpression e) {
        return new Value(e.value);
    }

    @Override
    public Value visit(MultiplicativeExpression e) {
        Value a = e.lhs.welcome(this);
        Value b = e.rhs.welcome(this);
        return new Value(a.asInteger() * b.asInteger());
    }

    @Override
    public Value visit(AdditiveExpression e) {
        Value a = e.lhs.welcome(this);
        Value b = e.rhs.welcome(this);
        return new Value(a.asInteger() + b.asInteger());
    }

    @Override
    public Value visit(ModuloExpression e) {
        Value a = e.lhs.welcome(this);
        Value b = e.rhs.welcome(this);
        return new Value(a.asInteger() % b.asInteger());
    }

    @Override
    public Value visit(NegateExpression e) {
        Value a = e.lhs.welcome(this);
        return new Value(-a.asInteger());
    }

    @Override
    public Value visit(IfStatement e) {
        // In the future we have to make sure that the
        // value is actually a type that we can use as boolean
        Value condition = e.cond.welcome(this);

        if (condition.asInteger() != 0) {
            e.then.welcome(this);
        } else if (e.alt != null) {
            e.alt.welcome(this);
        }

        return null;
    }

    @Override
    public Value visit(PrintStatement e) {
        Value value = e.expression.welcome(this);

        // In the future we have to determine of which type the value is
        // before calling an "asX()" method
        System.out.println(value.asInteger());
        return null;
    }

    @Override
    public Value visit(Block e) {
        // TODO Auto-generated method stub
        return null;
    }

}