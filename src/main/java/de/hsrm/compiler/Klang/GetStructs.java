package de.hsrm.compiler.Klang;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import de.hsrm.compiler.Klang.helper.Helper;
import de.hsrm.compiler.Klang.nodes.Node;
import de.hsrm.compiler.Klang.nodes.StructDefinition;
import de.hsrm.compiler.Klang.nodes.StructField;
import de.hsrm.compiler.Klang.types.StructType;
import de.hsrm.compiler.Klang.types.Type;

public class GetStructs extends KlangBaseVisitor<Node> {

    private Set<String> structNames;
    private Map<String, StructDefinition> structs;

    public GetStructs(Map<String, StructDefinition> structs) {
        this.structs = structs;
        this.structNames = new HashSet<>();
    }

    @Override
    public Node visitProgram(KlangParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.structDef().size(); i++) {
            this.visit(ctx.structDef(i));
        }

        return null;
    }

    @Override
    public Node visitStructDef(KlangParser.StructDefContext ctx) {
        String name = ctx.structName.getText();
        int line = ctx.start.getLine();
        int col = ctx.start.getCharPositionInLine();
        StructField[] fields = new StructField[ctx.structField().size()];

        if (this.structNames.contains(name)) {
            String error = "Struct " + name + " defined multiple times.";
            throw new Error(Helper.getErrorPrefix(line, col) + error);
        }

        this.structNames.add(name);
    
        for (int i = 0; i < ctx.structField().size(); i++) {
          StructField field = (StructField) this.visit(ctx.structField(i));
          fields[i] = field;
        }
    
        StructDefinition result = new StructDefinition(name, fields);
        result.line = line;
        result.col = col;
        result.type = new StructType(name);
        this.structs.put(name, result);
        return null;
    }

    @Override
    public Node visitStructField(KlangParser.StructFieldContext ctx) {
      String name = ctx.IDENT().getText();
      int line = ctx.start.getLine();
      int col = ctx.start.getCharPositionInLine();
      Type type = Type.getByName(ctx.type_annotation().type().getText());
  
      if (!type.isPrimitiveType() && !this.structNames.contains(type.getName())) {
        String error = "Type " + type.getName() + " not defined.";
        throw new RuntimeException(Helper.getErrorPrefix(line, col) + error);
      }
  
      Node result = new StructField(name);
      result.type = type;
      result.line = line;
      result.col = col;
      return result;
    }
}