package de.hsrm.compiler.Klang.asm.mnemonics;

public abstract class Mnemonic {
  public abstract String toAsm();
  public int indentation = 2;
}