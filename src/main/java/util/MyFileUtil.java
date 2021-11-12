package util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:40 下午
 * @Description 文件工具类，用于读写文件
 **/
public class MyFileUtil {
    /**
     * 清空文件，将结果写入文件中
     *
     * @param filePath 文件地址
     * @param result   内容
     */
    public static void write(String filePath, String result) {
        cn.hutool.core.io.FileUtil.writeString(result, filePath, CharsetUtil.UTF_8);
    }

    /**
     * 以追加形式，将结果写入文件种
     *
     * @param filePath 文件地址
     * @param result   内容
     */
    public static void append(String filePath, String result) {
        cn.hutool.core.io.FileUtil.appendString(result, filePath, CharsetUtil.UTF_8);
    }
}
