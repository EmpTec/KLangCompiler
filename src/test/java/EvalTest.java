import static org.junit.jupiter.api.Assertions.assertEquals;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.hsrm.compiler.Klang.ContextAnalysis;
import de.hsrm.compiler.Klang.visitors.EvalVisitor;

public class EvalTest {

    /**
     * code snippet courtesy of
     * Prof. Dr. Sven Eric Panitz
     * http://panitz.name/
     */
    static final String listSource =
        "struct List {\n" +
        "  head: int;\n" +
        "  tail: List;\n" +
        "}\n" +

        "function Cons(hd: int, tl:List): List {\n" +
        "  return create List(hd, tl);\n" +
        "}\n" +

        "function Nil(): List {\n" +
        "  return naught;\n" +
        "}\n" +

        "function isEmpty(this:List): bool {\n" +
        "  return this==naught;\n" +
        "}\n" +

        "function get(this: List, index: int): int {\n" +
        "  if (index == 0) {\n" +
        "    return this.head;\n" +
        "  } else {\n" +
        "    return get(this.tail, index - 1);\n" +
        "  }\n" +
        "}\n" +
        
        "function append(this: List, that: List): List {\n" +
        "  if(isEmpty(this)){\n" +
        "    return that;\n" +
        "  }else{\n" +
        "    return Cons(this.head,append(this.tail,that));\n" +
        "  }\n" +
        "}\n" +
        
        "function sum(this: List): int {\n" +
        "  if (isEmpty(this)) {\n" +
        "    return 0;\n" +
        "  }\n" +
        "  return this.head + sum(this.tail);\n" +
        "}\n" +
        
        "function sum2(this: List): int {\n" +
        "  return sumAux(this,0);\n" +
        "}\n" +
        
        "function sumAux(this: List,result:int): int {\n" +
        "  if (isEmpty(this)) {\n" +
        "    return result;\n" +
        "  }\n" +
        "  return sumAux(this.tail,result+this.head);\n" +
        "}\n" +
        
        "function length(this: List): int {\n" +
        "  return lengthAux(this,0);\n" +
        "}\n" +
        
        "function lengthAux(this: List,result:int): int {\n" +
        "  if (isEmpty(this)) {\n" +
        "    return result;\n" +
        "  }\n" +
        "  return lengthAux(this.tail,result+1);\n" +
        "}\n" +
        
        "function main():int{\n" +
        "  let xs : List;\n" +
        "  xs = Cons(1,Cons(2,Cons(3,Cons(4,Nil()))));\n" +
        "  let x:int = get(Cons(sum(xs),Cons(sum2(xs)+24,Nil())),1); \n" +
        "  let xsxs : List = append(xs,xs);\n" +
        "  let y:int = length(xsxs)+x;\n" +
        "  return y;\n" +
        "}\n";

    @Test
    void evaluateListSource() {
        ParseTree tree = Helper.prepareParser(listSource);
        var funcs = Helper.getFuncs(tree);
        var structs = Helper.getStructs(tree);
        ContextAnalysis ctxAnal = new ContextAnalysis(funcs, structs);
        var root = ctxAnal.visit(tree);
        var result = root.welcome(new EvalVisitor(structs));
        assertEquals(result.asInteger(), 42);
    }
}