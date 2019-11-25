package de.hsrm.compiler.Klang;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class ContextAnalysis extends KlangBaseVisitor<Node> {
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
  public Node visitBraced_block(KlangParser.Braced_blockContext ctx) {
    Statement[] statements = new Statement[ctx.statement().size()];

    for (int i = 0; i < ctx.statement().size(); i++) {
      Node currentStatement = this.visit(ctx.statement(i));
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
  public Node visitVariable_assignment(KlangParser.Variable_assignmentContext ctx) {
    String name = ctx.IDENT().getText();
    Expression expression = (Expression) this.visit(ctx.expression());
    return new VariableAssignment(name, expression);
  }

  @Override
  public Node visitReturn_statement(KlangParser.Return_statementContext ctx) {
    Expression expression = (Expression) this.visit(ctx.expression()); 
    return new ReturnStatement(expression);
  }

  @Override
  public Node visitMultiplicationExpression(KlangParser.MultiplicationExpressionContext ctx) {
    Node left = this.visit(ctx.atom(0));
    Node right = this.visit(ctx.atom(1));
    Node result = new MultiplicativeExpression((Expression) left, (Expression) right);
    result.type = left.type; // We have to actually compute this in the future
    return result;
  }

  @Override
  public Node visitAdditiveExpression(KlangParser.AdditiveExpressionContext ctx) {
    Node left = this.visit(ctx.atom(0));
    Node right = this.visit(ctx.atom(1));
    Node result = new AdditiveExpression((Expression) left, (Expression) right);
    result.type = left.type; // We have to actually compute this in the future
    return result;
  }

  @Override
  public Node visitModuloExpression(KlangParser.ModuloExpressionContext ctx) {
    Node left = this.visit(ctx.atom(0));
    Node right = this.visit(ctx.atom(1));
    Node result = new ModuloExpression((Expression) left, (Expression) right);
    result.type = left.type; // We have to actually compute this in the future
    return result;
  }

  @Override
  public Node visitUnaryNegateExpression(KlangParser.UnaryNegateExpressionContext ctx) {
    Node atom = this.visit(ctx.atom());
    Node result = new NegateExpression((Expression) atom);
    result.type = atom.type;
    return result;
  }

  @Override
  public Node visitVariable(KlangParser.VariableContext ctx) {
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
    String[] params = new String[ctx.parameters().IDENT().size()];
    for (int i = 0; i < ctx.parameters().IDENT().size(); i++) {
      params[i] = ctx.parameters().IDENT(i).getText();
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