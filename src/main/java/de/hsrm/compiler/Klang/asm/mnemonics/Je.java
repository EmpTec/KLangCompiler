package de.hsrm.compiler.Klang.asm.mnemonics;

public class Je extends Jump {
  
  public Je(int label) {
    super("je", label);
  }

  public Je(String labelPrefix, int label) {
    super("je", labelPrefix, label);
  }
}