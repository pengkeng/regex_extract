package csharp;

import csharp.grammar.CSharpLexer;
import csharp.grammar.CSharpParser;
import csharp.grammar.CSharpParserBaseListener;
import csharp.grammar.CSharpWalker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import php.grammar.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/csharp/example/regex.cs";
        CSharpLexer cSharpLexer = new CSharpLexer(CharStreams.fromFileName(filePath));
        CommonTokenStream tokenStream = new CommonTokenStream(cSharpLexer);
        CSharpParser cSharpParser = new CSharpParser(tokenStream);
        ParseTree tree = cSharpParser.compilation_unit();

        CSharpWalker walker = new CSharpWalker();
        CSharpParserBaseListener listener = new CSharpParserBaseListener();
        walker.walk(listener,tree);
    }
}
