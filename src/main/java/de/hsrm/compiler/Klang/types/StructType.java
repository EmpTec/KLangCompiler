package de.hsrm.compiler.Klang.types;

public class StructType extends Type {

    public String name;

    public StructType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCName() {
        return this.name + "*";
    }

    @Override
    public Type combine(Type that) {
        if (that.equals(this)) {
            return this;
        }

        // If you combine a null type with a struct type, you
        // always get the struct type back. 
        if (that == NullType.getType()) {
            return this;
        }

        throw new RuntimeException("Type missmatch: cannot combine " + this.getName() + " and " + that.getName());
    }

    @Override
    public boolean isPrimitiveType() {
        return false;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that instanceof StructType) {
            StructType thatType = (StructType) that;
            return this.getName().equals(thatType.getName());
        }

        return false;
    }

    @Override
    public boolean isNumericType() {
        return false;
    }
}