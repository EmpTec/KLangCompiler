package de.hsrm.compiler.Klang.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import de.hsrm.compiler.Klang.asm.ASM;
import de.hsrm.compiler.Klang.helper.Helper;
import de.hsrm.compiler.Klang.nodes.*;
import de.hsrm.compiler.Klang.nodes.expressions.*;
import de.hsrm.compiler.Klang.nodes.loops.DoWhileLoop;
import de.hsrm.compiler.Klang.nodes.loops.ForLoop;
import de.hsrm.compiler.Klang.nodes.loops.WhileLoop;
import de.hsrm.compiler.Klang.nodes.statements.*;
import de.hsrm.compiler.Klang.types.Type;

public class GenASM implements Visitor<Void> {
  private class FloatWriter {
    private StringBuilder sb = new StringBuilder();
    private int id = -1;

    public String getFloat(double d) {
      Long longBits = Double.doubleToRawLongBits(d);
      String binary = Long.toBinaryString(longBits);
      int padCount = 64 - binary.length();
      while (padCount > 0) {
        binary = "0" + binary;
        padCount--;
      }
      String upper = binary.substring(0, 32);
      String lower = binary.substring(32, 64);
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

  private ASM asm;
  private FloatWriter fw = new FloatWriter();
  Map<String, Integer> env = new HashMap<>();
  Map<String, StructDefinition> structs;
  Set<String> vars;
  String[] registers = { "%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9" };
  String[] floatRegisters = { "%xmm0", "%xmm1", "%xmm2", "%xmm3", "%xmm4", "%xmm5", "%xmm6", "%xmm7" };
  private int lCount = 0; // Invariant: lCount is used
  private int currentFunctionStartLabel = 0;
  private Parameter[] currentFunctionParams;

  private boolean prepareRegisters(Expression lhs, Expression rhs) {
    boolean lhsIsFloat = lhs.type.equals(Type.getFloatType());
    boolean rhsIsFloat = rhs.type.equals(Type.getFloatType());
    if (lhsIsFloat && rhsIsFloat) {
      lhs.welcome(this);
      asm.mov("sd", "%xmm0", "%xmm2");
      rhs.welcome(this);
      asm.mov("sd", "%xmm2", "%xmm0");
      asm.mov("sd", "%xmm2", "%xmm0");
      return true;
    } else if (lhsIsFloat && !rhsIsFloat) {
      lhs.welcome(this);
      rhs.welcome(this);
      asm.cvtsi2sd("%rax", "%xmm1");
      return true;
    } else if (!lhsIsFloat && rhsIsFloat) {
      lhs.welcome(this);
      asm.cvtsi2sd("%rax", "%xmm2");
      rhs.welcome(this);
      asm.mov("sd", "%xmm0", "%xmm1");
      asm.mov("sd", "%xmm2", "%xmm0");
      return true;
    } else {
      lhs.welcome(this);
      asm.push("q", "%rax");
      rhs.welcome(this);
      asm.mov("q", "%rax", "%rbx");
      asm.pop("q", "%rax");
      return false;
    }
  }

  public GenASM(Map<String, StructDefinition> structs) {
    this.structs = structs;
    this.asm = new ASM();
  }

  public String toAsm() {
    return asm.toAsm();
  }

  @Override
  public Void visit(IntegerExpression e) {
    asm.mov("q", e.value, "%rax");
    return null;
  }

  @Override
  public Void visit(FloatExpression e) {
    String floatLabel = fw.getFloat(e.value);
    asm.mov("sd", floatLabel, "%rip", "%xmm0");
    return null;
  }

  @Override
  public Void visit(BooleanExpression e) {
    asm.mov("q", e.value ? 1 : 0, "%rax");
    return null;
  }

  @Override
  public Void visit(Variable e) {
    if (e.type.equals(Type.getFloatType())) {
      asm.mov("sd", this.env.get(e.name), "%rbp", "%xmm0");
    } else {
      asm.mov("q", this.env.get(e.name), "%rbp", "%rax");
    }
    return null;
  }

  @Override
  public Void visit(EqualityExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm1", "xmm0");
    } else {
      asm.cmp("q", "%rbx", "%rax");
    }
    asm.je(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(NotEqualityExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm0", "%xmm1");
    } else {
      asm.cmp("q", "%rax", "%rbx");
    }
    asm.jne(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(GTExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm1", "%xmm0");
    } else {
      asm.cmp("q", "%rbx", "%rax");
    }
    asm.jg(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(GTEExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm1", "xmm0");
    } else {
      asm.cmp("q", "%rbx", "%rax");
    }
    asm.jge(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(LTExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm1", "%xmm0");
    } else {
      asm.cmp("q", "%rbx", "%rax");
    }
    asm.jl(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(LTEExpression e) {
    int lblTrue = ++lCount;
    int lblEnd = ++lCount;

    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.ucomi("sd", "%xmm1", "%xmm0");
    } else {
      asm.cmp("q", "%rbx", "%rax");
    }
    asm.jle(lblTrue);
    // false
    asm.mov("q", 0, "%rax");
    asm.jmp(lblEnd);
    asm.label(lblTrue);
    // true
    asm.mov("q", 1, "%rax");
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(AdditionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.add("sd", "%xmm1", "%xmm0");
    } else {
      asm.add("q", "%rbx", "%rax");
    }
    return null;
  }

  @Override
  public Void visit(SubstractionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.sub("sd", "%xmm1", "%xmm0");
    } else {
      asm.sub("q", "%rbx", "%rax");
    }
    return null;
  }

  @Override
  public Void visit(MultiplicationExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.mul("sd", "%xmm1", "%xmm0");
    } else {
      asm.imul("q", "%rbx", "%rax");
    }
    return null;
  }

  @Override
  public Void visit(DivisionExpression e) {
    boolean isFloatOperation = this.prepareRegisters(e.lhs, e.rhs);
    if (isFloatOperation) {
      asm.div("sd", "%xmm1", "%xmm0");
    } else {
      asm.cqto();
      asm.idiv("q", "%rbx");
    }
    return null;
  }

  @Override
  public Void visit(ModuloExpression e) {
    e.lhs.welcome(this);
    asm.push("q", "%rax");
    e.rhs.welcome(this);
    asm.mov("q", "%rax", "%rbx");
    asm.pop("q", "%rax");
    asm.cqto();
    asm.idiv("q", "%rbx");
    asm.mov("q", "%rdx", "%rax");
    return null;
  }

  @Override
  public Void visit(NegateExpression e) {
    e.lhs.welcome(this);
    if (e.lhs.type.equals(Type.getFloatType())) {
      String floatLabel = fw.getNegateFloat();
      asm.mov("sd", floatLabel, "%rip", "%xmm1");
      asm.xor("pd", "%xmm1", "%xmm0");
    } else {
      asm.neg("%rax");
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
    asm.cmp("q", 0, "%rax");
    asm.jne(lblTrue);

    // LHS war false, also werte RHS aus
    // Wenn RHS == 0 bedeutet das false,
    // also ist das Gesamtergebnis false
    e.rhs.welcome(this);
    asm.cmp("q", 0, "%rax");
    asm.je(lblFalse);

    // Die Expression wertet zu true aus
    // Springe am false Teil vorbei
    asm.label(lblTrue);
    asm.mov("q", 1, "%rax");
    asm.jmp(lblEnd);

    // Die Expressoin wertet zu false aus
    asm.label(lblFalse);
    asm.mov("q", 0, "%rax");

    // Das hier ist das ende
    asm.label(lblEnd);
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
    asm.cmp("q", 0, "%rax");
    asm.je(lblFalse);

    // LHS war true, also werte RHS aus.
    // Wenn RHS == 0, bedeutet das false
    // also ist das Gesamtergebnis false
    e.rhs.welcome(this);
    asm.cmp("q", 0, "%rax");
    asm.je(lblFalse);

    // Die Expression wertet zu true aus
    // Springe am false Teil vorbei
    asm.label(lblTrue);
    asm.mov("q", 1, "%rax");
    asm.jmp(lblEnd);

    // Die Expressoin wertet zu false aus
    asm.label(lblFalse);
    asm.mov("q", 0, "%rax");

    // Das hier ist das ende
    asm.label(lblEnd);
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
    asm.cmp("q", 0, "%rax");
    asm.jne(lblFalse);

    // Hier ist das Ergebnis true
    // Springe am false Teil vorbei
    asm.mov("q", 1, "%rax");
    asm.jmp(lblEnd);

    // Hier ist das Ergebnis false
    // Falle zum Ende durch
    asm.label(lblFalse);
    asm.mov("q", 0, "%rax");

    // Hier ist das Ende
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(IfStatement e) {
    int lblElse = ++lCount;
    int lblEnd = ++lCount;
    boolean hasElse = e.alt != null || e.elif != null;
    e.cond.welcome(this);
    asm.cmp("", 0, "%rax");
    // in case of cond evaluating to false, jump to else/elif
    // Jump to end if there is no else part, this saves a label declaration
    if (hasElse) {
      asm.jz(lblElse);
    } else {
      asm.jz(lblEnd);
    }
    e.then.welcome(this);
    if (hasElse) {
      asm.jmp(lblEnd);
      asm.label(lblElse);
      if (e.alt != null) {
        e.alt.welcome(this);
      } else {
        e.elif.welcome(this);
      }
    }
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(WhileLoop e) {
    int lblCond = ++lCount;
    int lblEnd = ++lCount;
    asm.label(lblCond);
    e.cond.welcome(this);
    asm.cmp("", 0, "%rax");
    asm.jz(lblEnd);
    e.block.welcome(this);
    asm.jmp(lblCond);
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(DoWhileLoop e) {
    int lblStart = ++lCount;
    asm.label(lblStart);
    e.block.welcome(this);
    e.cond.welcome(this);
    asm.cmp("", 0, "%rax");
    asm.jnz(lblStart);
    return null;
  }

  @Override
  public Void visit(ForLoop e) {
    int lblStart = ++lCount;
    int lblEnd = ++lCount;
    e.init.welcome(this);
    asm.label(lblStart);
    e.condition.welcome(this);
    asm.cmp("", 0, "%rax");
    asm.jz(lblEnd);
    e.block.welcome(this);
    e.step.welcome(this);
    asm.jmp(lblStart);
    asm.label(lblEnd);
    return null;
  }

  @Override
  public Void visit(VariableDeclaration e) {
    // If there is an initialization present,
    // push it to the location of the local var
    if (e.expression != null) {
      e.expression.welcome(this);
      int offset = this.env.get(e.name);

      if (e.expression.type.equals(Type.getFloatType())) {
        asm.mov("q", "%xmm0", "%rax");
      }

      asm.mov("q", "%rax", offset, "%rbp");
    }
    return null;
  }

  @Override
  public Void visit(VariableAssignment e) {
    e.expression.welcome(this);
    int offset = this.env.get(e.name);

    // Determine where the result of this expression was placed into
    // and move it onto the stack from there
    if (e.expression.type.equals(Type.getFloatType())) {
      asm.mov("q", "%xmm0", offset, "%rbp");
    } else {
      asm.mov("q", "%rax", offset, "%rbp");
    }

    return null;
  }

  @Override
  public Void visit(ReturnStatement e) {
    e.expression.welcome(this);
    asm.mov("q", "%rbp", "%rsp");
    asm.pop("q", "%rbp");
    asm.ret();
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
    int lblStart = ++lCount;
    this.currentFunctionStartLabel = lblStart;
    this.currentFunctionParams = e.parameters;
    asm.functionHead(e.name);
    asm.push("q", "%rbp");
    asm.mov("q", "%rsp", "%rbp");
    asm.label(lblStart);

    // hole die anzahl der lokalen variablen
    this.vars = new TreeSet<String>();
    HashMap<String, Type> types = new HashMap<String, Type>();
    GetVars getvars = new GetVars(vars, types);
    getvars.visit(e);

    // Erzeuge ein environment
    this.env = new HashMap<String, Integer>();

    // Merke dir die offsets der parameter, die direkt auf den stack gelegt wurden
    int offset = 16; // Per Stack übergebene Parameter liegen über unserem BSP
    int ri = 0;
    int fi = 0;
    // Per stack übergebene variablen in env registrieren
    var registerParameters = new ArrayList<Parameter>();
    for (int i = 0; i < e.parameters.length; i++) {
      if (e.parameters[i].type.equals(Type.getFloatType())) {
        if (fi >= this.floatRegisters.length) {
          // parameter is on stack already
          env.put(e.parameters[i].name, offset);
          offset += 8;
        } else {
          // parameter is in a xmm register
          registerParameters.add(e.parameters[i]);
          fi++;
        }
      } else {
        if (ri >= this.registers.length) {
          // parameter is on stack already
          env.put(e.parameters[i].name, offset);
          offset += 8;
        } else {
          // parameter is in a register
          registerParameters.add(e.parameters[i]);
          ri++;
        }
      }
    }

    offset = 0;
    ri = 0;
    fi = 0;
    for (var param: registerParameters) {
      if (param.type.equals(Type.getFloatType())) {
        asm.mov("q", this.floatRegisters[fi], "%rax");
        asm.push("q", "%rax");
        offset -= 8;
        this.env.put(param.name, offset); // negative, liegt unter aktuellem BP
        fi++;
      } else {
        asm.push("q", this.registers[ri]);
        offset -= 8;
        this.env.put(param.name, offset); // negative, liegt unter aktuellem BP
        ri++;
      }
    }

    // Reserviere Platz auf dem Stack für jede lokale variable
    for (String lok_var : vars) {
      offset -= 8;
      asm.push("q", 0);
      this.env.put(lok_var, offset);
    }

    e.block.welcome(this);
    return null;
  }

  @Override
  public Void visit(FunctionCall e) {
    if (e.isTailRecursive) {

      // Visit the arguments and move them into the stack
      for(int i = 0; i < e.arguments.length; i++) {
        e.arguments[i].welcome(this);

        if (e.arguments[i].type.equals(Type.getFloatType())) {
          asm.mov("q", "%xmm0", "%rax0");
        }

        asm.push("q", "%rax");
      }

      // push args into local var locations, last arg is ontop of the stack
      for (int i = e.arguments.length - 1; i >= 0; i--) {
        asm.pop("q", this.env.get(this.currentFunctionParams[i].name) + "(%rbp)");
      }

      asm.jmp(this.currentFunctionStartLabel);
      return null;
    }


    if (e.arguments.length > 0) {
      // Mapping arguments index -> xmm registers index
      int[] xmmIdxs = new int[this.floatRegisters.length];
      int fi = -1;

      // Mapping arguments index -> all purpose registers index
      int[] rIdxs = new int[this.registers.length];
      int ri = -1;

      // Mapping arguments index -> stack
      ArrayList<Integer> stackIdxs = new ArrayList<Integer>();

      // Go through arguments
      // sort them into the memory regions they go when being passed to functions
      for (int i = 0; i < e.arguments.length; i++) {
        var arg = e.arguments[i];
        if (arg.type.equals(Type.getFloatType())) {
          if (fi < this.floatRegisters.length - 1) {
            // Float into float reg
            fi += 1;
            xmmIdxs[fi] = i;
          } else {
            // Float onto stack
            stackIdxs.add(i);
          }
        } else {
          if (ri < this.registers.length - 1) {
            // bool/int into reg
            ri += 1;
            rIdxs[ri] = i;
          } else {
            // bool/int onto stack
            stackIdxs.add(i);
          }
        }
      }

      // Welcome the arguments in order, push everything onto the stack
      for (var arg : e.arguments) {
        arg.welcome(this);
        if (arg.type.equals(Type.getFloatType())) {
          asm.mov("q", "%xmm0", "%rax");
          asm.push("q", "%rax");
        } else {
          asm.push("q", "%rax");
        }
      }

      // Move floats from stack to xmm registers
      for (int i = 0; i <= fi; i++) {
        int indexInArguments = xmmIdxs[i];
        int rspOffset = (((e.arguments.length - indexInArguments) - 1) * 8);
        asm.mov("sd", rspOffset, "%rsp", this.floatRegisters[i]);
      }

      // Move primitives from stack to all purpose registers
      for (int i = 0; i <= ri; i++) {
        int indexInArguments = rIdxs[i];
        int rspOffset = (((e.arguments.length - indexInArguments) - 1) * 8);
        asm.mov("q", rspOffset, "%rsp", this.registers[i]);
      }

      // Move everything else from a higher stack position to our stack frame start
      int stackStartOffset = ((e.arguments.length) * 8);
      for (int i = stackIdxs.size() - 1; i >= 0; i--) {
        stackStartOffset -= 8;
        int indexInArguments = stackIdxs.get(i);
        int rspOffset = (((e.arguments.length - indexInArguments) - 1) * 8);
        asm.mov("q", rspOffset, "%rsp", "%rax");
        asm.mov("q", "%rax", stackStartOffset, "%rsp");
      }

      // Rescue RSP
      asm.add("q", stackStartOffset, "%rsp");
    }

    asm.call(e.name);
    return null;
  }

  @Override
  public Void visit(Program e) {
    asm.text(".text");
    for (var func : e.funcs) {
      func.welcome(this);
      asm.newline();
    }

    asm.text(fw.getFloatSection());
    return null;
  }

  @Override
  public Void visit(Parameter e) {
    // The work for a paremeter node is implement
    // in the function definition visitor
    return null;
  }

  @Override
  public Void visit(StructDefinition e) {
    // We get these from a previous visitor
    return null;
  }

  @Override
  public Void visit(StructField e) {
    // Nothing to do here...
    return null;
  }

  @Override
  public Void visit(StructFieldAccessExpression e) {
    var structDef = this.structs.get(e.structName);
    int offset = this.env.get(e.varName);

    // move struct address into rax
    asm.mov("q", offset, "%rbp", "%rax");

    // "follow" the first path element by moving the referenced value into rax
    asm.mov("q", Helper.getFieldOffset(structDef, e.path[0]), "%rax", "%rax");
    for  (int i = 1; i < e.path.length; i++) {
      // "follow" the current path element
      structDef = this.structs.get(structDef.fields[Helper.getFieldIndex(structDef, e.path[i - 1])].type.getName());
      asm.mov("q", Helper.getFieldOffset(structDef, e.path[i]), "%rax", "%rax");
    }

    // desired value now in rax

    // push rax to xmm0 if the result type is a float
    if (e.type.equals(Type.getFloatType())) {
      asm.mov("q", "%rax", "%xmm0");
    }

    return null;
  }

  @Override
  public Void visit(ConstructorCall e) {
    // push arguments onto the stack
    for (var arg: e.args) {
      arg.welcome(this);

      // move float values from xmm0 to rax first
      if (arg.type.equals(Type.getFloatType())) {
        asm.mov("q", "%xmm0", "%rax");
      }

      asm.push("q", "%rax");
    }

    // allocate heap memory by calling malloc
    var structDef = this.structs.get(e.structName);
    asm.mov("l", Helper.getFieldSizeBytes(structDef), "%edi");
    asm.call("malloc@PLT");

    // push args into struct memory, last arg is ontop of the stack
    for (int i = e.args.length - 1; i >= 0; i--) {
      asm.pop("q", Helper.getFieldOffset(structDef, i) + "(%rax)");
    }

    return null;
  }

  @Override
  public Void visit(NullExpression e) {
    asm.mov("q", 0 , "%rax");
    return null;
  }

  @Override
  public Void visit(DestructorCall e) {
    asm.mov("q", this.env.get(e.name), "%rbp", "%rdi");
    asm.call("free@PLT");
    return null;
  }

  @Override
  public Void visit(FieldAssignment e) {
    var structDef = this.structs.get(e.structName);
    int offset = this.env.get(e.varName);
    String fieldNameToUpdate = e.path[e.path.length - 1];

    e.expression.welcome(this);

    // Move it from xmm0 rax if its a flaot
    if (e.expression.type.equals(Type.getFloatType())) {
      asm.mov("q", "%xmm0", "%rax");
    }

    // Push the expression onto the stack
    asm.push("q", "%rax");

    // move struct address into rax
    asm.mov("q", offset, "%rbp", "%rax");

    // If there are at least two elements in the path,
    // move the address of the next referenced struct into rax
    for  (int i = 1; i < e.path.length - 1; i++) {
      structDef = this.structs.get(structDef.fields[Helper.getFieldIndex(structDef, e.path[i - 1])].type.getName());
      asm.mov("q",  Helper.getFieldOffset(structDef, e.path[i]), "%rax", "%rax");
    }

    // pop the expression that is ontop of the stack into the field of the struct that has to be updated
    asm.pop("q", Helper.getFieldOffset(structDef, fieldNameToUpdate) + "(%rax)");
    asm.mov("q", 0 , "%rax"); // clear rax since an assignment has no result

    return null;
  }

}