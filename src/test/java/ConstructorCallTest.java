import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class ConstructorCallTest {

    @Test
    void structNotDefined() {
        ParseTree tree = Helper.prepareParser("struct bar { a: int; } function foo(): bar { return create schwurbel(1); }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:52 Struct with name \"schwurbel\" not defined.", e.getMessage());
    }

    @Test
    void numConstructorParameterMissmatch() {
        ParseTree tree = Helper.prepareParser("struct bar { a: int; } function foo(): bar { return create bar(1, false); }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:52 Struct \"bar\" defined 1 fields, but got 2 constructor parameters.", e.getMessage());
    }

    @Test
    void constructorParameterTypeMismatch() {
        ParseTree tree = Helper.prepareParser("struct bar { a: int; } function foo(): bar { return create bar(false); }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:63 argument 0 Type missmatch: cannot combine bool and int", e.getMessage());
    }
}