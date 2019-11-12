package de.hsrm.compiler.Klang;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import de.hsrm.compiler.Klang.nodes.Node;
import de.hsrm.compiler.Klang.visitors.*;

public class Klang {
  public static void main(String[] args) throws Exception {
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

    // Pretty Print the sourcecode
    System.out.println("\nPrinting the sourcecode:");
    StringWriter w = new StringWriter();
    PrettyPrintVisitor.ExWriter ex = new PrettyPrintVisitor.ExWriter(w);
    PrettyPrintVisitor printVisitor = new PrettyPrintVisitor(ex);
    node.welcome(printVisitor);
    System.out.println(w.toString());

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
