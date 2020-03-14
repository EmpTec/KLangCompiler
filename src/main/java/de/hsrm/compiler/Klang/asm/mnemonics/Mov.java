package de.hsrm.compiler.Klang.asm.mnemonics;

public class Mov extends TwoOperandMnemonic{
  public String dataType = "q";

  public Mov(int immediate, String dst) {
    this.src = "$" + immediate;
    this.dst = dst;
  }

  public Mov(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  public Mov(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  public Mov(String dataType, String label, String src, String dst) {
    this.dataType = dataType;
    this.src = label + "(" + src + ")";
    this.dst = dst;
  }

  public Mov(String dataType, int offset, String src, String dst) {
    this.dataType = dataType;
    this.src = offset + "(" + src + ")";
    this.dst = dst;
  }

  public Mov(String dataType, int immediate, String dst) {
    this.dataType = dataType;
    this.src = "$" + immediate;
    this.dst = dst;
  }

  public Mov(String dataType, String src, int offset, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = offset + "(" + dst + ")";
  }

  @Override
  public String toAsm() {
    return "mov" + this.dataType + " " + this.src + ", " + this.dst;
  }
}