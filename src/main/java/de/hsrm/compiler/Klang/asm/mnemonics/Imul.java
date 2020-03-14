package de.hsrm.compiler.Klang.asm.mnemonics;

public class Imul extends TwoOperandMnemonic {
  public String dataType = "q";

  public Imul(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "imul" + this.dataType + " " + this.src + ", " + this.dst;
  }

}