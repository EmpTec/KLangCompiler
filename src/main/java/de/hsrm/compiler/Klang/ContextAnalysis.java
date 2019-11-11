package de.hsrm.compiler.Klang;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class ContextAnalysis extends KlangBaseVisitor<Node> {
  @Override
  public Node visitBlock(KlangParser.BlockContext ctx) {
    Statement[] statements = new Statement[ctx.statement().size()];

    for (int i = 0; i < ctx.statement().size(); i++) {
      Node currentStatement = this.visit(ctx.statement(i));
      statements[i] = (Statement) currentStatement;
    }
    
    return new Block(statements);
  }

  @Override
  public Node visitBraced_block(KlangParser.Braced_blockContext ctx) {
    Statement[] statements = new Statement[ctx.statement().size()];

    for (int i = 0; i < ctx.statement().size(); i++) {
      Node currentStatement = this.visit(ctx.statement(i));
      statements[i] = (Statement) currentStatement;
    }
    
    return new Block(statements);
  }

  @Override
  public Node visitPrint(KlangParser.PrintContext ctx) {
    Node expression = this.visit(ctx.expression());
    return new PrintStatement((Expression) expression);
  }

  @Override
  public Node visitIf_statement(KlangParser.If_statementContext ctx) {
    Node condition = this.visit(ctx.expression());
    Node thenBlock = this.visit(ctx.braced_block(0));

    if (ctx.braced_block().size() > 1) {
      Node elseBlock = this.visit(ctx.braced_block(1));
      return new IfStatement((Expression) condition, (Block) thenBlock, (Block) elseBlock);
    } else {
      return new IfStatement((Expression) condition, (Block) thenBlock, null);
    }
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
  public Node visitAtomExpression(KlangParser.AtomExpressionContext ctx) {
    return this.visit(ctx.atom());
  }

  @Override
  public Node visitIntAtom(KlangParser.IntAtomContext ctx) {
    Node n = new IntegerExpression(Integer.parseInt(ctx.getText()));
    n.type = Type.getIntegerType();
    return n;
  }
}