package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Parameter;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

public interface Visitor<R> {
    R visit(OrExpression e);
    R visit(AndExpression e);
    R visit (NotExpression e);
    R visit(IntegerExpression e);
    R visit(FloatExpression e);
    R visit(BooleanExpression e);
    R visit(Variable e);
    R visit(AdditionExpression e);
    R visit(EqualityExpression e);
    R visit(NotEqualityExpression e);
    R visit(GTExpression e);
    R visit(GTEExpression e);
    R visit(LTExpression e);
    R visit(LTEExpression e);
    R visit(SubstractionExpression e);
    R visit(MultiplicationExpression e);
    R visit(DivisionExpression e);
    R visit(ModuloExpression e);
    R visit(NegateExpression e);
    R visit(IfStatement e);
    R visit(WhileLoop e);
    R visit(DoWhileLoop e);
    R visit(ForLoop e);
    R visit(PrintStatement e);
    R visit(VariableDeclaration e);
    R visit(VariableAssignment e);
    R visit(ReturnStatement e);
    R visit(Block e);
    R visit(FunctionDefinition e);
    R visit(FunctionCall e);
    R visit(Program e);
    R visit(Parameter e);
}