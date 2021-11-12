package javal;

import csharp.CSharpRegexExtractor;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import utils.multithread.ITask;
import utils.multithread.MultiThreadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        single();
        m();
    }

    private static void m() {
        String rootPath = "D:/pqc/maven/2021-1-20/maven-file";
        String[] files = new File(rootPath).list();
        String[] files1 = new File(rootPath + "/com").list();
        String[] files2 = new File(rootPath + "/org").list();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            if (!file.equals("com") && !file.equals("org")) {
                arrayList.add(file);
            }
        }
        for (String file : files1) {
            arrayList.add("com/" + file);
        }
        for (String file : files2) {
            arrayList.add("org/" + file);
        }
        MultiThreadUtils<String, String> threadUtils = MultiThreadUtils.newInstance(15);
        threadUtils.execute(arrayList, new ITask<String, String>() {
            @Override
            public String execute(String fileName) {
                File path = new File(rootPath + "/" + fileName);
                String resultFile = "D:/pqc/maven/2021-1-20/new_result/" + fileName + ".json";
                if (!new File(resultFile).exists()) {
                    Logger.getGlobal().info(path.getAbsolutePath());
                    new RegexExtractor(path, new File(resultFile)).extractToFile();
                }
                return null;
            }
        });
//        CVE_Company();
    }

    public static void single() {
        File path = new File("C:\\Users\\pengqc\\IdeaProjects\\regex_extract\\src\\main\\java\\javal\\example");
//        path = new File("D:\\pqc\\maven\\2021-1-20\\maven-file\\ai\\libs\\thirdparty\\interruptible-weka\\0.1.0\\interruptible-weka-0.1.0-sources\\weka\\filters\\RenameRelation.java");
        String resultFile = "java.json";
        Logger.getGlobal().info(path.getAbsolutePath());
        new RegexExtractor(path, new File(resultFile)).extractToFile();
    }

    public static void CVE_Company() {
        String rootPath = "D:/pqc/extract-cve-company";
        File[] files = new File(rootPath).listFiles();
        assert files != null;
        for (File file : files) {
            file = new File(file.getAbsolutePath() + "/java");
            if (file.isDirectory()) {
                File[] projects = file.listFiles();
                assert projects != null;
                for (File project : projects) {
                    if (project.isDirectory()) {
                        File output = new File(project.getAbsolutePath().replace("extract", "json") + ".json");
                        System.out.println(project);
                        System.out.println(output);
                        if (output.exists()) {
                            continue;
                        }
                        new RegexExtractor(project, output).extractToFile();
//                        try {
//                            FileUtils.forceDeleteOnExit(output);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }

            }
        }


    }
}
