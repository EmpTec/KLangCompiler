package de.hsrm.compiler.Klang.asm.mnemonics;

public class Label extends Mnemonic {
  public String labelPrefix = "L";
  public int label;

  public Label(int label) {
    this.label = label;
    this.indentation = 0;
  }
  
  public Label(String labelPrefix, int label) {
    this.labelPrefix = labelPrefix;
    this.label = label;
    this.indentation = 0;
  }

  @Override
  public String toAsm() {
    return "." + this.labelPrefix + this.label + ":";
  }
}
