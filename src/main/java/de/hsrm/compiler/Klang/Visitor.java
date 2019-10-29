package de.hsrm.compiler.Klang;

public class Visitor extends KlangBaseVisitor<Value> {

  @Override
  public Value visitPrint(KlangParser.PrintContext ctx) {
    Value value = this.visit(ctx.expression());
    System.out.println(value);
    return value;
  }

  @Override
  public Value visitMultiplicationExpression(KlangParser.MultiplicationExpressionContext ctx) {
    Value left = this.visit(ctx.atom(0));
    Value right = this.visit(ctx.atom(1));
    return new Value(left.asInteger() * right.asInteger());
  }

  @Override
  public Value visitAdditiveExpression(KlangParser.AdditiveExpressionContext ctx) {
    Value left = this.visit(ctx.atom(0));
    Value right = this.visit(ctx.atom(1));

    switch (ctx.op.getType()) {
    case KlangParser.ADD:
      return new Value(left.asInteger() + right.asInteger());
    case KlangParser.SUB:
      return new Value(left.asInteger() - right.asInteger());
    default:
      throw new RuntimeException("Unknown operator for additive expression: "+ KlangParser.VOCABULARY.getDisplayName(ctx.op.getType()));
    }
  }

  @Override
  public Value visitModuloExpression(KlangParser.ModuloExpressionContext ctx) {
    Value left = this.visit(ctx.atom(0));
    Value right = this.visit(ctx.atom(1));
    return new Value(left.asInteger() % right.asInteger());
  }

  @Override
  public Value visitUnaryNegateExpression(KlangParser.UnaryNegateExpressionContext ctx) {
    Value value = this.visit(ctx.atom());
    return new Value(-value.asInteger());
  }

  @Override
  public Value visitIntAtom(KlangParser.IntAtomContext ctx) {
    return new Value(Integer.parseInt(ctx.getText()));
  }
}