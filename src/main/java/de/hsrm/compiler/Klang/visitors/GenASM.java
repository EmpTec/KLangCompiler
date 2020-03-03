package de.hsrm.compiler.Klang.visitors;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.ForLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

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

  private class FloatWriter {
    private StringBuilder sb = new StringBuilder();
    private int id = -1;

    public String getFloat(double d) {
      String binary = Long.toBinaryString(Double.doubleToLongBits(d));
      String upper = binary.substring(0, 30);
      String lower = binary.substring(31, 61);
      long first = Long.parseLong(lower, 2);
      long second = Long.parseLong(upper, 2);
      String lbl = ".FL" + ++id;
      sb.append(lbl);
      sb.append(":\n");
      sb.append("\t.long ");
      sb.append(first);
      sb.append("\n");
      sb.append("\t.long ");
      sb.append(second);
      sb.append("\n");
      return lbl;
    }

    public String getNegateFloat() {
      String lbl = ".FL" + ++id;
      sb.append(lbl);
      sb.append(":\n");
      sb.append("\t.long ");
      sb.append("0");
      sb.append("\n");
      sb.append("\t.long ");
      sb.append("-2147483648");
      sb.append("\n");
      return lbl;
    }

    public String getFloatSection() {
      return sb.toString();
    }
  }

  public ExWriter ex;
  private FloatWriter fw = new FloatWriter();
  private String mainName;
  Map<String, Integer> env = new HashMap<>();
  Set<String> vars;
  String[] rs = { "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9" };
  String[] frs = {"xmm0", "xmm1", "xmm2", "xmm3", "xmm4", "xmm5", "xmm6", "xmm7"};
  private int lCount = 0; // Invariante: lCount ist benutzt

  private void intToFloat(String src, String dst) {
    this.ex.write("    cvtsi2sd " + src +", " + dst + "\n");
  }

  private boolean prepareRegisters(Expression lhs, Expression rhs) {
    boolean lhsIsFloat = lhs.type.equals(Type.getFloatType());
    boolean rhsIsFloat = rhs.type.equals(Type.getFloatType());
    if (lhsIsFloat || rhsIsFloat) {
      lhs.welcome(this);
      if (!lhsIsFloat) {
        this.intToFloat("%rax", "%xmm1");
      } else {
        this.ex.write("    movsd %xmm0, %xmm1\n");
      }
      rhs.welcome(this);
      if (!rhsIsFloat) {
        this.intToFloat("%rax", "%xmm1");
      }
      return true;
    }
    lhs.welcome(this);
    this.ex.write("    pushq %rax\n");
    rhs.welcome(this);
    this.ex.write("    popq %rbx\n");
    return false;
  }


  public GenASM(ExWriter ex, String mainName) {
    this.ex = ex;
    this.mainName = mainName;
  }

  public GenASM(ExWriter ex) {
    this.ex = ex;
    this.mainName = "main";
  }

  @Override
  public Void visit(IntegerExpression e) {
    this.ex.write("    movq $" + e.value + ", %rax\n");
    return null;
  }

  @Override
  public Void visit(FloatExpression e) {
    String floatLabel = fw.getFloat(e.value);
    this.ex.write("    movsd " + floatLabel + "(%rip), %xmm0\n");
    return null;
  }

  @Override
  public Void visit(BooleanExpression e) {
    this.ex.write("    movq $" + (e.value ? 1 : 0) + ", %rax\n");
    return null;
  }

  @Override
  public Void visit(Variable e) {
    if (e.type.equals(Type.getFloatType())) {
      this.ex.write("    movsd " + this.env.get(e.name) + "(%rbp), %xmm0\n");
    } else {
      this.ex.write("    movq " + this.env.get(e.name) + "(%rbp), %rax\n");
    }
    return null;
  }

  @Override
  public Void visit(EqualityExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
      this.ex.write("    je .L" + lblTrue + "\n");
      // false
      this.ex.write("    movq $0, %rax\n");
      this.ex.write("    jmp .L" + lblEnd + "\n");
      this.ex.write(".L" + lblTrue + ":\n");
      // true
      this.ex.write("   movq $1, %rax\n");
      this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(NotEqualityExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
    this.ex.write("    jne .L" + lblTrue + "\n");
    // false
    this.ex.write("    movq $0, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");
    this.ex.write(".L" + lblTrue + ":\n");
    // true
    this.ex.write("   movq $1, %rax\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(GTExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
    this.ex.write("    jg .L" + lblTrue + "\n");
    // false
    this.ex.write("    movq $0, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");
    this.ex.write(".L" + lblTrue + ":\n");
    // true
    this.ex.write("   movq $1, %rax\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(GTEExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
    this.ex.write("    jge .L" + lblTrue + "\n");
    // false
    this.ex.write("    movq $0, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");
    this.ex.write(".L" + lblTrue + ":\n");
    // true
    this.ex.write("    movq $1, %rax\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(LTExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
    this.ex.write("    jl .L" + lblTrue + "\n");
    // false
    this.ex.write("    movq $0, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");
    this.ex.write(".L" + lblTrue + ":\n");
    // true
    this.ex.write("    movq $1, %rax\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(LTEExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    ucomisd %xmm0, %xmm1\n");
    } else {
      this.ex.write("    cmp %rax, %rbx\n");
    }
    this.ex.write("    jle .L" + lblTrue + "\n");
    // false
    this.ex.write("    movq $0, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");
    this.ex.write(".L" + lblTrue + ":\n");
    // true
    this.ex.write("    movq $1, %rax\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(AdditionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    addsd %xmm1, %xmm0\n");
    } else {
      this.ex.write("    addq %rbx, %rax\n");
    }
    return null;
  }

  @Override
  public Void visit(SubstractionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    subsd %xmm1, %xmm0\n");
    } else {
      this.ex.write("    subq %rax, %rbx\n");
      this.ex.write("    movq %rbx, %rax\n");
    }
    return null;
  }

  @Override
  public Void visit(MultiplicationExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    mulsd %xmm1, %xmm0\n");
    } else {
      this.ex.write("    imulq %rbx, %rax\n");
    }
    return null;
  }

  @Override
  public Void visit(DivisionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      this.ex.write("    divsd %xmm1, %xmm0\n");
    } else {
      this.ex.write("    xor %rdx, %rdx\n"); // clear upper part of division
      this.ex.write("    idiv %rbx\n"); // %rax/%rbx, quotient now in %rax
    }
    return null;
  }

  @Override
  public Void visit(ModuloExpression e) {
    e.lhs.welcome(this);
    this.ex.write("    pushq %rax\n");
    e.rhs.welcome(this);
    this.ex.write("    movq %rax, %rbx\n");
    this.ex.write("    popq %rax\n");
    this.ex.write("    xor %rdx, %rdx\n"); // clear upper part of division
    this.ex.write("    idiv %rbx\n"); // %rax/%rbx, remainder now in %rdx
    this.ex.write("    movq %rdx, %rax\n");
    return null;
  }

  @Override
  public Void visit(NegateExpression e) {
    e.lhs.welcome(this);
    if (e.lhs.type.equals(Type.getFloatType())) {
      String floatLabel = fw.getNegateFloat();
      this.ex.write("    movsd " + floatLabel + "(%rip), %xmm1\n");
      this.ex.write("    xorpd   %xmm1, %xmm0\n");
    } else {
      this.ex.write("    neg %rax\n");
    }
    return null;
  }

  @Override
  public Void visit(OrExpression e) {
    int lblTrue = ++lCount;
    int lblFalse = ++lCount;
    int lblEnd = ++lCount;

    // Werte LHS aus
    // Wenn LHS != 0 bedeutet das true
    // also können wir direkt sagen dass das Ergebnis true ist
    e.lhs.welcome(this);
    this.ex.write("    cmpq $0, %rax\n");
    this.ex.write("    jne .L" + lblTrue + "\n");

    // LHS war false, also werte RHS aus
    // Wenn RHS == 0 bedeutet das false,
    // also ist das Gesamtergebnis false
    e.rhs.welcome(this);
    this.ex.write("    cmpq $0, %rax\n");
    this.ex.write("    je .L" + lblFalse + "\n");

    // Die Expression wertet zu true aus
    // Springe am false Teil vorbei
    this.ex.write(".L" + lblTrue + ":\n");
    this.ex.write("    movq $1, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");

    // Die Expressoin wertet zu false aus
    this.ex.write(".L" + lblFalse + ":\n");
    this.ex.write("    movq $0, %rax\n");

    // Das hier ist das ende
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(AndExpression e) {
    int lblTrue = ++lCount;
    int lblFalse = ++lCount;
    int lblEnd = ++lCount;

    // Werte LHS aus
    // Wenn LHS == 0, bedeutet das false
    // also können wir direkt sagen dass das Ergebnis false ist
    e.lhs.welcome(this);
    this.ex.write("    cmpq $0, %rax\n");
    this.ex.write("    je .L" + lblFalse + "\n");

    // LHS war true, also werte RHS aus.
    // Wenn RHS == 0, bedeutet das false
    // also ist das Gesamtergebnis false
    e.rhs.welcome(this);
    this.ex.write("    cmpq $0, %rax\n");
    this.ex.write("    je .L" + lblFalse +"\n");

    // Die Expression wertet zu true aus
    // Springe am false Teil vorbei
    this.ex.write(".L" + lblTrue +":\n");
    this.ex.write("    movq $1, %rax\n");
    this.ex.write("    jmp .L" + lblEnd + "\n");

    // Die Expressoin wertet zu false aus
    this.ex.write(".L" + lblFalse +":\n");
    this.ex.write("    movq $0, %rax\n");

    // Das hier ist das ende
    this.ex.write(".L" + lblEnd +":\n");
    return null;
  }

  @Override
  public Void visit(NotExpression e) {
    int lblFalse = ++lCount;
    int lblEnd = ++lCount;
    
    // Werte LHS aus
    // Wenn LHS != 0 bedeutet das true, also jumpe zum false Teil
    // Wenn nicht, falle durch zum true Teil
    e.lhs.welcome(this);
    this.ex.write("    cmpq $0, %rax\n");
    this.ex.write("    jne .L" +lblFalse +"\n");

    // Hier ist das Ergebnis true
    // Springe am false Teil vorbei
    this.ex.write("    movq $1, %rax\n");
    this.ex.write("    jmp .L" +lblEnd +"\n");

    // Hier ist das Ergebnis false
    // Falle zum Ende durch
    this.ex.write(".L" +lblFalse + ":\n");
    this.ex.write("movq $0, %rax\n");

    // Hier ist das Ende
    this.ex.write(".L" +lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(IfStatement e) {
    int lblElse = ++lCount;
    int lblEnd = ++lCount;
    boolean hasElse = e.alt != null || e.elif != null;
    e.cond.welcome(this);
    this.ex.write("    cmp $0, %rax\n");
    // in case of cond evaluating to false, jump to else/elif
    // Jump to end if there is no else part, this saves a label declaration
    if (hasElse) {
      this.ex.write("    jz .L" + lblElse + "\n");
    } else {
      this.ex.write("    jz .L" + lblEnd + "\n");
    }
    e.then.welcome(this);
    if (hasElse) {
      this.ex.write("    jmp .L" + lblEnd + "\n");
      this.ex.write(".L" + lblElse + ":\n");
      if (e.alt != null) {
        e.alt.welcome(this);
      } else {
        e.elif.welcome(this);
      }
    }
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(WhileLoop e) {
    int lblCond = ++lCount;
    int lblEnd = ++lCount;
    this.ex.write(".L" + lblCond + ":\n");
    e.cond.welcome(this);
    this.ex.write("    cmp $0, %rax\n");
    this.ex.write("    jz .L" + lblEnd + "\n");
    e.block.welcome(this);
    this.ex.write("    jmp .L" + lblCond + "\n");
    this.ex.write(".L" + lblEnd + ":\n");
    return null;
  }

  @Override
  public Void visit(DoWhileLoop e) {
    int lblStart = ++lCount;
    this.ex.write(".L" + lblStart + ":\n");
    e.block.welcome(this);
    e.cond.welcome(this);
    this.ex.write("    cmp $0, %rax\n");
    this.ex.write("    jnz .L" + lblStart + "\n");
    return null;
  }

  @Override
  public Void visit(ForLoop e) {
    int lblStart = ++lCount;
    int lblEnd = ++lCount;
    e.init.welcome(this);
    this.ex.write(".L" + lblStart + ":\n");
    e.condition.welcome(this);
    this.ex.write("    cmp $0, %rax\n");
    this.ex.write("    jz .L" + lblEnd + "\n");
    e.block.welcome(this);
    e.step.welcome(this);
    this.ex.write("    jmp .L" + lblStart + "\n");
    this.ex.write(".L" + lblEnd + ":\n");

    return null;
  }

  @Override
  public Void visit(PrintStatement e) {
    throw new RuntimeException("Das machen wir mal nicht, ne?!");
  }

  @Override
  public Void visit(VariableDeclaration e) {
    // If there is an initialization present,
    // push it to the location of the local var
    if (e.expression != null) {
      e.expression.welcome(this);
      int offset = this.env.get(e.name);
      this.ex.write("    movq %rax, " + offset + "(%rbp)\n");
    }
    return null;
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
    this.ex.write("    movq %rbp, %rsp\n");
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
    this.vars = new TreeSet<String>();
    HashMap<String, Type> types = new HashMap<String, Type>();
    GetVars getvars = new GetVars(vars, types);
    getvars.visit(e);

    // Erzeuge ein environment
    this.env = new HashMap<String, Integer>();

    // Merke dir die offsets der parameter, die direkt auf den stack gelegt wurden
    int offset = 16; // Per Stack übergebene Parameter liegen über unserm BSP
    // Per stack übergebene variablen in env registrieren
    for (int i = this.rs.length; i < e.parameters.length; i++) {
      env.put(e.parameters[i].name, offset);
      offset += 8;
    }

    // pushe die aufrufparameter aus den Registern wieder auf den Stack
    offset = 0;
    for (int i = 0; i < Math.min(this.rs.length, e.parameters.length); i++) {
      this.ex.write("    pushq " + this.rs[i] + "\n");
      offset -= 8;
      this.env.put(e.parameters[i].name, offset); // negative, liegt unter aktuellem BP
    }

    // Reserviere Platz auf dem Stack für jede lokale variable
    for (String lok_var : vars) {
      offset -= 8;
      this.ex.write("    pushq $0\n");
      this.env.put(lok_var, offset);
    }

    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(FunctionCall e) {
    /*
      Idee:
      Über arguments iterieren, sich für jedes Argument merken an welche Position es kommt
      Über e.arguments iterieren, jedes welcomen, ergebnis auf den stack pushen
      Vom stack in die jeweiligen register / den richtigen Platz auf den Stack pushen
    */
    // // An xmmIdxy[i] steht ein index, der in e.arguments zeigt
    // // this.frs[i] = register | xmmIdxs[i] = index in arguments das in dieses register gehört
    // int[] xmmIdxs = new int[this.frs.length];
    // int fi = 0;
    // // Selbe Idee wie bei xmmIdxs
    // int[] rIdxs = new int[this.rs.length];
    // int ri = 0;
    //// Selbe Idee wie bei xmmIdxs
    // ArrayList<Integer> stackIdxs = new ArrayList<Integer>();
    //
    // // Go through arguments
    // // sort them into the memory regions they go when being passed to function later on
    // for (int i = 0; i < e.arguments.length; i++) {
    //   var arg = e.arguments[i];
    //   if (arg.type.equals(Type.getFloatType())) {
    //     if (fi >= this.frs.length) {
    //       // Float onto stack
    //       stackIdxs.add(i);
    //     } else {
    //       // Float into float reg
    //       xmmIdxs[fi] = i;
    //       fi += 1;
    //     }
    //   } else {
    //     if (ri >= this.rs.length) {
    //       // bool/int onto stack
    //       stackIdxs.add(i);
    //     } else {
    //       // bool/int into reg
    //       rIdxs[ri] = i;
    //       ri += 1;
    //     }
    //   }
    // }

    // // Welcome the arguments in order
    // for (var arg : e.arguments) {
    //   arg.welcome(this);
    //   if (arg.type.equals(Type.getFloatType())) {
    //     this.ex.write("  movq %xmm0, %rax\n");
    //     this.ex.write("  pushq %rax\n");
    //   } else {
    //     this.ex.write("  pushq %rax\n");
    //   }
    // }
    // // Move floats from stack to xmm registers
    // TODO: Check if indexInArguments is valid
    // for (int i = 0; i < xmmIdxs.length ; i++) {
    //   int indexInArguments = xmmIdxs[i];
    //   int rspOffset = (((e.arguments.length - indexInArguments) - 1) * 8) * -1;
    //   this.ex.write("    movsd " + rspOffset + "(%rsp),  " + this.frs[i] + "\n");
    // }

    // // Move primitives from stack to all purpose registers
    // TODO: Check if indexInArguments is valid
    // for (int i = 0; i < rIdxs.length ; i++) {
    //   int indexInArguments = rIdxs[i];
    //   int rspOffset = (((e.arguments.length - indexInArguments) - 1) * 8) * -1;
    //   this.ex.write("    movq " + rspOffset + "(%rsp),  " + this.rs[i] + "\n");
    // }
    // int stackStart = (e.arguments.length - 1) * 8 * -1;

    // Die ersten sechs params in die register schieben
    for (int i = 0; i < Math.min(this.rs.length, e.arguments.length); i++) {
      e.arguments[i].welcome(this);
      this.ex.write("    movq %rax,  " + this.rs[i] + "\n");
    }

    // Den Rest auf den stack pushen
    for (int i = e.arguments.length - 1; i >= this.rs.length; i--) {
      e.arguments[i].welcome(this);
      this.ex.write("  pushq %rax\n");
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
    this.ex.write(".globl " + mainName + "\n");
    this.ex.write(".type " + mainName + ", @function\n");
    this.ex.write(mainName + ":\n");
    this.ex.write("    pushq %rbp\n");
    this.ex.write("    movq %rsp, %rbp\n");
    e.expression.welcome(this);

    this.ex.write("    movq %rbp, %rsp\n");
    this.ex.write("    popq %rbp\n");
    this.ex.write("    ret\n");

    // PRINT FLOATS HERE
    this.ex.write("\n");
    this.ex.write(fw.getFloatSection());
    return null;
  }

  @Override
  public Void visit(Parameter e) {
    // The work for a paremeter node is implement
    // in the function definition visitor
    return null;
  }

}