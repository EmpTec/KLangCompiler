package de.hsrm.compiler.Klang.asm.mnemonics;

public class Div extends TwoOperandMnemonic {
  public String dataType;

  public Div(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "div" + this.dataType + " " + this.src + ", " + this.dst;
  }

}