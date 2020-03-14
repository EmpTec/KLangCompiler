package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jne extends Jump {
  public Jne(int label) {
    super("jne", label);
  }

  public Jne(String labelPrefix, int label) {
    super("jne", labelPrefix, label);
  } 
}