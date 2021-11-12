package javal.example;

import com.sun.xml.internal.fastinfoset.Decoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

//    protected String m_regexMatch = "([\\s\\S]+)";
    protected static String m_regexMatch = "([\\s\\S]+123)";

    public static void main(String[] args) {
        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".*runoob.*";
        pattern.replace("<ab>", "12");
        pattern.replaceAll("123", "123");
        boolean isMatch = Pattern.matches(pattern, content);
        Pattern.compile("ewq", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }

    public String faq(String in) throws Exception {
        Pattern.compile(m_regexMatch);
        return in.replace('\'', '"');
    }
}
