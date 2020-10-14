package bean;

/**
 * regex 对象
 */
public class RegexObj {
    private int line;
    private String regex;

    public RegexObj(int line, String regex) {

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
