package gui.view;

import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 2:16 下午
 * @Description 界面接口，除主界面外其他均需实现本接口。
 * 理论上，顺序为new Gui().init()，然后通过start传递数据，最后show()
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
     */
    void show();

    /**
     * 初始化组件功能
     */
    void initComponentFunctions();
}
