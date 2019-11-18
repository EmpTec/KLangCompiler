package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class Program extends Node {
  
  public FunctionDefinition[] funcs;
  public Expression expression;

  public Program(FunctionDefinition[] funcs, Expression expression) {
    this.funcs = funcs;
    this.expression = expression;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}