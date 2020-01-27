package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class Parameter extends Node {

  public String name;

  public Parameter(String name) {
    this.name = name;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}