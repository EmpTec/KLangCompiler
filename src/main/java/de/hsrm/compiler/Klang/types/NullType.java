package de.hsrm.compiler.Klang.types;

public class NullType extends Type {

    private static NullType instance = null;

    public static NullType getType() {
        if (instance != null) {
            return instance;
        }
        instance = new NullType();
        return instance;
    }

    @Override
    public String getName() {
        return "naught";
    }

    @Override
    public Type combine(Type that) {
        // You can not combine null with a primitive type
        if (that.isPrimitiveType()) {
            throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
        }

        // Everything else combines with null to the type it was before
        return that;
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