package de.hsrm.compiler.Klang.asm.mnemonics;

public class Sub extends TwoOperandMnemonic {
  public String dataType;

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