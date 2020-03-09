package de.hsrm.compiler.Klang.nodes;

import de.hsrm.compiler.Klang.visitors.Visitor;

public class StructDefinition extends Node {

    public String name;
    public StructField[] fields;

    public StructDefinition(String name, StructField[] fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public <R> R welcome(Visitor<R> v) {
        return v.visit(this);
    }
}