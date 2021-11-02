package csharp;

import csharp.grammar.CSharpLexer;
import csharp.grammar.CSharpParser;
import csharp.grammar.CSharpParserBaseListener;
import csharp.grammar.CSharpWalker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;
import php.grammar.*;
import utils.IOUtils;
import utils.bean.FileObj;
import utils.bean.regexps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class CSharpRegexExtractor {

    private File rootPath;
    private File resultFile;


    public CSharpRegexExtractor(File rooPath, File resultFile) {
        this.rootPath = rooPath;
        this.resultFile = resultFile;
    }


    /**
     * 提取re的api
     */
    public void extractToFile() {
        LinkedList<FileObj> resultList = new LinkedList<>();
        listFile(rootPath, resultList);
        if (!resultList.isEmpty()) {
            try {
                IOUtils.saveData(resultList, resultFile);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 遍历项目每一个文件，并进行re提取
     *
     * @param dir
     * @param resultList
     */
    private void listFile(File dir, LinkedList<FileObj> resultList) {
        if (dir.isFile()) {
            String fileName = dir.toString();
            if (fileName.endsWith(".cs")) {
                try {
                    System.out.println("111->>>>" + fileName);
                    LinkedList<regexps> regexs = getReFromFile(fileName);
                    if (regexs != null && regexs.size() > 0) {
                        resultList.add(new FileObj(regexs, fileName));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.toString();
                        if (fileName.endsWith(".cs") && FileUtils.sizeOf(file) < 100 * 1024) {
                            try {
                                Logger.getGlobal().info("111->>>>" + fileName);
                                LinkedList<regexps> regexs = getReFromFile(fileName);
                                if (regexs != null && regexs.size() > 0) {
                                    resultList.add(new FileObj(regexs, fileName));
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (file.isDirectory()) {
                        if (file.toString().contains("test")) {
                            continue;
                        }
                        listFile(file, resultList);
                    }
                }
            }
        }
    }

    /**
     * 从单个文件提取re，提取re的真正入口
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    private LinkedList<regexps> getReFromFile(String filePath) throws FileNotFoundException {
        try {

            CSharpLexer cSharpLexer = new CSharpLexer(CharStreams.fromFileName(filePath));
            CommonTokenStream tokenStream = new CommonTokenStream(cSharpLexer);
            CSharpParser cSharpParser = new CSharpParser(tokenStream);
            ParseTree tree = cSharpParser.compilation_unit();

            CSharpWalker walker = new CSharpWalker();
            CSharpParserBaseListener listener = new CSharpParserBaseListener();
            walker.walk(listener, tree);
            return listener.regexpsArrayList;
        } catch (Exception e) {
            return null;
        }
    }
}
