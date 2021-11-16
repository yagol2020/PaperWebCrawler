package gui.designer;

import bean.impl.IeeeSearchQuery;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.impl.IeeeResultProcessor;
import core.impl.LoveScienceDetector;
import gui.param.GuiParam;
import result.BaseResult;
import result.IeeeResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 11:05 上午
 * @Description 工具界面启动类
 **/
public class MainGui {
    private final Log log = LogFactory.get();
    private JTextField searchQueryInput;
    private JTextArea logArea;
    private JPanel main;

    private final PaperInfoGui paperInfoGui = new PaperInfoGui().init();
    private final HelpGui helpGui = new HelpGui().init();

    public MainGui() {
        initButtonFunctions();
    }


    private void saveResult2Csv(BaseResult result) {
        result.save2File();
    }

    private void initButtonFunctions() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.append("执行爬虫任务，请稍后。请勿再次点击\"开始\"按钮\n");
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        BaseResult result = loadPaperInfoData2Table();
                        if (saveResult2Csv.isSelected()) {
                            log.info("存储到csv中，地址为{}", result.getCsvResultPath());
                            saveResult2Csv(result);
                        }
                    }
                });
            }

            private BaseResult loadPaperInfoData2Table() {
                IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery(searchQueryInput.getText());
                IeeeResultProcessor processor = new IeeeResultProcessor();
                IeeeResult ieeeResult = processor.run(ieeeSearchQuery, logArea);
                LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
                BaseResult result = loveScienceDetector.detector(ieeeResult);
                paperInfoGui.start(new HashMap<String, Object>(16) {
                    {
                        put(BaseResult.class.getSimpleName(), result);
                    }
                });
                paperInfoGui.show();
                log.info("论文信息窗口渲染完毕");
                return result;
            }
        });
        searchQueryInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == GuiParam.ENTER_CODE) {
                    searchButton.doClick();
                }
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpGui.show();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGui");
        frame.setContentPane(new MainGui().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel content;
    private JLabel searchQueryLabel;
    private JButton searchButton;
    private JButton helpButton;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JCheckBox saveResult2Csv;
    private JScrollPane logScrollPane;

}
