package de.hsrm.compiler.Klang.visitors;

import java.util.Set;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.expressions.AdditiveExpression;
import de.hsrm.compiler.Klang.nodes.expressions.FunctionCall;
import de.hsrm.compiler.Klang.nodes.expressions.IntegerExpression;
import de.hsrm.compiler.Klang.nodes.expressions.ModuloExpression;
import de.hsrm.compiler.Klang.nodes.expressions.MultiplicativeExpression;
import de.hsrm.compiler.Klang.nodes.expressions.NegateExpression;
import de.hsrm.compiler.Klang.nodes.expressions.Variable;
import de.hsrm.compiler.Klang.nodes.statements.IfStatement;
import de.hsrm.compiler.Klang.nodes.statements.PrintStatement;
import de.hsrm.compiler.Klang.nodes.statements.ReturnStatement;
import de.hsrm.compiler.Klang.nodes.statements.VariableAssignment;

class GetVars implements Visitor<Void> {

  public Set<String> vars;

  public GetVars(Set<String> vars) {
    this.vars = vars;
  }

  @Override
  public Void visit(IntegerExpression e) {
    return null;
  }

  @Override
  public Void visit(Variable e) {
    return null;
  }

  @Override
  public Void visit(MultiplicativeExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(AdditiveExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(NegateExpression e) {
    e.lhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(ModuloExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(IfStatement e) {
    e.cond.welcome(this);
    e.then.welcome(this);
    if (e.alt != null) {
      e.alt.welcome(this);
    } else if (e.elif != null) {
      e.elif.welcome(this);
    }
    return null;
  }

  @Override
  public Void visit(PrintStatement e) {
    e.expression.welcome(this);
    return null;
  }

  @Override
  public Void visit(VariableAssignment e) {
    vars.add(e.name);
    e.expression.welcome(this);
    return null;
  }

  @Override
  public Void visit(ReturnStatement e) {
    e.expression.welcome(this);
    return null;
  }

  @Override
  public Void visit(Block e) {
    for (var statement : e.statements) {
      statement.welcome(this);
    }
    return null;
  }

  @Override
  public Void visit(FunctionDefinition e) {
    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(FunctionCall e) {
    for (var expression : e.arguments) {
      expression.welcome(this);
    }
    return null;
  }

  @Override
  public Void visit(Program e) {
    e.expression.welcome(this);
    for (var func : e.funcs) {
      func.welcome(this);
    }
    return null;
  }

}