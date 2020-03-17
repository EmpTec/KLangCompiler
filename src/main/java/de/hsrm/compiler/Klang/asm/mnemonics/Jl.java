package de.hsrm.compiler.Klang.asm.mnemonics;

public class Jl extends Jump {
  public Jl(int label) {
    super("jl", label);
  }

  public Jl(String labelPrefix, int label) {
    super("jl", labelPrefix, label);
  }
}