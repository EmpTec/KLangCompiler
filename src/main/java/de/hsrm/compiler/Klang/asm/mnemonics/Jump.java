package de.hsrm.compiler.Klang.asm.mnemonics;

public abstract class Jump extends Mnemonic {
  protected String opcode;
  public String labelPrefix = "L";
  public int label;
  public Jump(String opcode, String labelPrefix, int label) {
    this.opcode = opcode;
    this.labelPrefix = labelPrefix;
    this.label = label;
  }

  public Jump(String opcode, int label) {
    this.opcode = opcode;
    this.label = label;
  }
  @Override
  public String toAsm() {
    return this.opcode + " ." + this.labelPrefix + this.label;
  }
}