package de.hsrm.compiler.Klang.visitors;

import java.io.*;
import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.*;
import de.hsrm.compiler.Klang.nodes.statements.*;

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
  public Void visit(Program e) {
    for (var funcDef : e.funcs) {
      funcDef.welcome(this);
      ex.nl();
      ex.nl();
    }
    e.expression.welcome(this);
    ex.write(";");
    return null;
  }

  @Override
  public Void visit(IntegerExpression e) {
    ex.write(e.value);
    return null;
  }

  @Override
  public Void visit(EqualityExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" == ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(NotEqualityExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" != ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(GTExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" > ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(GTEExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" >= ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(LTExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" < ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(LTEExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" <= ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(AdditionExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" + ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(SubstractionExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" - ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(MultiplicationExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" * ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(DivisionExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" / ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(ModuloExpression e) {
    ex.write("(");
    e.lhs.welcome(this);
    ex.write(" % ");
    e.rhs.welcome(this);
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(NegateExpression e) {
    ex.write(" - ");
    e.lhs.welcome(this);
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
    } else if (e.elif != null) {
      ex.write(" else ");
      e.elif.welcome(this);
    }
    return null;
  }

  @Override
  public Void visit(WhileLoop e) {
    ex.write("while (");
    e.cond.welcome(this);
    ex.write(") ");
    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(DoWhileLoop e) {
    ex.write("do ");
    e.block.welcome(this);
    ex.write(" while (");
    e.cond.welcome(this);
    ex.write(");");
    return null;
  }

  @Override
  public Void visit(ForLoop e) {
    ex.write("for (");
    e.init.welcome(this);
    ex.write(" ");
    e.condition.welcome(this);
    ex.write("; ");
    e.step.welcome(this);
    ex.write(") ");
    e.block.welcome(this);
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
  public Void visit(VariableDeclaration e) {
    ex.write("let " + e.name);

    if (e.expression != null) {
      ex.write(" = ");
      e.expression.welcome(this);
    }

    return null;
  }

  @Override
  public Void visit(VariableAssignment e) {
    ex.write(e.name + " = ");
    e.expression.welcome(this);
    return null;
  }

  @Override
  public Void visit(ReturnStatement e) {
    ex.write("return ");
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
      if (stmt.getClass() == VariableAssignment.class || stmt.getClass() == VariableDeclaration.class) {
        ex.write(";");
      }
    }
    ex.subIndent();
    ex.nl();
    ex.write("}");
    return null;
  }

  @Override
  public Void visit(FunctionDefinition e) {
    ex.write("function ");
    ex.write(e.name);
    ex.write("(");
    boolean first = true;
    for (String param : e.parameters) {
      if (!first) {
        ex.write(", ");
      } else {
        first = false;
      }
      ex.write(param);
    }
    ex.write(") ");
    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(FunctionCall e) {
    ex.write(e.name);
    ex.write("(");
    boolean first = true;
    for (Expression arg : e.arguments) {
      if (!first) {
        ex.write(", ");
      } else {
        first = false;
      }
      arg.welcome(this);
    }
    ex.write(")");
    return null;
  }

  @Override
  public Void visit(Variable e) {
    ex.write(e.name);
    return null;
  }

}