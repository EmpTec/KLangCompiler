package de.hsrm.compiler.Klang.types;

public abstract class PrimitiveType extends Type {

  @Override
  public boolean isPrimitiveType() {
    return true;
  }

  public boolean isIntegerType() {
    return false;
  };

  public boolean isBooleanType() {
    return false;
  };
}