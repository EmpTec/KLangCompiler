package de.hsrm.compiler.Klang.visitors;

import java.io.*;
import de.hsrm.compiler.Klang.nodes.Block;
import de.hsrm.compiler.Klang.nodes.expressions.AdditiveExpression;
import de.hsrm.compiler.Klang.nodes.expressions.IntegerExpression;
import de.hsrm.compiler.Klang.nodes.expressions.ModuloExpression;
import de.hsrm.compiler.Klang.nodes.expressions.MultiplicativeExpression;
import de.hsrm.compiler.Klang.nodes.expressions.NegateExpression;
import de.hsrm.compiler.Klang.nodes.statements.IfStatement;
import de.hsrm.compiler.Klang.nodes.statements.PrintStatement;
import de.hsrm.compiler.Klang.nodes.statements.Statement;;

public class PrettyPrintVisitor implements Visitor<Void> {

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

    public PrettyPrintVisitor(ExWriter ex) {
        this.ex = ex;
    }

    @Override
    public Void visit(IntegerExpression e) {
        ex.write(e.value);
        return null;
    }

    @Override
    public Void visit(MultiplicativeExpression e) {
        e.lhs.welcome(this);
        ex.write(" * ");
        e.rhs.welcome(this);
        return null;
    }

    @Override
    public Void visit(AdditiveExpression e) {
        e.lhs.welcome(this);
        ex.write(" + ");
        e.rhs.welcome(this);
        return null;
    }

    @Override
    public Void visit(NegateExpression e) {
        ex.write(" - ");
        e.lhs.welcome(this);
        return null;
    }

    @Override
    public Void visit(ModuloExpression e) {
        e.lhs.welcome(this);
        ex.write(" % ");
        e.rhs.welcome(this);
        return null;
    }

    @Override
    public Void visit(IfStatement e) {
        ex.write("if (");
        e.cond.welcome(this);
        ex.write(") ");
        e.then.welcome(this);
        if (e.alt != null) {
            ex.write(" else ");
            e.alt.welcome(this);
        }
        return null;
    }

    @Override
    public Void visit(PrintStatement e) {
        ex.write("print ");
        e.expression.welcome(this);
        ex.write(";");
        return null;
    }

    @Override
    public Void visit(Block e) {
        ex.write("{");
        ex.addIndent();
        for (Statement stmt : e.statements) {
            ex.nl();
            stmt.welcome(this);
        }
        ex.subIndent();
        ex.nl();
        ex.write("}");
        return null;
    }

}