package csharp;

import csharp.grammar.CSharpLexer;
import csharp.grammar.CSharpParser;
import csharp.grammar.CSharpParserBaseListener;
import csharp.grammar.CSharpWalker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import php.PhpRegexExtractor;
import php.grammar.*;
import utils.bean.regexps;
import utils.multithread.ITask;
import utils.multithread.MultiThreadUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "D:/pqc/csharp/extract";
        String[] files = new File(path).list();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            arrayList.add(files[i]);
        }
//        Collections.addAll(arrayList, files);
        MultiThreadUtils<String, String> threadUtils = MultiThreadUtils.newInstance(30);
        threadUtils.execute(arrayList, new ITask<String, String>() {
            @Override
            public String execute(String fileName) {
                File rootPath = new File(path + "//" + fileName);
                String resultFile = "D:/pqc/csharp/new_result/" + fileName + ".json";
//                if (!new File(resultFile).exists()) {
                    Logger.getGlobal().info(rootPath.getAbsolutePath());
                    new CSharpRegexExtractor(rootPath, new File(resultFile)).extractToFile();
//                }
                return null;
            }
        });

//        File rootPath = new File("C:\\Users\\pengqc\\IdeaProjects\\regex_extract\\src\\main\\java\\csharp\\example\\AssumeRoleAWSCredentialsTest.cs");
//        String resultFile = "test.json";
//        Logger.getGlobal().info(rootPath.getAbsolutePath());
//        new CSharpRegexExtractor(rootPath, new File(resultFile)).extractToFile();

    }


}
