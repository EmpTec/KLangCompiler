package de.hsrm.compiler.Klang.visitors;

import java.util.Set;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;

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
  public Void visit(EqualityExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(NotEqualityExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(GTExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(GTEExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(LTExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(LTEExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(AdditionExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(SubstractionExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(MultiplicationExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(DivisionExpression e) {
    e.lhs.welcome(this);
    e.rhs.welcome(this);
    return null;
  }

  @Override
  public Void visit(ModuloExpression e) {
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
  public Void visit(WhileLoop e) {
    e.cond.welcome(this);
    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(DoWhileLoop e) {
    e.cond.welcome(this);
    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(PrintStatement e) {
    e.expression.welcome(this);
    return null;
  }

  @Override
  public Void visit(VariableDeclaration e) {
    vars.add(e.name);
    return null;
  }

  @Override
  public Void visit(VariableAssignment e) {
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