package php;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;
import php.grammar.*;

import utils.bean.FileObj;
import utils.bean.regexps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Logger;

import utils.IOUtils;

public class PhpRegexExtractor {

    private File rootPath;
    private File resultFile;


    public PhpRegexExtractor(File rooPath, File resultFile) {
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
            if (fileName.endsWith(".php")) {
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
                        if (fileName.endsWith(".php") && FileUtils.sizeOf(file) < 100 * 1024) {
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
     * 从单个java文件提取re，提取re的真正入口
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    private LinkedList<regexps> getReFromFile(String filePath) throws FileNotFoundException {
        try {

            PhpLexer phpLexer = new PhpLexer(CharStreams.fromFileName(filePath));
            CommonTokenStream tokenStream = new CommonTokenStream(phpLexer);

            PhpParser phpParser = new PhpParser(tokenStream);

            ParseTree tree = phpParser.htmlDocument();


            PhpWalker walker = new PhpWalker();
            PhpParserListener listener = new PhpParserBaseListener();
            walker.walk(listener, tree);
            return walker.regexs;
        } catch (Exception e) {
            return null;
        }
    }
}
