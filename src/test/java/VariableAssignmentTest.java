import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;

public class VariableAssignmentTest {

    @Test
    void variableNotDefined() {
        ParseTree tree = Helper.prepareParser("function foo(): int { x = 1; return 1; }");
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        
        Exception e = assertThrows(RuntimeException.class, () -> ctxAnal.visit(tree));
        assertEquals("Error in line 1:22 Variable with name \"x\" not defined.", e.getMessage());
    }
}