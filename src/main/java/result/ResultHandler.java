package result;

import bean.BaseResult;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:40 下午
 * @Description
 **/
public class ResultHandler {
    /**
     * 清空文件，将结果写入文件中
     *
     * @param filePath
     * @param result
     */
    public void reWrite(String filePath, BaseResult result) {
        FileUtil.writeString(result.genResultStr(), filePath, CharsetUtil.UTF_8);
    }

    /**
     * 以追加形式，将结果写入文件种
     *
     * @param filePath
     * @param result
     */
    public void append(String filePath, BaseResult result) {
        if (!FileUtil.isFile(filePath)) {
            reWrite(filePath, result);
        } else {
            FileUtil.appendString(result.genResultStr(), filePath, CharsetUtil.UTF_8);
        }
    }
}
