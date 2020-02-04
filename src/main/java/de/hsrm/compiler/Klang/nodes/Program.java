package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class Program extends Node {
  
  public FunctionDefinition[] funcs;
  public StructDefinition[] structs;
  public Expression expression;

  public Program(FunctionDefinition[] funcs, StructDefinition[] structs, Expression expression) {
    this.funcs = funcs;
    this.structs = structs;
    this.expression = expression;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}
