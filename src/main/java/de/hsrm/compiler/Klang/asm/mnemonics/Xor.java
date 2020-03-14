package de.hsrm.compiler.Klang.asm.mnemonics;

public class Xor extends TwoOperandMnemonic {
  public String dataType = "q";

  public Xor(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  public Xor(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "xor" + this.dataType + " " + this.src + ", " + this.dst;
  }

}