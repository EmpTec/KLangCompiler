package de.hsrm.compiler.Klang.asm.mnemonics;

public class Push extends OneOperandMnemonic {
  public String dataType;

  public Push(String dataType, int immediate){
    this.dataType = dataType;
    this.operand = "$" + immediate;
  }

  public Push(String dataType, String operand) {
    this.dataType = dataType;
    this.operand = operand;
  }

  @Override
  public String toAsm() {
    return "push" + this.dataType + " " + this.operand;
  }

}