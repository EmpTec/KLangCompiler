import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class FunctionCallTest {

    @Test
    void funcNotDefined() {
        ParseTree tree = Helper.prepareParser("function foo(): int { return 1; } function main(): int { let x: int = bar(); return 1; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:70 Function with name \"bar\" not defined.", e.getMessage());
    }

    @Test
    void numParameterMismatch() {
        ParseTree tree = Helper.prepareParser("function foo(): int { return 1; } function main(): int { return foo(5); }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:64 Function \"foo\" expects 0 parameters, but got 1.", e.getMessage());
    }

    @Test
    void parameterTypeMissmatch() {
        ParseTree tree = Helper.prepareParser("function foo(x: int): int { return x; } function main(): int { return foo(false); }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:70 argument 0 Expected int but got: bool", e.getMessage());
    }
}