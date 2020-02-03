package de.hsrm.compiler.Klang;

import de.hsrm.compiler.Klang.types.Type;

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
