import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class VariableDeclarationTest {
    @Test
    void typeNotDefined() {
        ParseTree tree = Helper.prepareParser("function foo(): int { let X: unk; return 1; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:22 Type unk not defined.", e.getMessage());
    }

    @Test
    void variableRedeclaration()
    {
        ParseTree tree = Helper.prepareParser("function foo(): int { let x: int; let x: bool; return 1; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:34 Redeclaration of variable with name \"x\".", e.getMessage());
    }
}