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

  @Override
  public String getName() {
    return "bool";
  }

  @Override
  public Type combine(Type that) {
    // Combining two equal types always works
    if (that.equals(this)) {
      return this;
    }

    // Every remaining type will throw a RuntimeException
    throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
  }

}