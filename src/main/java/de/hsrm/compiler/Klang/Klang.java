package de.hsrm.compiler.Klang;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Klang {
  public static void main(String[] args) throws Exception {
    // create a CharStream that reads from standard input
    ANTLRInputStream input = new ANTLRInputStream(System.in);

    // create a lexer that feeds off of input CharStream
    KlangLexer lexer = new KlangLexer(input);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    KlangParser parser = new KlangParser(tokens);

    ParseTree tree = parser.parse(); // begin parsing at init rule
    Visitor visitor = new Visitor();
    int result = visitor.visit(tree);
    System.out.println(result);
  }
}
