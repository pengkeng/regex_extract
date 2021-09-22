package php;

import org.antlr.v4.runtime.*;
import php.grammar.*;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        String file = FileUtils.readFileToString(new File("src/main/java/php/examples/array.php"), "utf-8");
        PhpLexer phpLexer = new PhpLexer(CharStreams.fromFileName("src/main/java/php/examples/regex/filter.php"));


        String rootPath = "src/main/java/php/examples/regex";
        String resultFile = "test.json";
        new PhpRegexExtractor(new File(rootPath), new File(resultFile)).extractToFile();
    }
}
