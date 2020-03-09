import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import de.hsrm.compiler.Klang.*;
import de.hsrm.compiler.Klang.helper.*;
import de.hsrm.compiler.Klang.nodes.*;

public class Helper {
    public static ParseTree prepareParser(String input) {
        CharStream inStream = CharStreams.fromString(input);
        KlangLexer lexer = new KlangLexer(inStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        KlangParser parser = new KlangParser(tokens);
        return parser.parse();
    }

    public static Map<String, FunctionInformation> getFuncs(ParseTree tree) {
        var functionDefinitions = new HashMap<String, FunctionInformation>();
        new GetFunctions(functionDefinitions).visit(tree);
        return functionDefinitions;
    }

    public static Set<String> getStructNames(ParseTree tree) {
        var structNames = new HashSet<String>();
        new GetStructNames(structNames).visit(tree);
        return structNames;
    }

    public static Map<String, StructDefinition> getStructs(ParseTree tree) {
        var structs = new HashMap<String, StructDefinition>();
        new GetStructs(getStructNames(tree), structs).visit(tree);
        return structs;
    }
}