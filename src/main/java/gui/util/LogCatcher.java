package gui.util;

import cn.hutool.core.thread.ThreadUtil;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author yagol
 * @TIME 2021/11/13 - 9:43 下午
 * @Description
 **/
public class LogCatcher implements Runnable {

    JTextArea logTextArea;

    public LogCatcher(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    public static void main(String[] args) {
        LogCatcher logCatcher = new LogCatcher(null);
        ThreadUtil.execute(logCatcher);
    }

    @Override
    public void run() {
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        PrintStream cacheStream = new PrintStream(baoStream);
        System.setOut(cacheStream);
        while (true) {
            baoStream.reset();
            String logMessage = baoStream.toString();
            logTextArea.append(logMessage);
        }
    }
}
