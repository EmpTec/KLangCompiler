package de.hsrm.compiler.Klang.visitors;

import java.util.HashMap;
import java.util.Map;

import de.hsrm.compiler.Klang.Value;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Parameter;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.StructDefinition;
import de.hsrm.compiler.Klang.nodes.StructField;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.ForLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class EvalVisitor implements Visitor<Value> {

  Map<String, FunctionDefinition> funcs = new HashMap<>();
  Map<String, StructDefinition> structs;
  Map<String, Value> env = new HashMap<>();
  Map<String, Map<String, Value>> heap = new HashMap<>();

  public EvalVisitor(Map<String, StructDefinition> structs) {
    this.structs = structs;
  }

  @Override
  public Value visit(IntegerExpression e) {
    Value result = new Value(e.value);
    result.type = Type.getIntegerType();
    return result;
  }

  @Override 
  public Value visit(FloatExpression e) {
    Value result = new Value(e.value);
    result.type = Type.getFloatType();
    return result;
  }

  @Override
  public Value visit(BooleanExpression e) {
    Value result = new Value(e.value);
    result.type = Type.getBooleanType();
    return result;
  }

  @Override
  public Value visit(EqualityExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = Type.getBooleanType();
    Type combineType = lhs.type.combine(rhs.type);

    switch(combineType.getName()) {
      case "bool": {
        return new Value(lhs.asBoolean() == rhs.asBoolean(), resultType);
      }
      case "int": {
        return new Value(lhs.asInteger() == rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() == rhs.asFloat(), resultType);
      }
      default: {
        return new Value(lhs.asObject() == rhs.asObject(), resultType);
      }
    }
  }

  @Override
  public Value visit(NotEqualityExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = Type.getBooleanType();
    Type combineType = lhs.type.combine(rhs.type);

    switch(combineType.getName()) {
      case "bool": {
        return new Value(lhs.asBoolean() != rhs.asBoolean(), resultType);
      }
      case "int": {
        return new Value(lhs.asInteger() != rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() != rhs.asFloat(), resultType);
      }
      default: {
        return new Value(lhs.asObject() != rhs.asObject(), resultType);
      }
    }
  }

  @Override
  public Value visit(GTExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = Type.getBooleanType();
    Type combineType = lhs.type.combine(rhs.type);

    switch(combineType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() > rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() > rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(GTEExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = Type.getBooleanType();
    Type combineType = lhs.type.combine(rhs.type);

    switch(combineType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() >= rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() >= rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(LTExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = Type.getBooleanType();
    Type combineType = lhs.type.combine(rhs.type);

    switch(combineType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() < rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() < rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(LTEExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type combineType = lhs.type.combine(rhs.type);
    Type resultType = Type.getBooleanType();

    switch(combineType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() <= rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() <= rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(AdditionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);

    switch(resultType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() + rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() + rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(SubstractionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);

    switch(resultType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() - rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() - rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(MultiplicationExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);

    switch(resultType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() * rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() * rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(DivisionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);

    switch(resultType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() / rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() / rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(ModuloExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);
    
    switch(resultType.getName()) {
      case "int": {
        return new Value(lhs.asInteger() % rhs.asInteger(), resultType);
      }
      case "float": {
        return new Value(lhs.asFloat() % rhs.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(NegateExpression e) {
    Value a = e.lhs.welcome(this);
    Type resultType = a.type;
    
    switch(resultType.getName()) {
      case "int": {
        return new Value(-a.asInteger(), resultType);
      }
      case "float": {
        return new Value(-a.asFloat(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(OrExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);
    
    switch(resultType.getName()) {
      case "bool": {
        return new Value(lhs.asBoolean() || rhs.asBoolean(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(AndExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    Type resultType = lhs.type.combine(rhs.type);
    
    switch(resultType.getName()) {
      case "bool": {
        return new Value(lhs.asBoolean() && rhs.asBoolean(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(NotExpression e) {
    Value lhs = e.lhs.welcome(this);
    Type resultType = lhs.type;
    
    switch(resultType.getName()) {
      case "bool": {
        return new Value(!lhs.asBoolean(), resultType);
      }
      default: {
        throw new RuntimeException("Unknown Type encountered");
      }
    }
  }

  @Override
  public Value visit(Variable e) {
    return this.env.get(e.name);
  }

  @Override
  public Value visit(IfStatement e) {
    // In the future we have to make sure that the
    // value is actually a type that we can use as boolean
    Value condition = e.cond.welcome(this);

    Value result = null;

    if (condition.asBoolean()) {
      result = e.then.welcome(this);
    } else if (e.alt != null) {
      result = e.alt.welcome(this);
    } else if (e.elif != null) {
      result = e.elif.welcome(this);
    }

    return result;
  }

  @Override
  public Value visit(WhileLoop e) {
    Value result = null;
    while (e.cond.welcome(this).asBoolean()) {
      result = e.block.welcome(this);
    }

    return result;
  }

  @Override
  public Value visit(DoWhileLoop e) {
    Value result = null;
    do {
      result = e.block.welcome(this);
    } while (e.cond.welcome(this).asBoolean());

    return result;
  }

  @Override
  public Value visit(ForLoop e) {
    e.init.welcome(this);
    Value cond = e.condition.welcome(this);
    Value result = null;
    while (cond.asBoolean()) {
      result = e.block.welcome(this);
      e.step.welcome(this);
      cond = e.condition.welcome(this);
    }

    return result;
  }

  @Override
  public Value visit(VariableDeclaration e) {
    Value initialValue = null;
    if (e.expression != null) {
      initialValue = e.expression.welcome(this);
    }

    this.env.put(e.name, initialValue);
    return null;
  }

  @Override
  public Value visit(VariableAssignment e) {
    Value result = e.expression.welcome(this);
    this.env.put(e.name, result);
    return null;
  }

  @Override
  public Value visit(ReturnStatement e) {
    return e.expression.welcome(this);
  }

  @Override
  public Value visit(Block e) {
    for (var stmt : e.statements) {
      Value result = stmt.welcome(this);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  @Override
  public Value visit(FunctionDefinition e) {
    // Ein Eval über eine FunDef macht keinen Sinn
    throw new RuntimeException("Wir sind im Eval und visiten eine Funktionsdefinition.. WUT?!");
  }

  @Override
  public Value visit(FunctionCall e) {
    // Die funktionsdefinition speichern
    FunctionDefinition func = this.funcs.get(e.name);

    // Baue ein neues environment
    Map<String, Value> newEnv = new HashMap<>();
    for (int i = 0; i < func.parameters.length; i++) {
      newEnv.put(func.parameters[i].name, e.arguments[i].welcome(this));
    }
    var oldEnv = this.env;
    this.env = newEnv;

    // Execute
    Value result = func.block.welcome(this);

    // Das alte env wiederherstellen
    this.env = oldEnv;

    return result;
  }

  @Override
  public Value visit(Program e) {
    FunctionDefinition main = null;
    for (var funcDef : e.funcs) {
      this.funcs.put(funcDef.name, funcDef);
      
      // save the main function
      if (funcDef.name.equals("main")) {
        main = funcDef;
        // make sure the function requires no parameters
        if (funcDef.parameters.length > 0) {
          throw new RuntimeException("can not evaluate sourcecode since main function has parameters defined");
        }
      }
    }

    if (main == null) {
      throw new RuntimeException("can not evaluate sourcecode since no main function was defined");
    }

    return new FunctionCall(main.name, new Expression[0]).welcome(this);
  }

  @Override
  public Value visit(Parameter e) {
    return null;
  }

  @Override
  public Value visit(StructDefinition e) {
    // We get these from a previous visitor
    return null;
  }

  @Override
  public Value visit(StructField e) {
    // Nothing to do here...
    return null;
  }

  @Override
  public Value visit(StructFieldAccessExpression e) {
    Value var = this.env.get(e.varName);
    Map<String, Value> struct = var.asStruct();

    Value currentValue = struct.get(e.path[0]);
    for (int i = 1; i < e.path.length; i++) {
      currentValue = currentValue.asStruct().get(e.path[i]);
    }

    return currentValue;
  }

  @Override
  public Value visit(ConstructorCall e) {
    StructDefinition structDef = this.structs.get(e.structName);
    Map<String, Value> struct = new HashMap<>();

    for (int i = 0; i < e.args.length; i++) {
      var arg = e.args[i].welcome(this);
      struct.put(structDef.fields[i].name, arg);
    }

    var result = new Value(struct);
    result.type = structDef.type;
    return result;
  }

  @Override
  public Value visit(NullExpression e) {
    return null;
  }

  @Override
  public Value visit(DestructorCall e) {
    this.env.remove(e.name);
    return null;
  }

  @Override
  public Value visit(FieldAssignment e) {
    Value val = this.env.get(e.varName);
    String fieldNameToUpdate = e.path[e.path.length - 1];
    
    // Find the struct that holds the field to be updated
    Map<String, Value> struct = val.asStruct();
    for (int i = 0; i < e.path.length - 1; i++) {
      struct = struct.get(e.path[i]).asStruct();
    }

    // if we are here, struct contains a reference to the struct that holds the field to be updated
    struct.put(fieldNameToUpdate, e.expression.welcome(this));

    return null;
  }

}