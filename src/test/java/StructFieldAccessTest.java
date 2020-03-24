import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class StructFieldAccessTest {
    @Test
    void variableNotDefined() {
        ParseTree tree = Helper.prepareParser("struct test { a: int; } function foo(): int { return str.a; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:53 Variable with name str not defined.", e.getMessage());
    }

    @Test
    void fieldAssignmentOnNonStruct() {
        ParseTree tree = Helper.prepareParser("struct test { a: int; } function foo(): int { let x: int = 0; return x.a; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:69 Variable must reference a struct but references int.", e.getMessage());
    }
}