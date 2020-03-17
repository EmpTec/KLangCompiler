package de.hsrm.compiler.Klang;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

import de.hsrm.compiler.Klang.nodes.Node;
import de.hsrm.compiler.Klang.nodes.StructDefinition;
import de.hsrm.compiler.Klang.visitors.*;
import de.hsrm.compiler.Klang.helper.*;

public class Klang {

  private static void generateOutput(String file, String output) {
    if (file != null) {
      try {
        var outFile = new FileWriter(file);
        outFile.write(output);
        outFile.close();
      } catch (Exception e) {
        System.err.println("Cannot write output to file");
        e.printStackTrace();
      }
      return;
    }
    System.out.println(output);
  }

  public static void main(String[] args) throws Exception {
    boolean evaluate = false;
    boolean prettyPrint = false;
    String mainName = "main";
    String out = null;

    List<String> arguments = Arrays.asList(args);
    if (arguments.size() <= 0 || arguments.contains("-h") || arguments.contains("--help") || arguments.contains("?")) {
      System.out.println("\nKaiser Lang Compiler");
      System.out.println("Authors: Dennis Kaiser and Marvin Kaiser");
      System.out.println("");
      System.out.println("Last argument must be file");
      System.out.println("");
      System.out.println("Arguments:");
      System.out.println("--o <file>:\t File to write to");
      System.out.println("--evaluate:\t Evaluates the given source code");
      System.out.println("--pretty:\t Pretty print the given source code");
      System.out
          .println("--no-main:\t Do not generate main function, will be generated as 'start'. Useful for testing");
      return;
    }
    if (arguments.contains("--evaluate")) {
      evaluate = true;
    }
    if (arguments.contains("--pretty")) {
      prettyPrint = true;
    }
    if (arguments.contains("--no-main")) {
      mainName = "start";
    }
    if (arguments.contains("-o")) {
      if (arguments.size() <= 1) {
        System.out.println("Must specify file name with -o");
        return;
      }
      out = arguments.get(arguments.indexOf("-o") + 1);
    }

    // create a CharStream that reads from standard input
    CharStream input = CharStreams.fromFileName(arguments.get(arguments.size() - 1));

    // create a lexer that feeds off of input CharStream
    KlangLexer lexer = new KlangLexer(input);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    KlangParser parser = new KlangParser(tokens);

    // Parse tokens to AST
    ParseTree tree = parser.parse(); // begin parsing at init rule

    // Context Analysis and DAST generation
    Node root;
    HashMap<String, StructDefinition> structs;
    try {
      // Extract information about all functions
      var functionDefinitions = new HashMap<String, FunctionInformation>();
      new GetFunctions(functionDefinitions).visit(tree);

      // Extract names of all structs
      var structNames = new HashSet<String>();
      new GetStructNames(structNames).visit(tree);

      // Extract information about all structs
      structs = new HashMap<String, StructDefinition>();
      new GetStructs(structNames, structs).visit(tree);

      // Create the DAST
      ContextAnalysis ctxAnal = new ContextAnalysis(functionDefinitions, structs);
      root = ctxAnal.visit(tree);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return;
    }

    if (prettyPrint) {
      // Pretty Print the sourcecode
      StringWriter w = new StringWriter();
      PrettyPrintVisitor.ExWriter ex = new PrettyPrintVisitor.ExWriter(w);
      PrettyPrintVisitor printVisitor = new PrettyPrintVisitor(ex);
      root.welcome(printVisitor);
      generateOutput(out, w.toString());
      return;
    }

    if (evaluate) {
      // Evaluate the sourcecode and print the result
      System.out.println("\nEvaluating the source code:");
      EvalVisitor evalVisitor = new EvalVisitor(structs);
      Value result = root.welcome(evalVisitor);
      generateOutput(out, "Result was: " + result.asObject().toString());
      return;
    }

    // Generate assembler code
    GenASM genasm = new GenASM(mainName, structs);
    root.welcome(genasm);
    generateOutput(out, genasm.toAsm());
  }
}
