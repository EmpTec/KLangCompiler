package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class FunctionCall extends Expression {

  public String name;
  public Expression[] arguments;
  public boolean isTailRecursive = false;

  public FunctionCall(String name, Expression[] arguments) {
    this.name = name;
    this.arguments = arguments;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}