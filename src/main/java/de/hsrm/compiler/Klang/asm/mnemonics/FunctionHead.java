package de.hsrm.compiler.Klang.asm.mnemonics;

public class FunctionHead extends NoOperandMnemonic {

  public String functionName;

  public FunctionHead(String functionName) {
    this.functionName = functionName;
    this.indentation = 0;
  }

  @Override
  public String toAsm() {
    StringBuilder sb = new StringBuilder();
    sb.append(".globl ");
    sb.append(this.functionName);
    sb.append("\n");
    sb.append(".type ");
    sb.append(this.functionName);
    sb.append(", @function\n");
    sb.append(this.functionName);
    sb.append(":");
    return sb.toString();
  }

}