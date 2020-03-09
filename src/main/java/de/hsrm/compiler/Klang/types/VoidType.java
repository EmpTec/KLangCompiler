package de.hsrm.compiler.Klang.types;

public class VoidType extends Type {

  private static VoidType instance;

  public static VoidType getType() {
    if (instance != null) {
        return instance;
    }
    instance = new VoidType();
    return instance;
}

  @Override
  public String getName() {
    return "void";
  }

  @Override
  public Type combine(Type that) {
    if (that.equals(this)) {
      return this;
    }
    throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
  }

  @Override
  public boolean isPrimitiveType() {
    return false;
  }

  @Override
  public boolean isNumericType() {
    return false;
  }

}