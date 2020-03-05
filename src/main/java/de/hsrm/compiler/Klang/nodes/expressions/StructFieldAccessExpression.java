package de.hsrm.compiler.Klang.nodes.expressions;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class StructFieldAccessExpression extends Expression {
  public String varName;
  public String structName;
  public String[] path;

  public StructFieldAccessExpression(String varName, String structName, String[] path) {
    this.varName = varName;
    this.structName = structName;
    this.path = path;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}