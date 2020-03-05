package de.hsrm.compiler.Klang;

import de.hsrm.compiler.Klang.types.Type;
import java.util.Map;

public class Value {
    public Type type;
    private Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Value(Object value, Type type) {
      this.value = value;
      this.type = type;
    }

    public Object asObject() {
        return this.value;
    }

    public int asInteger() {
        return (int) this.value;
    }
    
    public double asFloat() {
        return (double) this.value;
    }

    public boolean asBoolean() {
        return (boolean) this.value;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Value> asStruct() {
        return (Map<String, Value>) this.value;
    }
}
