package php;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import php.grammar.*;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        String file = FileUtils.readFileToString(new File("src/main/java/php/examples/array.php"), "utf-8");
        PhpLexer phpLexer = new PhpLexer(CharStreams.fromFileName("src/main/java/php/examples/regex/filter.php"));
        CommonTokenStream tokenStream = new CommonTokenStream(phpLexer);
        PhpParser phpParser = new PhpParser(tokenStream);
        ParseTree tree = phpParser.htmlDocument();
        PhpWalker walker = new PhpWalker();
        PhpParserListener listener = new PhpParserBaseListener();
        walker.walk(listener,tree);
    }
}
