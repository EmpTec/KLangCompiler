package de.hsrm.compiler.Klang.types;

public abstract class Type {

  // Returns an instance of IntegerType
  // Used for adding new types to a node
  public static IntegerType getIntegerType() {
    return IntegerType.getType();
  }

  public static BooleanType getBooleanType() {
    return BooleanType.getType();
  }

  public abstract boolean isPrimitiveType();
}