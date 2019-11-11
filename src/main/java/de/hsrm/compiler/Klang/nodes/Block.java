package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.nodes.statements.Statement;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class Block extends Node {

    public Statement[] statements;

    public Block(Statement[] statements) {
        this.statements = statements;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}