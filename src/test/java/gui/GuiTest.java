package gui;

import bean.analysis.CountDataOneYear;
import bean.analysis.CountDataPerYear;
import cn.hutool.core.util.RandomUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import gui.bean.ChartData;
import gui.view.ChartGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
    private JButton guiTestButtonButton;
    private final GuiLogCreator guiLogCreator;
    private static int processBarValue = 0;
    private ChartGui chartGui = new ChartGui().init();

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
                processBarValue = processBarValue + RandomUtil.randomInt(0, 10);
                if (processBarValue > 100) {
                    processBarValue = 0;
                }
            }
        }).start();
        guiTestButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> data = new HashMap<>();
                ChartData chartData = new ChartData();
                HashMap<String, Object> hashMap = new HashMap<>();
                CountDataPerYear countDataPerYear = new CountDataPerYear();
                countDataPerYear.setStartYear(2002);
                countDataPerYear.setEndYear(2022);
                java.util.List<CountDataOneYear> list = new ArrayList<>();
                for (int i = 2002; i <= 2022; i++) {
                    list.add(new CountDataOneYear(String.valueOf(i), RandomUtil.randomInt(0, 20)));
                }
                countDataPerYear.setData(list);
                hashMap.put(CountDataPerYear.class.getSimpleName(), countDataPerYear);
                chartData.setPaperInfo(hashMap);
                chartData.setWebsiteInfos(new LinkedHashMap<>());
                data.put(ChartData.class.getSimpleName(), chartData);
                chartGui.start(data);
                chartGui.show();
            }
        });
    }

}
