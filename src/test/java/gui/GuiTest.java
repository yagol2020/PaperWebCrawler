package gui;

import javax.swing.*;
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
    private final GuiLogCreator guiLogCreator;

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

            }
        });
    }
}
