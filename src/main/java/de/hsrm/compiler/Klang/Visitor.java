package de.hsrm.compiler.Klang;

public class Visitor extends KlangBaseVisitor<Integer> {
    @Override
    public Integer visitMultiplicativeExpr(KlangParser.MultiplicativeExprContext ctx) {
        int result = 1;
        
        for (var expr: ctx.unaryExpression()) {
            result *= this.visit(expr);
        }
        
        return result;
    }
    
    @Override
    public Integer visitUnaryExpression(KlangParser.UnaryExpressionContext ctx) {
        return Integer.parseInt(ctx.getText());
    }
}