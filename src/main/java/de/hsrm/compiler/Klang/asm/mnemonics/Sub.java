package de.hsrm.compiler.Klang.asm.mnemonics;

public class Sub extends TwoOperandMnemonic {
  public String dataType = "q";

  public Sub(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  public Sub(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "sub" + this.dataType + " " + this.src + ", " + this.dst;
  }

}