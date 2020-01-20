package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class FunctionDefinition extends Node {

  public String name;
  public Parameter[] parameters;
  public Block block;

  public FunctionDefinition(String name, Parameter[] parameters, Block block) {
    this.name = name;
    this.parameters = parameters;
    this.block = block;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}