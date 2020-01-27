package de.hsrm.compiler.Klang.helper;

import java.util.Map;

import de.hsrm.compiler.Klang.types.Type;

public class FunctionInformation {
  public String name;
  public Type returnType;
  public Map<String, Type> parameters;
  public Type[] signature;

  public FunctionInformation(String name, Type returnType, Map<String,Type> parameters, Type[] signature) {
    this.name = name;
    this.returnType = returnType;
    this.parameters = parameters;
    this.signature = signature;
  }  
}