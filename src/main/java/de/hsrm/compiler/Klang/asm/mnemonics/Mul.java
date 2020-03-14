package de.hsrm.compiler.Klang.asm.mnemonics;

public class Mul extends TwoOperandMnemonic {
  public String dataType = "q";

  public Mul(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  public Mul(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "mul" + this.dataType + " " + this.src + ", " + this.dst;
  }

}