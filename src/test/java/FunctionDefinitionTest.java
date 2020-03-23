import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class FunctionDefinitionTest {

    @Test
    void typeNotDefined() {
        ParseTree tree = Helper.prepareParser("function foo(): schwurbel { return 1; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:0 Type schwurbel not defined.", e.getMessage());
    }

    @Test
    void noReturnExpression() {
        ParseTree tree = Helper.prepareParser("function foo(): int { let x: int; x = 0; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:0 Function foo has to return something of type int.", e.getMessage());
    }
}