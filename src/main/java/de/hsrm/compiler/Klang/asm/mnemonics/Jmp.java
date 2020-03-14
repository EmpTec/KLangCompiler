package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jmp extends Jump {
  public Jmp(String labelPrefix, int label) {
    super("jmp", labelPrefix, label);
  }

  public Jmp(int label) {
    super("jmp", label);
  }  
}