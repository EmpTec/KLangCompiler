package de.hsrm.compiler.Klang.nodes;

import java.util.Map;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class Program extends Node {
  
  public FunctionDefinition[] funcs;
  public Map<String, StructDefinition> structs;

  public Program(FunctionDefinition[] funcs, Map<String, StructDefinition> structs) {
    this.funcs = funcs;
    this.structs = structs;
  }

  @Override
  public <R> R welcome(Visitor<R> v) {
    return v.visit(this);
  }
}
