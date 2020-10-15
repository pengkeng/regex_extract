import bean.FileObj;
import bean.regexps;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author pqc
 */
public class RegexExtractor {

    private File rootPath;
    private File resultFile;


    public RegexExtractor(File rooPath, File resultFile) {
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
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.toString();
                    if (fileName.endsWith(".java")) {
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
                } else if (file.isDirectory()) {
                    if (file.toString().contains("test")) {
                        return;
                    }
                    listFile(file, resultList);
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
            FileInputStream in = new FileInputStream(filePath);
            CompilationUnit cu = StaticJavaParser.parse(in);
            LinkedList<regexps> regexs = new LinkedList<>();
            getNode(cu, new HashMap<>(), regexs);
            return regexs;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 遍历每一个AST节点，根据节点的属性提判断是否存在re
     *
     * @param compilationUnit
     * @param list
     * @param regexs
     */
    private void getNode(Node compilationUnit, HashMap<String, String> list, LinkedList<regexps> regexs) {
        List<Node> childNodes = compilationUnit.getChildNodes();
        if (compilationUnit instanceof VariableDeclarator && "String".equals(((VariableDeclarator) compilationUnit).getTypeAsString())) {
            String key = ((VariableDeclarator) compilationUnit).getName().toString();
            if (((VariableDeclarator) compilationUnit).getInitializer().isPresent()) {
                Optional<Expression> init = ((VariableDeclarator) compilationUnit).getInitializer();
                if (init.isPresent() && init.get().isStringLiteralExpr()) {
                    String value = ((StringLiteralExpr) init.get()).asString();
                    list.put(key, value);
                }
                if (init.isPresent() && init.get().isNameExpr()) {
                    String value = list.get(init.get().toString());
                    list.put(key, value);
                }

            }
        }
        if (compilationUnit instanceof AssignExpr) {
            String key = ((AssignExpr) compilationUnit).getTarget().toString();
            Expression valueObj = ((AssignExpr) compilationUnit).getValue();
            if (valueObj.isStringLiteralExpr()) {
                String value = valueObj.toString();
                list.put(key, value);
            }
            if (valueObj.isNameExpr()) {
                String value = list.get(valueObj.toString());
                list.put(key, value);
            }
        }
        if (compilationUnit instanceof MethodCallExpr) {
            String name = ((MethodCallExpr) compilationUnit).getName().toString();
            switch (name) {
                case "matches":
                    regexProcess(compilationUnit, list, regexs);
                    break;
                case "compile":
                    regexProcess(compilationUnit, list, regexs);
                    break;
                case "replaceAll":
                    regexProcess(compilationUnit, list, regexs);
                    break;
                case "replace":
                    regexProcess(compilationUnit, list, regexs);
                    break;
                default:
                    break;
            }
        }
        for (Node node : childNodes) {
            getNode(node, list, regexs);
        }
    }

    /**
     * 从当前AST节点提取re
     *
     * @param compilationUnit
     * @param list
     * @param regexs
     */
    private void regexProcess(Node compilationUnit, HashMap<String, String> list, LinkedList<regexps> regexs) {
        NodeList<Expression> arguments = ((MethodCallExpr) compilationUnit).getArguments();
        if (arguments.size() > 0) {
            Expression argument = arguments.get(0);
            if (argument.isNameExpr()) {
                String regex = list.get(argument.toString());
                if (regex != null && !regex.isEmpty() && regex.length() > 5) {
                    int line = -1;
                    if (argument.getRange().isPresent()) {
                        line = argument.getRange().get().begin.line;
                    }
                    System.out.println(regex);
                    regexs.add(new regexps(line, regex));
                }
            }
            if (argument.isStringLiteralExpr()) {
                String regex = ((StringLiteralExpr) argument).asString();
                if (regex != null && !regex.isEmpty() && regex.length() > 5) {
                    int line = -1;
                    if (argument.getRange().isPresent()) {
                        line = argument.getRange().get().begin.line;
                    }
                    System.out.println(regex);
                    regexs.add(new regexps(line, regex));
                }
            }
        }
    }
}

