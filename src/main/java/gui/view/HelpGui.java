package gui.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import param.NormalParam;
import util.MyFileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 7:49 下午
 * @Description
 **/
public class HelpGui implements BaseGui {
    public HelpGui() {
        initComponentFunctions();
    }

    @Override
    public void initComponentFunctions() {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }


    private JPanel mainPanel;
    private JButton closeButton;
    private JPanel helpPanel;
    private JPanel aboutPanel;
    private JTextArea helpTextArea;
    private JTextArea aboutTextArea;
    private JFrame frame;

    /**
     * 关于和帮助界面无需传递外部数据
     *
     * @param data 数据
     */
    @Override
    @Deprecated
    public void start(HashMap<String, Object> data) {

    }

    @Override
    public HelpGui init() {
        frame = new JFrame("HelpGui");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        aboutTextArea.setText(MyFileUtil.readFile(NormalParam.ABOUT_FILE_PATH));
        helpTextArea.setText(MyFileUtil.readFile(NormalParam.HELP_FILE_PATH));
        return this;
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

}
