package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jle extends Jump {
  public Jle(int label) {
    super("jle", label);
  }

  public Jle(String labelPrefix, int label) {
    super("jle", labelPrefix, label);
  }
}