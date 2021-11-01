package php;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {

        String rootPath = "D:\\pqc\\php\\pkg\\extract";
        File[] dirs = new File(rootPath).listFiles();
        for (int i =257907 ; i < dirs.length; i++) {
            File path = dirs[i];
            String resultFile = "D:\\pqc\\php\\result\\" + i + ".json";
            new PhpRegexExtractor(path, new File(resultFile)).extractToFile();
            Logger.getGlobal().warning("path ->>>>>  " + i);
        }
    }
}
