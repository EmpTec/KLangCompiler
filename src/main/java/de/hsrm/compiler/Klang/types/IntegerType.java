package de.hsrm.compiler.Klang.types;

public class IntegerType extends PrimitiveType {

  private static IntegerType instance = null;

  public static IntegerType getType() {
    if (instance != null) {
      return instance;
    }
    instance = new IntegerType();
    return instance;
  }

  @Override
  public boolean isIntegerType() {
    return true;
  }

}