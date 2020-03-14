package de.hsrm.compiler.Klang.asm.mnemonics;

public class Ret extends NoOperandMnemonic {
  @Override
  public String toAsm() {
    return "ret";
  }
}