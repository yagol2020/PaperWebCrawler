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

}
