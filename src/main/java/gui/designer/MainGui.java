package gui.designer;

import bean.impl.IeeeSearchQuery;
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
 * @Description
 **/
public class MainGui {
    private final Log log = LogFactory.get();
    private JTextField searchQueryInput;
    private JTextArea logArea;
    private JPanel main;

    private final PaperInfoGui paperInfoGui = new PaperInfoGui().init();

    public MainGui() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery(searchQueryInput.getText());
                IeeeResultProcessor processor = new IeeeResultProcessor();
                IeeeResult ieeeResult = processor.run(ieeeSearchQuery);
                LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
                BaseResult result = loveScienceDetector.detector(ieeeResult);
                paperInfoGui.start(new HashMap<String, Object>(16) {
                    {
                        put(BaseResult.class.getSimpleName(), result);
                    }
                });
                paperInfoGui.show();
                log.info("论文信息窗口渲染完毕");
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

}
