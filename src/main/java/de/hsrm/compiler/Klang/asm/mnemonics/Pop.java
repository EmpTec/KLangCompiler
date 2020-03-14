package de.hsrm.compiler.Klang.asm.mnemonics;

public class Pop extends OneOperandMnemonic {
  public String dataType = "q";
  public Pop(String operand) {
    this.operand = operand;
  }

  public Pop(String dataType, String operand) {
    this.dataType = dataType;
    this.operand = operand;
  }

  @Override
  public String toAsm() {
    return "pop" + this.dataType + " " + this.operand;
  }

}