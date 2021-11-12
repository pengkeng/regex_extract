package php;

import csharp.CSharpRegexExtractor;
import org.apache.commons.io.FileUtils;
import utils.multithread.ITask;
import utils.multithread.MultiThreadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {


        String rootPath = "D:/pqc/php/pkg/extract";
        String[] files = new File(rootPath).list();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(files));
        MultiThreadUtils<String, String> threadUtils = MultiThreadUtils.newInstance(40);
        threadUtils.execute(arrayList, new ITask<String, String>() {
            @Override
            public String execute(String fileName) {
                File Path = new File(rootPath + "/" + fileName);
                String resultFile = "D:/pqc/php/new_result/" + fileName + ".json";
                if (!new File(resultFile).exists()) {
                    Logger.getGlobal().info(Path.getAbsolutePath());
                    new PhpRegexExtractor(Path, new File(resultFile)).extractToFile();
                }
                return null;
            }
        });
//        new PhpRegexExtractor(new File("C:\\Users\\pengqc\\IdeaProjects\\regex_extract\\src\\main\\java\\php\\examples\\regex"), new File("php.json")).extractToFile();
    }
}
