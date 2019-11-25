package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

public interface Visitor<R> {
    R visit(IntegerExpression e);
    R visit(Variable e);
    R visit(MultiplicativeExpression e);
    R visit(AdditiveExpression e);
    R visit(NegateExpression e);
    R visit(ModuloExpression e);
    R visit(IfStatement e);
    R visit(PrintStatement e);
    R visit(VariableAssignment e);
    R visit(ReturnStatement e);
    R visit(Block e);
    R visit(FunctionDefinition e);
    R visit(FunctionCall e);
    R visit(Program e);
}