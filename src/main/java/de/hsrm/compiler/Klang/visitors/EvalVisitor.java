package de.hsrm.compiler.Klang.visitors;

import java.util.HashMap;
import java.util.Map;

import de.hsrm.compiler.Klang.Value;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

public class EvalVisitor implements Visitor<Value> {

  Map<String, FunctionDefinition> funcs = new HashMap<>();
  Map<String, Value> env = new HashMap<>();

  @Override
  public Value visit(IntegerExpression e) {
    return new Value(e.value);
  }

  @Override
  public Value visit(EqualityExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() == rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(NotEqualityExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() != rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(GTExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() > rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(GTEExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() >= rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(LTExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() < rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(LTEExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    if (lhs.asInteger() <= rhs.asInteger()) {
      return new Value(1);
    }
    return new Value(0);
  }

  @Override
  public Value visit(AdditionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    return new Value(lhs.asInteger() + rhs.asInteger());
  }

  @Override
  public Value visit(SubstractionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    return new Value(lhs.asInteger() - rhs.asInteger());
  }

  @Override
  public Value visit(MultiplicationExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    return new Value(lhs.asInteger() * rhs.asInteger());
  }

  @Override
  public Value visit(DivisionExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    return new Value(lhs.asInteger() / rhs.asInteger());
  }

  @Override
  public Value visit(ModuloExpression e) {
    Value lhs = e.lhs.welcome(this);
    Value rhs = e.rhs.welcome(this);
    return new Value(lhs.asInteger() % rhs.asInteger());
  }

  @Override
  public Value visit(NegateExpression e) {
    Value a = e.lhs.welcome(this);
    return new Value(-a.asInteger());
  }

  @Override
  public Value visit(Variable e) {
    Value result = this.env.get(e.name);

    if (result == null) {
      throw new RuntimeException("Variable with name " + e.name + " not found.");
    }

    return result;
  }

  @Override
  public Value visit(IfStatement e) {
    // In the future we have to make sure that the
    // value is actually a type that we can use as boolean
    Value condition = e.cond.welcome(this);

    Value result = null;

    if (condition.asInteger() != 0) {
      result = e.then.welcome(this);
    } else if (e.alt != null) {
      result = e.alt.welcome(this);
    } else if (e.elif != null) {
      result = e.elif.welcome(this);
    }

    return result;
  }

  @Override
  public Value visit(PrintStatement e) {
    Value value = e.expression.welcome(this);

    // In the future we have to determine of which type the value is
    // before calling an "asX()" method
    System.out.println(value.asInteger());
    return null;
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

    // Stelle sicher, dass die Länge der argumente und parameter übereinstimmen
    if (e.arguments.length != func.parameters.length) {
      throw new RuntimeException("Error with function call " + e.name + ": Number of parameters wrong");
    }

    // Baue ein neues environment
    Map<String, Value> newEnv = new HashMap<>();
    for (int i = 0; i < func.parameters.length; i++) {
      newEnv.put(func.parameters[i], e.arguments[i].welcome(this));
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
    // Funktionsdefinitionen für die Auswertung
    // von Funktionsaufrufen speichern
    for (var funcDef : e.funcs) {
      this.funcs.put(funcDef.name, funcDef);
    }

    return e.expression.welcome(this);
  }

}