package de.hsrm.compiler.Klang;

import java.util.Map;
import java.util.TreeMap;

import de.hsrm.compiler.Klang.types.*;
import de.hsrm.compiler.Klang.helper.*;

public class GetFunctions extends KlangBaseVisitor<Void> {

  private Map<String, FunctionInformation> funcs;

  public GetFunctions(Map<String, FunctionInformation> funcs) {
    this.funcs = funcs;
  }

  @Override
  public Void visitProgram(KlangParser.ProgramContext ctx) {
    for (int i = 0; i < ctx.functionDef().size(); i++) {
      this.visit(ctx.functionDef(i));
    }
    return null;
  }

  @Override
  public Void visitFunctionDef(KlangParser.FunctionDefContext ctx) {
    String name = ctx.funcName.getText();

    if (this.funcs.containsKey(name)) {
      throw new Error("Function " + name + " defined multiple times");
    }
    
    Type returnType = Type.getByName(ctx.returnType.type().getText());

    TreeMap<String, Type> parameters = new TreeMap<String, Type>();

    // Process the paremter list by visiting every paremter in it
    int paramCount = ctx.params.parameter().size();
    Type[] signature = new Type[paramCount];
    for (int i = 0; i < paramCount; i++) {
      Type paramType = Type.getByName(ctx.params.parameter(i).type_annotation().getText());
      String paramName = ctx.params.parameter(i).IDENT().getText();
      parameters.put(paramName, paramType);
      signature[i] = paramType;
    }

    FunctionInformation information = new FunctionInformation(name, returnType, parameters, signature);
    this.funcs.put(name, information);
    return null;
  }
}