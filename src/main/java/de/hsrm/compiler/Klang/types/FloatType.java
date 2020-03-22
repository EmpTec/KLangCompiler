package de.hsrm.compiler.Klang.types;

public class FloatType extends NumericType {

  private static FloatType instance = null;

  public static FloatType getType() {
    if (instance != null) {
      return instance;
    }
    instance = new FloatType();
    return instance;
  }

  @Override
  public boolean isFloatType() {
    return true;
  }

  @Override
  public String getName() {
    return "float";
  }

  @Override
  public String getCName() {
    return "double";
  }

  @Override
  public Type combine(Type that) {
    // Combining two equal types always works
    if (that.equals(this)) {
      return this;
    }

    if (that.equals(Type.getIntegerType())) {
      return Type.getFloatType();
    }

    // Every remaining type will throw a RuntimeException
    throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
  }

}