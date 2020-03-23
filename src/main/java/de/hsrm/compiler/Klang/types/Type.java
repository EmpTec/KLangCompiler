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

  public static FloatType getFloatType() {
    return FloatType.getType();
  }

  public static NullType getNullType() {
    return NullType.getType();
  }

  public static Type getByName(String name) {
    switch (name) {
      case "bool": return getBooleanType();
      case "int": return getIntegerType();
      case "float": return getFloatType();
      case "null": return getNullType();
      default: return new StructType(name);
    }
  }

  public abstract String getName();
  public abstract String getCName();
  public abstract Type combine(Type that);
  public abstract boolean isPrimitiveType();
  public abstract boolean isNumericType();
}