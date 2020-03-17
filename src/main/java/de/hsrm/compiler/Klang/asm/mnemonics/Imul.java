package de.hsrm.compiler.Klang.asm.mnemonics;

public class Imul extends TwoOperandMnemonic {
  public String dataType;

  public Imul(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "imul" + this.dataType + " " + this.src + ", " + this.dst;
  }

}