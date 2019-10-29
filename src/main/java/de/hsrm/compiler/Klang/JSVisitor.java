package de.hsrm.compiler.Klang;

public class JSVisitor extends KlangBaseVisitor<Void> {
  private final StringBuilder sb;

  public JSVisitor(StringBuilder sb) {
    this.sb = sb;
  }

  @Override
  public Void visitStatement(KlangParser.StatementContext ctx) {
    for (int i = 0; i < ctx.children.size(); i++) {
      this.visit(ctx.children.get(i));
      sb.append("\n");
    }
    return null;
  }

  @Override
  public Void visitPrint(KlangParser.PrintContext ctx) {
    sb.append("console.log(");
    this.visit(ctx.expression());
    sb.append(");");
    return null;
  }

  @Override
  public Void visitMultiplicationExpression(KlangParser.MultiplicationExpressionContext ctx) {
    this.visit(ctx.atom(0));
    sb.append(" * ");
    this.visit(ctx.atom(1));
    return null;
  }

  @Override
  public Void visitAdditiveExpression(KlangParser.AdditiveExpressionContext ctx) {
    this.visit(ctx.atom(0));

    switch (ctx.op.getType()) {
    case KlangParser.ADD:
      sb.append(" + ");
      break;
    case KlangParser.SUB:
      sb.append(" - ");
      break;
    }
    this.visit(ctx.atom(1));
    return null;
  }

  @Override
  public Void visitModuloExpression(KlangParser.ModuloExpressionContext ctx) {
    this.visit(ctx.atom(0));
    sb.append(" % ");
    this.visit(ctx.atom(1));
    return null;
  }

  @Override
  public Void visitUnaryNegateExpression(KlangParser.UnaryNegateExpressionContext ctx) {
    sb.append("-");
    this.visit(ctx.atom());
    return null;
  }

  @Override
  public Void visitIntAtom(KlangParser.IntAtomContext ctx) {
    sb.append(ctx.getText());
    return null;
  }
}