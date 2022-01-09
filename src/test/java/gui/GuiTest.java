package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author yagol
 * @TIME 2021/11/16 - 7:15 下午
 * @Description
 **/
public class GuiTest {
    private JPanel logTestPanel;
    private JButton testLog;
    private JTextArea logArea;
    private JCheckBox checkBox1;
    private JProgressBar processBar;
    private final GuiLogCreator guiLogCreator;
    private static int processBarValue = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GuiTest");
        frame.setContentPane(new GuiTest().logTestPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public GuiTest() {
        guiLogCreator = new GuiLogCreator(logArea);
        testLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiLogCreator.logInOtherClazz();
                testLog.setEnabled(false);
            }
        });
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    guiLogCreator.logInOtherClazz();
                }
            }
        });
        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processBar.setValue(processBarValue);
                processBarValue = processBarValue + 10;
            }
        }).start();
    }

}
