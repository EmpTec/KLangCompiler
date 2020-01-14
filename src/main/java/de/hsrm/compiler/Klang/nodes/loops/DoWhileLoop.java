package de.hsrm.compiler.Klang.nodes.loops;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.statements.Statement;
import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class DoWhileLoop extends Statement {

    public Expression cond;
    public Block block;
    public Block alt;

    public DoWhileLoop(Expression cond, Block block) {
        this.cond = cond;
        this.block = block;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}