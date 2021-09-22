package javal;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String rootPath = "src/main/java/javal/example/";
        String resultFile = "javatest.json";
        new RegexExtractor(new File(rootPath), new File(resultFile)).extractToFile();
//        CVE_Company();
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