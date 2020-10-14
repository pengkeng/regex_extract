package bean;

import java.util.LinkedList;

/**
 * 每一个文件输出对象
 */
public class FileObj {
    private LinkedList<RegexObj> regexObj;
    private String fileName;

    public FileObj(LinkedList<RegexObj> regexObj, String fileName) {
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
