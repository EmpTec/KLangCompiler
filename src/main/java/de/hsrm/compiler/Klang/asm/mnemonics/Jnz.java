package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jnz extends Jump {
  public Jnz(int label) {
    super("jnz", label);
  }

  public Jnz(String labelPrefix, int label) {
    super("jnz", labelPrefix, label);
  }
}