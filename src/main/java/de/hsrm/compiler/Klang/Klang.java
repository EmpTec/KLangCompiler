package de.hsrm.compiler.Klang;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import de.hsrm.compiler.Klang.nodes.Node;
import de.hsrm.compiler.Klang.visitors.*;

public class Klang {

  public static void main(String[] args) throws Exception {
    boolean evaluate = false;
    boolean prettyPrint = false;
    boolean compile = true;
    String mainName = "main";

    List<String> arguments = Arrays.asList(args);
    if (arguments.contains("-h") || arguments.contains("--help") || arguments.contains("?")) {
      System.out.println("\nKaiser Lang Compiler");
      System.out.println("Authors: Dennis Kaiser and Marvin Kaiser");
      System.out.println("");
      System.out.println("Pass source code via stdin");
      System.out.println("");
      System.out.println("Arguments:");
      System.out.println("--evaluate:\t Evaluates the given source code");
      System.out.println("--pretty:\t Pretty print the given source code");
      System.out.println("--no-compile:\t Do not compile the source code");
      System.out.println("--no-main:\t Do not generate main function, will be generated as 'start'. Useful for testing");
      return;
    }
    if (arguments.contains("--evaluate")) {
      evaluate = true;
    }
    if (arguments.contains("--pretty")) {
      prettyPrint = true;
    }
    if (arguments.contains("--no-compile")) {
      compile = false;
    }
    if (arguments.contains("--no-main")) {
      mainName = "start";
    }

    // create a CharStream that reads from standard input
    CharStream input = CharStreams.fromStream(System.in);

    // create a lexer that feeds off of input CharStream
    KlangLexer lexer = new KlangLexer(input);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    KlangParser parser = new KlangParser(tokens);

    ParseTree tree = parser.parse(); // begin parsing at init rule
    ContextAnalysis ctxAnal = new ContextAnalysis();
    Node node = ctxAnal.visit(tree); // this gets us the DAST

    if (prettyPrint) {
      // Pretty Print the sourcecode
      StringWriter w = new StringWriter();
      PrettyPrintVisitor.ExWriter ex = new PrettyPrintVisitor.ExWriter(w);
      PrettyPrintVisitor printVisitor = new PrettyPrintVisitor(ex);
      node.welcome(printVisitor);
      System.out.println(w.toString());
    }

    if (compile) {
      // Generate assembler code
      // System.out.println("\nPrinting the assembler code");
      StringWriter wAsm = new StringWriter();
      GenASM.ExWriter exAsm = new GenASM.ExWriter(wAsm);
      GenASM genasm = new GenASM(exAsm, mainName);
      node.welcome(genasm);
      System.out.println(wAsm.toString());
    }

    if (evaluate) {
      // Evaluate the sourcecode and print the result
      System.out.println("\nEvaluating the source code:");
      EvalVisitor evalVisitor = new EvalVisitor();
      Value result = node.welcome(evalVisitor);
      if (result != null) {
        System.out.println("result: " + result.asInteger());
      } else {
        System.out.println("result was null");
      }
    }
  }
}
