import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class ParameterTest {
    @Test
    void typeNotDefined() {
        ParseTree tree = Helper.prepareParser("struct test { a: schwurbel; } function foo(): int { return 1; } foo();");
        Exception e = assertThrows(RuntimeException.class, () -> Helper.getStructs(tree));
        assertEquals("Error in line 1:14 Type schwurbel not defined.", e.getMessage());
    }
}