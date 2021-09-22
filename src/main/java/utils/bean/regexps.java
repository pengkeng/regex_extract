package utils.bean;

/**
 * regex 对象
 */
public class regexps {
    private String pattern;
    private String flags;
    private String funcName;
    private int line;

    public regexps(int line, String pattern) {

        this.pattern = pattern;
        this.flags = "";
        this.line = line;
    }

    public regexps(int line, String pattern, String funcName) {

        this.pattern = pattern;
        this.flags = "";
        this.funcName = funcName;
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
}
