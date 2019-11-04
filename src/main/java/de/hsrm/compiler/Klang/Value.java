package de.hsrm.compiler.Klang;

public class Value {
    private Object value;

    public Value(Object value) {
        this.value = value;
    }

    public int asInteger() {
        return (int) this.value;
    }
}
