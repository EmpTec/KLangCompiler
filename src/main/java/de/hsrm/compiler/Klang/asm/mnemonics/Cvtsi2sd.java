package de.hsrm.compiler.Klang.asm.mnemonics;

public class Cvtsi2sd extends TwoOperandMnemonic {

  public Cvtsi2sd(String src, String dst) {
    this.src = src;
    this.dst = dst;
  }

  @Override
  public String toAsm() {
    return "cvtsi2sd " + this.src + ", " + this.dst;
  }

}