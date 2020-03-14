package de.hsrm.compiler.Klang.asm.mnemonics;

public abstract class Mnemonic {
  public String dataType = "";
  public abstract String toAsm();
  public int indentation = 2;
}