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

    public regexps(int line, String flags, String pattern, String funcName) {

        this.pattern = pattern;
        String[] strs = flags.split("\\|");
        StringBuilder fs = new StringBuilder();
        for (String f : strs) {
            if (f.contains("IgnoreCase")) {
                fs.append("i");
            } else if (f.contains("Multiline")) {
                fs.append("m");
            } else if (f.contains("Singleline")) {
                fs.append("s");
            } else if (f.contains("ExplicitCapture")) {
                fs.append("n");
            } else if (f.contains("IgnorePatternWhitespace")) {
                fs.append("x");
            }
        }
        this.flags = fs.toString();
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

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "regexps{" +
                "pattern='" + pattern + '\'' +
                ", flags='" + flags + '\'' +
                ", funcName='" + funcName + '\'' +
                ", line=" + line +
                '}';
    }
}
