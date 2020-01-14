package de.hsrm.compiler.Klang.nodes.loops;

import de.hsrm.compiler.Klang.nodes.statements.Statement;
import de.hsrm.compiler.Klang.nodes.statements.VariableAssignment;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class ForLoop extends Statement {

    public Statement init;
    public Expression condition;
    public VariableAssignment step;
    public Block block;

    public ForLoop(Statement init, Expression condition, VariableAssignment step, Block block) {
      this.init = init;
      this.condition = condition;
      this.step = step;
      this.block = block;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }

}