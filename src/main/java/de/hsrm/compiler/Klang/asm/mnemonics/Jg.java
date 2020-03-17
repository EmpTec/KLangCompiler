package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jg extends Jump {
  public Jg(int label) {
    super("jg", label);
  }

  public Jg(String labelPrefix, int label) {
    super("jg", labelPrefix, label);
  }
}