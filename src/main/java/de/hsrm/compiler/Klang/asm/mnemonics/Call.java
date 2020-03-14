package de.hsrm.compiler.Klang.asm.mnemonics;

public class Call extends OneOperandMnemonic {

  public Call(String operand) {
    this.operand = operand;
  }

  @Override
  public String toAsm() {
    return "call " + this.operand; 
  }

}