package de.hsrm.compiler.Klang.asm.mnemonics;

public class Neg extends OneOperandMnemonic {

  public Neg(String operand) {
    this.operand = operand;
  }

  @Override
  public String toAsm() {
    return "neg " + this.operand;
  }

}