package utils;

import bean.FileObj;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IOUtils {
    /**
     * 将结果输出到文件
     *
     * @param resultList
     * @param file
     * @throws IOException
     */
    public static void saveData(List resultList, File file) throws IOException {
        System.out.println(file);
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        System.out.println(json);
        FileUtils.writeStringToFile(file, json, "UTF-8");
    }
}
