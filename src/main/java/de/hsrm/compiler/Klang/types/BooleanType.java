package de.hsrm.compiler.Klang.types;

public class BooleanType extends PrimitiveType {

  private static BooleanType instance = null;

  public static BooleanType getType() {
    if (instance != null) {
      return instance;
    }
    instance = new BooleanType();
    return instance;
  }

  @Override
  public boolean isBooleanType() {
    return true;
  }

}