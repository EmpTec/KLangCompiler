package de.hsrm.compiler.Klang.asm.mnemonics;

public class Text extends NoOperandMnemonic {
  public String text;

  public Text(String text) {
    this.text = text;
    this.indentation = 0;
  }

  @Override
  public String toAsm() {
    return this.text;
  }  
}