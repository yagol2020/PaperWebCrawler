package gui.util;

import result.BaseResult;

/**
 * @author yagol
 * @TIME 2021/11/13 - 1:27 下午
 * @Description Gui相关数据填充工具类
 **/
public class DataUtil {
    @Deprecated
    public static Object[][] fillPaperInfoTableData(BaseResult result) {
        Object[][] data = new Object[result.getPaperList().size()][result.genHeader().length];
        for (int i = 0; i < data.length; i++) {
            data[i] = result.genResults().get(i);
        }
        return data;
    }
}
