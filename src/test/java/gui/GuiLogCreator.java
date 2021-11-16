package gui;

import log.MyLogFactory;
import log.MySwingTextAreaLog;

import javax.swing.*;

/**
 * @author yagol
 * @TIME 2021/11/16 - 7:16 下午
 * @Description log信息生成器
 **/
public class GuiLogCreator {
    private final MySwingTextAreaLog log = MyLogFactory.get();

    GuiLogCreator(JTextArea jTextArea) {
        log.setLogTextArea(jTextArea);
    }

    public static void main(String[] args) {
    }

    public void logInOtherClazz() {
        log.info("a");
    }
}
