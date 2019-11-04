package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

public interface Visitor<R> {
    R visit(IntegerExpression e);
    R visit(MultiplicativeExpression e);
    R visit(AdditiveExpression e);
    R visit(NegateExpression e);
    R visit(ModuloExpression e);
    R visit(IfStatement e);
    R visit(PrintStatement e);
}