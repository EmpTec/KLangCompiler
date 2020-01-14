package de.hsrm.compiler.Klang;

import java.util.Set;
import java.util.HashSet;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class ContextAnalysis extends KlangBaseVisitor<Node> {
  Set<String> vars = new HashSet<>();

  @Override
  public Node visitProgram(KlangParser.ProgramContext ctx) {
    FunctionDefinition[] funcs = new FunctionDefinition[ctx.functionDef().size()];
    for (int i = 0; i < ctx.functionDef().size(); i++) {
      funcs[i] = (FunctionDefinition) this.visit(ctx.functionDef(i));
    }
    Expression expression = (Expression) this.visit(ctx.expression());
    return new Program(funcs, expression);
  }

  @Override
  public Node visitStatement(KlangParser.StatementContext ctx) {
    // The first child is the proper context we need to visit
    // The second child is either null or just a SCOL!
    return this.visit(ctx.getChild(0));
  }

  @Override
  public Node visitBraced_block(KlangParser.Braced_blockContext ctx) {
    Statement[] statements = new Statement[ctx.statement().size()];
    
    for (int i = 0; i < ctx.statement().size(); i++) {
      var stmtCtx = ctx.statement(i);
      Node currentStatement = this.visit(stmtCtx);
      statements[i] = (Statement) currentStatement;
    }

    Block result = new Block(statements);
    result.type = null;
    return result;
  }

  @Override
  public Node visitPrint(KlangParser.PrintContext ctx) {
    Node expression = this.visit(ctx.expression());
    return new PrintStatement((Expression) expression);
  }

  @Override
  public Node visitIf_statement(KlangParser.If_statementContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node thenBlock = this.visit(ctx.then);

    if (ctx.alt != null) {
      Node elseBlock = this.visit(ctx.alt);
      return new IfStatement((Expression) condition, (Block) thenBlock, (Block) elseBlock);
    } else if (ctx.elif != null) {
      Node elif = this.visit(ctx.elif);
      return new IfStatement((Expression) condition, (Block) thenBlock, (IfStatement) elif);
    } else {
      return new IfStatement((Expression) condition, (Block) thenBlock);
    }
  }

  @Override
  public Node visitWhileLoop(KlangParser.WhileLoopContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node block = this.visit(ctx.braced_block());
    return new WhileLoop((Expression) condition, (Block) block);
  }

  @Override
  public Node visitDoWhileLoop(KlangParser.DoWhileLoopContext ctx) {
    Node condition = this.visit(ctx.cond);
    Node block = this.visit(ctx.braced_block());
    return new DoWhileLoop((Expression) condition, (Block) block);
  }


  @Override
  public Node visitVariable_declaration(KlangParser.Variable_declarationContext ctx) {
    String name = ctx.IDENT().getText();

    if (this.vars.contains(name)) {
      throw new RuntimeException("Redeclaration of variable with name \"" + name +"\".");
    }

    this.vars.add(name);

    if (ctx.expression() != null) {
      return new VariableDeclaration(name, (Expression) this.visit(ctx.expression()));
    } else {
      return new VariableDeclaration(name);
    }
  }

  @Override
  public Node visitVariable_assignment(KlangParser.Variable_assignmentContext ctx) {
    String name = ctx.IDENT().getText();

    if (!this.vars.contains(name)) {
      throw new RuntimeException("Variable with name \"" + name + "\" not defined.");
    }

    Expression expression = (Expression) this.visit(ctx.expression());
    return new VariableAssignment(name, expression);
  }

  @Override
  public Node visitReturn_statement(KlangParser.Return_statementContext ctx) {
    Expression expression = (Expression) this.visit(ctx.expression());
    return new ReturnStatement(expression);
  }

  @Override
  public Node visitAdditionExpression(KlangParser.AdditionExpressionContext ctx) {
    return new AdditionExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitEqualityExpression(KlangParser.EqualityExpressionContext ctx) { 
    return new EqualityExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitNotEqualityExpression(KlangParser.NotEqualityExpressionContext ctx) { 
    return new NotEqualityExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitLessThanExpression(KlangParser.LessThanExpressionContext ctx) { 
    return new LTExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitGreaterThanExpression(KlangParser.GreaterThanExpressionContext ctx) { 
    return new GTExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitLessThanOrEqualToExpression(KlangParser.LessThanOrEqualToExpressionContext ctx) { 
    return new LTEExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override 
  public Node visitGreaterThanOrEqualToExpression(KlangParser.GreaterThanOrEqualToExpressionContext ctx) { 
    return new GTEExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override
  public Node visitSubstractionExpression(KlangParser.SubstractionExpressionContext ctx) {
    return new SubstractionExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override
  public Node visitMultiplicationExpression(KlangParser.MultiplicationExpressionContext ctx) {
    return new MultiplicationExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override
  public Node visitDivisionExpression(KlangParser.DivisionExpressionContext ctx) {
    return new DivisionExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override
  public Node visitModuloExpression(KlangParser.ModuloExpressionContext ctx) {
    return new ModuloExpression((Expression) this.visit(ctx.lhs), (Expression) this.visit(ctx.rhs));
  }

  @Override
  public Node visitNegateExpression(KlangParser.NegateExpressionContext ctx) {
    return new NegateExpression((Expression) this.visit(ctx.expression()));
  }

  @Override
  public Node visitVariable(KlangParser.VariableContext ctx) {
    String name = ctx.IDENT().getText();

    if (!this.vars.contains(name)) {
      throw new RuntimeException("Variable with name \"" + name + "\" not defined.");
    }

    return new Variable(ctx.IDENT().getText());
  }

  @Override
  public Node visitAtomExpression(KlangParser.AtomExpressionContext ctx) {
    return this.visit(ctx.atom());
  }

  @Override
  public Node visitIntAtom(KlangParser.IntAtomContext ctx) {
    Node n = new IntegerExpression(Integer.parseInt(ctx.getText()));
    n.type = Type.getIntegerType();
    return n;
  }

  @Override
  public Node visitFunctionDef(KlangParser.FunctionDefContext ctx) {
    String name = ctx.funcName.getText();

    // Create a new set for the variables of the current function
    // this will be filled in the variable declaration visitor aswell
    this.vars = new HashSet<>();

    String[] params = new String[ctx.parameters().IDENT().size()];
    for (int i = 0; i < ctx.parameters().IDENT().size(); i++) {
      String paramName = ctx.parameters().IDENT(i).getText();
      params[i] = paramName;
      this.vars.add(paramName); // add the param as a variable
    }
    Node block = this.visit(ctx.braced_block());
    return new FunctionDefinition(name, params, (Block) block);
  }

  @Override
  public Node visitFunctionCallExpression(KlangParser.FunctionCallExpressionContext ctx) {
    String name = ctx.functionCall().IDENT().getText();
    Expression[] args = new Expression[ctx.functionCall().arguments().expression().size()];
    for (int i = 0; i < ctx.functionCall().arguments().expression().size(); i++) {
      args[i] = (Expression) this.visit(ctx.functionCall().arguments().expression(i));
    }
    return new FunctionCall(name, args);
  }
}