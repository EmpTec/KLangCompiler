package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jz extends Jump {
  public Jz(int label) {
    super("jz", label);
  }

  public Jz(String labelPrefix, int label) {
    super("jz", labelPrefix, label);
  }
}