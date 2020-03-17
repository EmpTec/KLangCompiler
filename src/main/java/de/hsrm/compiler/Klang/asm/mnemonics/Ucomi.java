package de.hsrm.compiler.Klang.asm.mnemonics;

public class Ucomi extends TwoOperandMnemonic {
  public String dataType;

  public Ucomi(String dataType, String src, String dst) {
    this.dataType = dataType;
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "ucomi" + this.dataType + " " + this.src + ", " + this.dst;
  }

}
