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
     * 向组件传递数据
     *
     * @param data 数据
     */
    void start(HashMap<String, Object> data);

    /**
     * 初始化窗口，注意，不能被内部的构造函数调用，任何时候都应该由外部调用
     *
     * @return 窗口自身
     */
    BaseGui init();

    /**
     * 展示窗口
     */
    void show();

    /**
     * 初始化组件功能，应该由构造函数内部调用
     */
    void initComponentFunctions();
}
