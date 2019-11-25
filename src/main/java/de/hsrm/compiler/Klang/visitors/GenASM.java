package de.hsrm.compiler.Klang.visitors;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import de.hsrm.compiler.Klang.Value;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.FunctionDefinition;
import de.hsrm.compiler.Klang.nodes.Program;
import de.hsrm.compiler.Klang.nodes.expressions.AdditiveExpression;
import de.hsrm.compiler.Klang.nodes.expressions.FunctionCall;
import de.hsrm.compiler.Klang.nodes.expressions.IntegerExpression;
import de.hsrm.compiler.Klang.nodes.expressions.ModuloExpression;
import de.hsrm.compiler.Klang.nodes.expressions.MultiplicativeExpression;
import de.hsrm.compiler.Klang.nodes.expressions.NegateExpression;
import de.hsrm.compiler.Klang.nodes.expressions.Variable;
import de.hsrm.compiler.Klang.nodes.statements.IfStatement;
import de.hsrm.compiler.Klang.nodes.statements.PrintStatement;
import de.hsrm.compiler.Klang.nodes.statements.ReturnStatement;
import de.hsrm.compiler.Klang.nodes.statements.VariableAssignment;

public class GenASM implements Visitor<Void> {

    public static class ExWriter {
        Writer w;
        String indent = "";

        void addIndent() {
            indent = indent + "  ";
        }

        void subIndent() {
            indent = indent.substring(2);
        }

        void nl() {
            write("\n" + indent);
        }

        int lbl = 0;

        int next() {
            return lbl++;
        }

        public ExWriter(Writer w) {
            this.w = w;
        }

        void lnwrite(Object o) {
            nl();
            write(o);
        }

        void write(Object o) {
            try {
                w.write(o + "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ExWriter ex;
    Map<String, FunctionDefinition> funcs = new HashMap<>();
    Map<String, Value> env = new HashMap<>();
    String[] rs = { "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9" };

    public GenASM(ExWriter ex) {
        this.ex = ex;
    }

    @Override
    public Void visit(IntegerExpression e) {
        this.ex.write("    pushq $" + e.value + "\n");
        return null;
    }

    @Override
    public Void visit(Variable e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(MultiplicativeExpression e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(AdditiveExpression e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(NegateExpression e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(ModuloExpression e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(IfStatement e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(PrintStatement e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(VariableAssignment e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(ReturnStatement e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(Block e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(FunctionDefinition e) {
        this.ex.write(".globl " + e.name + "\n");
        this.ex.write(".type " + e.name + ", @function\n");
        this.ex.write(e.name + ":\n");
        this.ex.write("    pushq %rbp\n");
        this.ex.write("    movq %rsp, %rbp\n");
        // TODO hier fehlt noch einiges


        e.block.welcome(this);
        this.ex.write("    popq %rax\n");
        this.ex.write("    popq %rpb\n");
        this.ex.write("    ret\n");
        return null;
    }

    @Override
    public Void visit(FunctionCall e) {
        // Die ersten sechs params in die register schieben
        for (int i = 0; i < Math.min(this.rs.length, e.arguments.length); i++) {
            e.arguments[i].welcome(this);
            this.ex.write("    popq" + this.rs[i] + "\n");
        }

        // Den Rest auf den stack pushen
        for (int i = this.rs.length; i < e.arguments.length; i++) {
            e.arguments[i].welcome(this);
        }

        this.ex.write("    call " + e.name + "\n");
        return null;
    }

    @Override
    public Void visit(Program e) {
        for (var func : e.funcs) {
            func.welcome(this);
            this.ex.write("\n");
        }

        this.ex.write(".globl prog\n");
        this.ex.write(".type prog, @function\n");
        this.ex.write("prog:\n");
        this.ex.write("    pushq %rbp\n");
        this.ex.write("    movq %rsp, %rbp\n");
        e.expression.welcome(this);
        this.ex.write("    popq %rax\n");
        this.ex.write("    popq %rpb\n");
        this.ex.write("    ret\n");
        return null;
    }
}