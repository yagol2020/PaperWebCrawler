package gui.view;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import util.MyUpdateUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/22 - 8:03 下午
 * @Description PWC更新界面
 **/
public class UpdateGui implements BaseGui {
    private JFrame jFrame;
    private String savePath;

    public UpdateGui() {
        initComponentFunctions();
    }

    @Override
    public void start(HashMap<String, Object> data) {

    }

    @Override
    public UpdateGui init() {
        jFrame = new JFrame("UpdateGui");
        jFrame.setContentPane(updatePanel);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.pack();
        update.setEnabled(false);
        return this;
    }

    @Override
    public void show() {
        jFrame.setVisible(true);
        updateLogTextArea.setText(StrUtil.EMPTY);
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                downloadUpdateFile();
            }
        });
    }

    private void downloadUpdateFile() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    savePath = MyUpdateUtil.checkUpdate(updateProgressBar, updateLogTextArea);
                    if (!StrUtil.equals(savePath, MyUpdateUtil.ALREADY_NEWEST_VERSION)) {
                        update.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(updatePanel, "已为最新版，无需升级！");
                    }
                } catch (Exception e) {
                    updateLogTextArea.append(DateUtil.now() + StrUtil.SPACE + "下载更新文件时出现错误，错误信息为：" + e.getMessage() + StrUtil.LF);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initComponentFunctions() {
        update.addActionListener(e -> {
            if (StrUtil.isNotEmpty(savePath)) {
                JOptionPane.showMessageDialog(updatePanel, "新版本的地址为" + StrUtil.LF + savePath + StrUtil.LF + StrUtil.LF
                        + "在使用前请重新命名为PWC.exe");
            }
        });
    }


    private JPanel updatePanel;
    private JProgressBar updateProgressBar;
    private JTextArea updateLogTextArea;
    private JLabel updateLabel;
    private JLabel updateLogLabel;
    private JButton update;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        updatePanel.setPreferredSize(new Dimension(600, 200));
        updateLabel = new JLabel();
        updateLabel.setText("下载进度");
        updatePanel.add(updateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        updateProgressBar = new JProgressBar();
        updateProgressBar.setStringPainted(true);
        updatePanel.add(updateProgressBar, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        updatePanel.add(scrollPane1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        updateLogTextArea = new JTextArea();
        updateLogTextArea.setEditable(false);
        scrollPane1.setViewportView(updateLogTextArea);
        updateLogLabel = new JLabel();
        updateLogLabel.setText("日志");
        updatePanel.add(updateLogLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        update = new JButton();
        update.setText("更新");
        updatePanel.add(update, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return updatePanel;
    }

}
