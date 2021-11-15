package gui.designer;

import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 2:16 下午
 * @Description
 **/
public interface BaseGui {
    /**
     * 启动窗口，并显示
     *
     * @param data 数据
     */
    void start(HashMap<String, Object> data);

    /**
     * 初始化窗口
     *
     * @return 窗口自身
     */
    BaseGui init();

    /**
     * 展示窗口
     *
     * @return 窗口自身
     */
    BaseGui show();
}
