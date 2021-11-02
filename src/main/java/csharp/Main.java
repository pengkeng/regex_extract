package csharp;

import csharp.grammar.CSharpLexer;
import csharp.grammar.CSharpParser;
import csharp.grammar.CSharpParserBaseListener;
import csharp.grammar.CSharpWalker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import php.PhpRegexExtractor;
import php.grammar.*;
import utils.bean.regexps;
import utils.multithread.ITask;
import utils.multithread.MultiThreadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {
        MultiThreadUtils<String, String> threadUtils = MultiThreadUtils.newInstance();
        threadUtils.execute(new ArrayList<String>(), new ITask<String, String>() {
            @Override
            public String execute(String fileName) {
                File rootPath = new File(fileName);
                String resultFile = fileName + ".json";
                new CSharpRegexExtractor(rootPath, new File(resultFile)).extractToFile();
                return null;
            }
        });

    }
}
