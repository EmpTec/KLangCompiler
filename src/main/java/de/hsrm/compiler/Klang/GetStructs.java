package de.hsrm.compiler.Klang;

import java.util.Set;

import de.hsrm.compiler.Klang.helper.Helper;

public class GetStructs extends KlangBaseVisitor<Void> {

    private Set<String> structs;

    public GetStructs(Set<String> structs) {
        this.structs = structs;
    }

    @Override
    public Void visitProgram(KlangParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.structDef().size(); i++) {
            this.visit(ctx.structDef(i));
        }

        return null;
    }

    @Override
    public Void visitStructDef(KlangParser.StructDefContext ctx) {
        String name = ctx.structName.getText();
        int line = ctx.start.getLine();
        int col = ctx.start.getCharPositionInLine();

        if (this.structs.contains(name)) {
            String error = "Struct " + name + " defined multiple times.";
            throw new Error(Helper.getErrorPrefix(line, col) + error);
        }

        this.structs.add(name);
        return null;
    }
}