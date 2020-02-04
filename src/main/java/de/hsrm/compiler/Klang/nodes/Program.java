package de.hsrm.compiler.Klang.nodes;

import java.util.Map;

import de.hsrm.compiler.Klang.nodes.expressions.Expression;
import de.hsrm.compiler.Klang.visitors.Visitor;

public class Program extends Node {
  
  public FunctionDefinition[] funcs;
  public Map<String, StructDefinition> structs;
  public Expression expression;

  public Program(FunctionDefinition[] funcs, Map<String, StructDefinition> structs, Expression expression) {
    this.funcs = funcs;
    this.structs = structs;
    this.expression = expression;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}
