package de.hsrm.compiler.Klang.asm.mnemonics;

public class Cmp extends TwoOperandMnemonic {

  public Cmp(String src, String dst) {
    this.dataType = "";
    this.src = src;
    this.dst = dst;
  }

  public Cmp(String dataType, int immediate, String dst) {
    this.dataType = dataType;
    this.src = "$" + immediate;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "cmp" + this.dataType + " " + this.src + ", " + this.dst;
  }
}