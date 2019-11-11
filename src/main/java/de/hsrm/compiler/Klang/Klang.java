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
    //EvalVisitor visitor = new EvalVisitor();
    StringWriter w = new StringWriter();
    PrettyPrintVisitor.ExWriter ex = new PrettyPrintVisitor.ExWriter(w);
    PrettyPrintVisitor visitor = new PrettyPrintVisitor(ex);
    node.welcome(visitor);
    System.out.println(w.toString());
  }
}
