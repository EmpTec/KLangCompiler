package de.hsrm.compiler.Klang.asm;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.compiler.Klang.asm.mnemonics.*;

public class ASM {
  private List<Mnemonic> mnemonics;

  public ASM() {
    this.mnemonics = new ArrayList<Mnemonic>();
  }

  public void push(String dataType, int immediate) {
    mnemonics.add(new Push(dataType, immediate));
  }

  public void push(String dataType, String operand) {
    mnemonics.add(new Push(dataType, operand));
  }

  public void pop(String dataType, String operand) {
    mnemonics.add(new Pop(dataType, operand));
  }

  public void mov(String dataType, String src, String dst) {
    mnemonics.add(new Mov(dataType, src, dst));
  }

  public void mov(String dataType, String label, String src, String dst) {
    mnemonics.add(new Mov(dataType, label, src, dst));
  }

  public void mov(String dataType, int offset, String src, String dst) {
    mnemonics.add(new Mov(dataType, offset, src, dst));
  }

  public void mov(String dataType, String src, int offset, String dst) {
    mnemonics.add(new Mov(dataType, src, offset, dst));
  }

  public void mov(String dataType, int immediate, String dst) {
    mnemonics.add(new Mov(dataType, immediate, dst));
  }

  public void ucomi(String dataType, String src, String dst) {
    mnemonics.add(new Ucomi(dataType, src, dst));
  }

  public void cmp(String dataType, String src, String dst) {
    mnemonics.add(new Cmp(dataType, src, dst));
  }

  public void cmp(String dataType, int immediate, String dst) {
    mnemonics.add(new Cmp(dataType, immediate, dst));
  }

  public void je(int label) {
    mnemonics.add(new Je(label));
  }

  public void je(String labelPrefix, int label) {
    mnemonics.add(new Je(labelPrefix, label));
  }

  public void jmp(int label) {
    mnemonics.add(new Jmp(label));
  }

  public void jmp(String labelPrefix, int label) {
    mnemonics.add(new Jmp(labelPrefix, label));
  }

  public void jne(int label) {
    mnemonics.add(new Jne(label));
  }

  public void jne(String labelPrefix, int label) {
    mnemonics.add(new Jne(labelPrefix, label));
  }

  public void jg(int label) {
    mnemonics.add(new Jg(label));
  }

  public void jg(String labelPrefix, int label) {
    mnemonics.add(new Jg(labelPrefix, label));
  }

  public void jge(int label) {
    mnemonics.add(new Jge(label));
  }

  public void jge(String labelPrefix, int label) {
    mnemonics.add(new Jge(labelPrefix, label));
  }

  public void jl(int label) {
    mnemonics.add(new Jl(label));
  }

  public void jl(String labelPrefix, int label) {
    mnemonics.add(new Jl(labelPrefix, label));
  }

  public void jle(int label) {
    mnemonics.add(new Jle(label));
  }

  public void jle(String labelPrefix, int label) {
    mnemonics.add(new Jle(labelPrefix, label));
  }

  public void jz(int label) {
    mnemonics.add(new Jz(label));
  }

  public void jz(String labelPrefix, int label) {
    mnemonics.add(new Jz(labelPrefix, label));
  }

  public void jnz(int label) {
    mnemonics.add(new Jnz(label));
  }

  public void jnz(String labelPrefix, int label) {
    mnemonics.add(new Jnz(labelPrefix, label));
  }

  public void label(int label) {
    mnemonics.add(new Label(label));
  }

  public void label(String labelPrefix, int label) {
    mnemonics.add(new Label(labelPrefix, label));
  }

  public void add(String dataType, String src, String dst) {
    mnemonics.add(new Add(dataType, src, dst));
  }

  public void add(String dataType, String src, int dstOffset, String dst) {
    mnemonics.add(new Add(dataType, src, dstOffset, dst));
  }

  public void add(String dataType, int immediate, String dst) {
    mnemonics.add(new Add(dataType, immediate, dst));
  }
  
  public void sub(String dataType, String src, String dst) {
    mnemonics.add(new Sub(dataType, src, dst));
  }
  
  public void mul(String dataType, String src, String dst) {
    mnemonics.add(new Mul(dataType, src, dst));
  }
  
  public void div(String dataType, String src, String dst) {
    mnemonics.add(new Div(dataType, src, dst));
  }

  public void idiv(String dataType, String operand) {
    mnemonics.add(new Idiv(dataType, operand));
  }
  
  public void imul(String dataType, String src, String dst) {
    mnemonics.add(new Imul(dataType, src, dst));
  }

  public void cqto() {
    mnemonics.add(new Cqto());
  }

  public void ret() {
    mnemonics.add(new Ret());
  }

  public void xor(String dataType, String src, String dst) {
    mnemonics.add(new Xor(dataType, src, dst));
  }

  public void neg(String operand) {
    mnemonics.add(new Neg(operand));
  }

  public void functionHead(String functionName) {
    mnemonics.add(new FunctionHead(functionName));
  }

  public void call(String operand) {
    mnemonics.add(new Call(operand));
  }
  
  public void text(String text) {
    mnemonics.add(new Text(text));
  }

  public void newline() {
    mnemonics.add(new Newline());
  }

  public void cvtsi2sd(String src, String dst) {
    mnemonics.add(new Cvtsi2sd(src, dst));
  }

  public String toAsm() {
    StringBuilder sb = new StringBuilder();
    mnemonics.stream().forEach(x -> {
      for (int i = 0; i < x.indentation; i++) {
        sb.append("\t");
      }
      sb.append(x.toAsm());
      sb.append("\n");
    });
    return sb.toString();
  }
}