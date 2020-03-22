package de.hsrm.compiler.Klang.visitors;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.*;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class GenCHeader implements Visitor<Void> {
    public StringBuilder sb = new StringBuilder();

    @Override
    public Void visit(Program e) {
        ContainsType containsBool = new ContainsType(Type.getBooleanType());
        boolean doesContainBool = e.welcome(containsBool);

        if (doesContainBool) {
            sb.append("#include <stdbool.h>");
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }

        for (var funDef : e.funcs) {
            funDef.welcome(this);
            sb.append(System.lineSeparator());
        }
        
        for (var structDef: e.structs.values()) {
            structDef.welcome(this);
            sb.append(System.lineSeparator());
        }

        return null;
    }

    @Override
    public Void visit(FunctionDefinition e) {
        sb.append(e.type.getCName());
        sb.append(" " + e.name);
        sb.append("(");
        boolean first = true;
        for (var param: e.parameters) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            param.welcome(this);
        }
        sb.append(");");
        return null;
    }

    @Override
    public Void visit(Parameter e) {
        sb.append(e.type.getCName());
        sb.append(" " + e.name);
        return null;
    }

    @Override
    public Void visit(StructDefinition e) {
        sb.append("struct " + e.name + " {");
        sb.append(System.lineSeparator());
        for (var field: e.fields) {
            sb.append("\t");
            field.welcome(this);
            sb.append(System.lineSeparator());
        }
        sb.append("};");
        return null;
    }

    @Override
    public Void visit(StructField e) {
        sb.append(e.type.getCName());
        sb.append(" " + e.name + ";");
        return null;
    }

    @Override
    public Void visit(OrExpression e) {
        return null;
    }

    @Override
    public Void visit(AndExpression e) {
        return null;
    }

    @Override
    public Void visit(NotExpression e) {
        return null;
    }

    @Override
    public Void visit(IntegerExpression e) {
        return null;
    }

    @Override
    public Void visit(FloatExpression e) {
        return null;
    }

    @Override
    public Void visit(BooleanExpression e) {
        return null;
    }

    @Override
    public Void visit(Variable e) {
        return null;
    }

    @Override
    public Void visit(AdditionExpression e) {
        return null;
    }

    @Override
    public Void visit(EqualityExpression e) {
        return null;
    }

    @Override
    public Void visit(NotEqualityExpression e) {
        return null;
    }

    @Override
    public Void visit(GTExpression e) {
        return null;
    }

    @Override
    public Void visit(GTEExpression e) {
        return null;
    }

    @Override
    public Void visit(LTExpression e) {
        return null;
    }

    @Override
    public Void visit(LTEExpression e) {
        return null;
    }

    @Override
    public Void visit(SubstractionExpression e) {
        return null;
    }

    @Override
    public Void visit(MultiplicationExpression e) {
        return null;
    }

    @Override
    public Void visit(DivisionExpression e) {
        return null;
    }

    @Override
    public Void visit(ModuloExpression e) {
        return null;
    }

    @Override
    public Void visit(NegateExpression e) {
        return null;
    }

    @Override
    public Void visit(IfStatement e) {
        return null;
    }

    @Override
    public Void visit(WhileLoop e) {
        return null;
    }

    @Override
    public Void visit(DoWhileLoop e) {
        return null;
    }

    @Override
    public Void visit(ForLoop e) {
        return null;
    }

    @Override
    public Void visit(VariableDeclaration e) {
        return null;
    }

    @Override
    public Void visit(VariableAssignment e) {
        return null;
    }

    @Override
    public Void visit(ReturnStatement e) {
        return null;
    }

    @Override
    public Void visit(Block e) {
        return null;
    }

    @Override
    public Void visit(FunctionCall e) {
        return null;
    }

    @Override
    public Void visit(StructFieldAccessExpression e) {
        return null;
    }

    @Override
    public Void visit(ConstructorCall e) {
        return null;
    }

    @Override
    public Void visit(NullExpression e) {
        return null;
    }

    @Override
    public Void visit(DestructorCall e) {
        return null;
    }

    @Override
    public Void visit(FieldAssignment e) {
        return null;
    }

}