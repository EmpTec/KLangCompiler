package de.hsrm.compiler.Klang.asm.mnemonics;

public class Add extends TwoOperandMnemonic {
  public String dataType = "q";

  public Add(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  public Add(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  public Add(String dataType, String src, int dstOffset, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dstOffset + "(" + dst + ")";
  }

  public Add(String dataType, int immediate, String dst) {
    this.dataType = dataType;
    this.src = "$" + immediate;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "add" + this.dataType + " " + this.src + ", " + this.dst;
  }

}