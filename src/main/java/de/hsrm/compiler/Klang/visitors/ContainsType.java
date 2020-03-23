package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.*;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

/**
 * Checks whether a Tree contains a function prototype or
 * struct definition that contains a given type
 */
public class ContainsType implements Visitor<Boolean> {
    private Type type;

    public ContainsType(Type type) {
        this.type = type;
    }

    @Override
    public Boolean visit(Program e) {
        boolean result = false;
        for (var funDef : e.funcs) {
            result = result || funDef.welcome(this);
        }
        
        for (var structDef: e.structs.values()) {
            result = result || structDef.welcome(this);
        }

        return result;
    }

    @Override
    public Boolean visit(FunctionDefinition e) {
        boolean result = false;
        for (var param: e.parameters) {
            result = result || param.welcome(this);
        }
        return result;
    }

    @Override
    public Boolean visit(Parameter e) {
        return e.type.equals(this.type);
    }

    @Override
    public Boolean visit(StructDefinition e) {
        boolean result = false;
        for (var field: e.fields) {
            result = result || field.welcome(this);
        }
        return result;
    }

    @Override
    public Boolean visit(StructField e) {
        return e.type.equals(this.type);
    }

    @Override
    public Boolean visit(OrExpression e) {
        return false;
    }

    @Override
    public Boolean visit(AndExpression e) {
        return false;
    }

    @Override
    public Boolean visit(NotExpression e) {
        return false;
    }

    @Override
    public Boolean visit(IntegerExpression e) {
        return false;
    }

    @Override
    public Boolean visit(FloatExpression e) {
        return false;
    }

    @Override
    public Boolean visit(BooleanExpression e) {
        return false;
    }

    @Override
    public Boolean visit(Variable e) {
        return false;
    }

    @Override
    public Boolean visit(AdditionExpression e) {
        return false;
    }

    @Override
    public Boolean visit(EqualityExpression e) {
        return false;
    }

    @Override
    public Boolean visit(NotEqualityExpression e) {
        return false;
    }

    @Override
    public Boolean visit(GTExpression e) {
        return false;
    }

    @Override
    public Boolean visit(GTEExpression e) {
        return false;
    }

    @Override
    public Boolean visit(LTExpression e) {
        return false;
    }

    @Override
    public Boolean visit(LTEExpression e) {
        return false;
    }

    @Override
    public Boolean visit(SubstractionExpression e) {
        return false;
    }

    @Override
    public Boolean visit(MultiplicationExpression e) {
        return false;
    }

    @Override
    public Boolean visit(DivisionExpression e) {
        return false;
    }

    @Override
    public Boolean visit(ModuloExpression e) {
        return false;
    }

    @Override
    public Boolean visit(NegateExpression e) {
        return false;
    }

    @Override
    public Boolean visit(IfStatement e) {
        return false;
    }

    @Override
    public Boolean visit(WhileLoop e) {
        return false;
    }

    @Override
    public Boolean visit(DoWhileLoop e) {
        return false;
    }

    @Override
    public Boolean visit(ForLoop e) {
        return false;
    }

    @Override
    public Boolean visit(VariableDeclaration e) {
        return false;
    }

    @Override
    public Boolean visit(VariableAssignment e) {
        return false;
    }

    @Override
    public Boolean visit(ReturnStatement e) {
        return false;
    }

    @Override
    public Boolean visit(Block e) {
        return false;
    }

    @Override
    public Boolean visit(FunctionCall e) {
        return false;
    }

    @Override
    public Boolean visit(StructFieldAccessExpression e) {
        return false;
    }

    @Override
    public Boolean visit(ConstructorCall e) {
        return false;
    }

    @Override
    public Boolean visit(NullExpression e) {
        return false;
    }

    @Override
    public Boolean visit(DestructorCall e) {
        return false;
    }

    @Override
    public Boolean visit(FieldAssignment e) {
        return false;
    }
}