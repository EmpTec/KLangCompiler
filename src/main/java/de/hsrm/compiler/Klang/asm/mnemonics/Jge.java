package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jge extends Jump {
  public Jge(int label) {
    super("jge", label);
  }

  public Jge(String labelPrefix, int label) {
    super("jge", labelPrefix, label);
  }
}