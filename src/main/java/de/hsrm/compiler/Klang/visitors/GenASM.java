package de.hsrm.compiler.Klang.visitors;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
    Map<String, Integer> env = new HashMap<>();
    String[] rs = { "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9" };
    private int lCount = 0; // Invariante: lCount ist benutzt

    public GenASM(ExWriter ex) {
        this.ex = ex;
    }

    @Override
    public Void visit(IntegerExpression e) {
        this.ex.write("    movq $" + e.value + ", %rax\n");
        return null;
    }

    @Override
    public Void visit(Variable e) {
        this.ex.write("    movq " +this.env.get(e.name) +"(%rbp), %rax\n");
        return null;
    }

    @Override
    public Void visit(MultiplicativeExpression e) {
        e.lhs.welcome(this);
        this.ex.write("    movq  %rax, %rbx\n");
        e.rhs.welcome(this);
        this.ex.write("    imulq %rbx, %rax\n");
        return null;
    }

    @Override
    public Void visit(AdditiveExpression e) {
        e.lhs.welcome(this);
        this.ex.write("    movq  %rax, %rbx\n");
        e.rhs.welcome(this);
        this.ex.write("    iaddq %rbx, %rax\n");
        return null;
    }

    @Override
    public Void visit(NegateExpression e) {
        e.lhs.welcome(this);
        this.ex.write("    neg %rax\n");
        return null;
    }

    @Override
    public Void visit(ModuloExpression e) {
        e.rhs.welcome(this);
        this.ex.write("    movq %rax, %rbx\n");
        e.lhs.welcome(this);
        this.ex.write("    xor %rdx, %rdx\n"); // clear remainder register
        this.ex.write("    idiv %ebx\n"); // %rax/%rbx, remainder now in %rdx
        this.ex.write("    movq %rdx, %rax\n");
        return null;
    }

    @Override
    public Void visit(IfStatement e) {
        int then = ++lCount;
        int end = ++lCount;
        
        e.cond.welcome(this);
        this.ex.write("    cmp $0, %rax\n");
        this.ex.write("    jz L" + then + "\n");
        // else Part
        if (e.alt != null) {
            e.alt.welcome(this);
        } else if (e.elif != null) {
            e.elif.welcome(this);
        }
        this.ex.write("   j L" + end + "\n");
        // then Part
        this.ex.write(".L" + then + "/n");
        e.then.welcome(this);
        this.ex.write(".L" + end + "\n");
        return null;
    }

    @Override
    public Void visit(PrintStatement e) {
        throw new RuntimeException("Das machen wir mal nicht, ne?!");
    }

    @Override
    public Void visit(VariableAssignment e) {
        e.expression.welcome(this);
        int offset = this.env.get(e.name);
        this.ex.write("    movq %rax, " + offset + "(%rbp)\n");
        return null;
    }

    @Override
    public Void visit(ReturnStatement e) {
        e.expression.welcome(this);
        this.ex.write("    popq %rbp\n");
        this.ex.write("    ret\n");
        return null;
    }

    @Override
    public Void visit(Block e) {
        for (var statement : e.statements) {
            statement.welcome(this);
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition e) {
        this.ex.write(".globl " + e.name + "\n");
        this.ex.write(".type " + e.name + ", @function\n");
        this.ex.write(e.name + ":\n");
        this.ex.write("    pushq %rbp\n");
        this.ex.write("    movq %rsp, %rbp\n");

        // hole die anzahl der lokalen variablen
        Set<String> vars = new TreeSet<String>();
        GetVars getvars = new GetVars(vars);
        getvars.visit(e);
        // System.out.println("Function: " + e.name);
        // System.out.println("vars size: " + vars.size());
        // for (var s : vars) {
        //   System.out.println(s);
        // }

        // Erzeuge ein environment
        this.env = new HashMap<String, Integer>();

        // Merke dir die offsets der parameter, die direkt auf den stack gelegt wurden
        int m = e.parameters.length - this.rs.length;
        for (int i = this.rs.length; i < e.parameters.length; i++) {
            int j = i - this.rs.length;
            this.env.put(e.parameters[i], -((m - j) * 8));
        }

        // pushe die aufrufparameter aus den Registern wieder auf den Stack
        for (int i = 0; i < Math.min(this.rs.length, e.parameters.length); i++) {
            this.ex.write("    popq " + this.rs[i] + "\n");
            this.env.put(e.parameters[i], (i + 2) * 8);
        }

        // Reserviere Platz auf dem Stack für jede lokale variable
        int offset = 2 + Math.min(this.rs.length, e.parameters.length); // der offset des letzten register parameters
        for (String lok_var: vars) {
            this.ex.write("    pushq $0\n");
            this.env.put(lok_var, ++offset * 8);
        }

        e.block.welcome(this);

        // this.ex.write("    popq %rax\n");
        // this.ex.write("    popq %rbp\n");
        // this.ex.write("    ret\n");
        return null;
    }

    @Override
    public Void visit(FunctionCall e) {
        // Die ersten sechs params in die register schieben
        for (int i = 0; i < Math.min(this.rs.length, e.arguments.length); i++) {
            e.arguments[i].welcome(this);
            this.ex.write("    movq %rax,  " + this.rs[i] + "\n");
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
      this.ex.write(".text\n");
        for (var func : e.funcs) {
            func.welcome(this);
            this.ex.write("\n");
        }
        this.ex.write(".globl main\n");
        this.ex.write(".type main, @function\n");
        this.ex.write("main:\n");
        this.ex.write("    pushq %rbp\n");
        this.ex.write("    movq %rsp, %rbp\n");
        e.expression.welcome(this);
        // this.ex.write("    popq %rax\n");
        this.ex.write("    popq %rbp\n");
        this.ex.write("    ret\n"); 
        this.ex.write("\n");
        this.ex.write("\n");
        return null;
    }
}