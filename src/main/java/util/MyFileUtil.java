package util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

import java.io.InputStream;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:40 下午
 * @Description 文件工具类，用于读写文件
 **/
public class MyFileUtil {
    public static void writeMultiLine2Csv(String filePath, String[] header, List<String[]> results) {
        CsvWriter csvWriter = CsvUtil.getWriter(filePath + ".csv", CharsetUtil.CHARSET_UTF_8);
        csvWriter.writeLine(header);
        csvWriter.write(results);
        csvWriter.close();
    }

    public static String readFile(String path) {
        InputStream inputStream = MyFileUtil.class.getResourceAsStream(path);
        return IoUtil.read(inputStream, CharsetUtil.CHARSET_UTF_8);
    }

    public static String getResourcesFilePath(String filePathInResources) {
        return MyFileUtil.class.getResource(filePathInResources).getPath();
    }
}
