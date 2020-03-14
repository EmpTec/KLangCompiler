package de.hsrm.compiler.Klang.asm.mnemonics;

public class Idiv extends OneOperandMnemonic{
  public String dataType = "";

  public Idiv(String operand) {
    this.operand = operand;
  }

  public Idiv(String dataType, String operand) {
    this.dataType = dataType;
    this.operand = operand;
  }

  @Override
  public String toAsm() {
    return "idiv" + this.dataType + " " + this.operand;
  }

}