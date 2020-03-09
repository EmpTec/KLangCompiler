import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class VariableTest {

    @Test
    void variableNotDefined() {
        ParseTree tree = Helper.prepareParser("function foo(): int { return x; } foo();");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:29 Variable with name \"x\" not defined.", e.getMessage());
    }

    @Test
    void variableNotInitialized() {
        ParseTree tree = Helper.prepareParser("function foo(): int { let x: int; return x; } foo();");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:41 Variable with name \"x\" has not been initialized.", e.getMessage());
    }
}