package de.hsrm.compiler.Klang.types;

public class IntegerType extends NumericType {

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

  @Override
  public String getName() {
    return "int";
  }

  @Override
  public String getCName() {
    return "long";
  }

  @Override
  public Type combine(Type that) {
    // Combining two equal types always works
    if (that.equals(this)) {
      return this;
    }

    if (that.equals(Type.getFloatType())) {
      return Type.getFloatType();
    }

    // Every remaining type will throw a RuntimeException
    throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
  }

}