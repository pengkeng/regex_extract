import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

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
public class Main {

    static CompilationUnit cu;

    public static void main(String[] args) throws Exception {
        String rootPath = "/Users/pqc/Downloads/fastjson-master";
        File dir = new File(rootPath);
        LinkedList<FileObj> resultList = new LinkedList<>();
        listFile(dir, resultList);
        if (!resultList.isEmpty()) {
            saveData(resultList, "test.json");
        }
    }

    public static void listFile(File dir, LinkedList<FileObj> resultList) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getAbsolutePath();
                    if (fileName.endsWith(".java")) {
                        try {
                            System.out.println("111->>>>" + fileName);
                            LinkedList<RegexObj> regexs = getReFromFile(fileName);
                            if (regexs.size() > 0) {
                                resultList.add(new FileObj(regexs, fileName));
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (file.isDirectory()) {
                    listFile(file, resultList);
                }
            }
        }
    }

    private static void saveData(LinkedList<FileObj> resultList, String fileName) throws IOException {
        System.out.println(fileName);
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        System.out.println(json);
        FileUtils.writeStringToFile(new File(fileName), json, "UTF-8");

    }

    private static LinkedList<RegexObj> getReFromFile(String filePath) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(filePath);
        CompilationUnit cu = StaticJavaParser.parse(in);
        LinkedList<RegexObj> regexs = new LinkedList<>();
        getNode(cu, new HashMap<>(), regexs);
        return regexs;
    }

    private static void getNode(Node compilationUnit, HashMap<String, String> list, LinkedList<RegexObj> regexs) {
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

    private static void regexProcess(Node compilationUnit, HashMap<String, String> list, LinkedList<RegexObj> regexs) {
        NodeList<Expression> arguments = ((MethodCallExpr) compilationUnit).getArguments();
        if (arguments.size() > 0) {
            Expression argument = arguments.get(0);
            if (argument.isNameExpr()) {
                String regex = list.get(argument.toString());
                if (regex != null && !regex.isEmpty()) {
                    int line = -1;
                    if (argument.getRange().isPresent()) {
                        line = argument.getRange().get().begin.line;
                    }
                    System.out.println(regex);
                    regexs.add(new RegexObj(line, regex));
                }
            }
            if (argument.isStringLiteralExpr()) {
                String regex = ((StringLiteralExpr) argument).asString();
                if (regex != null && !regex.isEmpty()) {
                    int line = -1;
                    if (argument.getRange().isPresent()) {
                        line = argument.getRange().get().begin.line;
                    }
                    System.out.println(regex);
                    regexs.add(new RegexObj(line, regex));
                }
            }
        }
    }
}

class RegexObj {
    private int line;
    private String regex;

    RegexObj(int line, String regex) {

        this.line = line;
        this.regex = regex;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}

class FileObj {
    private LinkedList<RegexObj> regexObj;
    private String fileName;

    FileObj(LinkedList<RegexObj> regexObj, String fileName) {
        this.regexObj = regexObj;
        this.fileName = fileName;
    }

    public LinkedList<RegexObj> getRegexObj() {
        return regexObj;
    }

    public void setRegexObj(LinkedList<RegexObj> regexObj) {
        this.regexObj = regexObj;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
