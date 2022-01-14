package log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.dialect.slf4j.Slf4jLog;

import javax.swing.*;

/**
 * @author yagol
 * @TIME 2021/11/16 - 7:20 下午
 * @Description 自定义Log，实现了swing log text area的连带输出
 **/
public class MySwingTextAreaLog extends Slf4jLog {
    private JTextArea logTextArea;

    public MySwingTextAreaLog(String name) {
        super(name);
    }

    public MySwingTextAreaLog(Class<?> clazz) {
        super(clazz);
    }

    public void setLogTextArea(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    /**
     * 只有INFO界别的才输出到GUI界面，其他的不要给用户展示
     *
     * @param format    文本
     * @param arguments 参数
     */
    @Override
    public void info(String format, Object... arguments) {
        //正常日志输出
        super.info(format, arguments);
        //如果GUI组件不是空，输出到GUI
        if (ObjectUtil.isNotNull(logTextArea)) {
            logTextArea.append(DateUtil.now());
            logTextArea.append(StrUtil.SPACE);
            logTextArea.append(StrUtil.format(format, arguments));
            logTextArea.append("\n");
            logTextArea.setCaretPosition(logTextArea.getText().length());
        }
    }
}
