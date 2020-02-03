package de.hsrm.compiler.Klang;

public class Value {
    private Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Object asObject() {
        return this.value;
    }

    public int asInteger() {
        return (int) this.value;
    }

    public boolean asBoolean() {
        return (boolean) this.value;
    }
}
