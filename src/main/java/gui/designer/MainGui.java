package gui.designer;

import bean.impl.IeeeSearchQuery;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import config.MyConfig;
import core.impl.IeeeResultProcessor;
import core.impl.LoveScienceDetector;
import gui.param.GuiParam;
import log.MyLogFactory;
import log.MySwingTextAreaLog;
import result.BaseResult;
import result.IeeeResult;
import util.JarUtil;

import javax.swing.*;
import java.awt.*;
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
    private final MyConfig config = MyConfig.initConfig(JarUtil.PWC_JAR_PATH);

    private final MySwingTextAreaLog log = MyLogFactory.get();
    private JTextField searchQueryInput;
    private JTextArea logArea;
    private JPanel main;

    private final PaperInfoGui paperInfoGui = new PaperInfoGui().init();
    private final HelpGui helpGui = new HelpGui().init();

    public MainGui() {
        log.setLogTextArea(logArea);
        initComponent();
        initButtonFunctions();
        log.info(JarUtil.PWC_JAR_PATH);
    }

    private void initComponent() {
        if (StrUtil.isNotEmpty(config.getChrome().getDriverPath())) {
            driverFilePath.setText(config.getChrome().getDriverPath());
        }
    }


    private void saveResult2Csv(BaseResult result) {
        result.save2File();
    }

    private void initButtonFunctions() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StrUtil.isNotEmpty(config.getChrome().getDriverPath())) {
                    log.info("执行爬虫任务，请稍后。请勿再次点击\"开始\"按钮");
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            BaseResult result = loadPaperInfoData2Table();
                            if (saveResult2Csv.isSelected() && ObjectUtil.isNotNull(result)) {
                                assert result != null;
                                log.info("存储到csv中，地址为{}", result.getCsvResultPath());
                                saveResult2Csv(result);
                            }
                        }
                    });
                } else {
                    log.info("驱动未选定，请选定驱动地址！");
                }
            }

            private BaseResult loadPaperInfoData2Table() {
                IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery(searchQueryInput.getText());
                IeeeResultProcessor processor = new IeeeResultProcessor();
                IeeeResult ieeeResult = processor.run(ieeeSearchQuery, logArea);
                if (ArrayUtil.isNotEmpty(ieeeResult.getPaperList())) {
                    LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
                    BaseResult result = loveScienceDetector.detector(ieeeResult, logArea);
                    paperInfoGui.start(new HashMap<String, Object>(16) {
                        {
                            put(BaseResult.class.getSimpleName(), result);
                        }
                    });
                    paperInfoGui.show();
                    log.info("论文信息窗口渲染完毕");
                    return result;
                } else {
                    return null;
                }
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
        driverChoseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser driverChooser = new JFileChooser();
                driverChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (driverChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                    driverFilePath.setText(driverChooser.getSelectedFile().getAbsolutePath());
                    config.getChrome().setDriverPath(driverFilePath.getText());
                    log.info("设置驱动地址为:{}", driverFilePath.getText());
                    MyConfig.createOrUpdateConfig(config, JarUtil.PWC_JAR_PATH);
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
    private JCheckBox saveResult2Csv;
    private JScrollPane logScrollPane;
    private JTextField driverFilePath;
    private JButton driverChoseButton;
    private JLabel driverChoseLabel;

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
        main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setPreferredSize(new Dimension(900, 500));
        titleLabel = new JLabel();
        titleLabel.setText("Paper Web Crawler 文献网站爬虫工具");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        main.add(titleLabel, gbc);
        authorLabel = new JLabel();
        authorLabel.setText("作者：@yagol, @vencerk");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        main.add(authorLabel, gbc);
        content = new JPanel();
        content.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        main.add(content, gbc);
        searchQueryInput = new JTextField();
        content.add(searchQueryInput, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        content.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("开始");
        panel2.add(searchButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveResult2Csv = new JCheckBox();
        saveResult2Csv.setSelected(true);
        saveResult2Csv.setText("是否保存到csv");
        panel2.add(saveResult2Csv, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        helpButton = new JButton();
        helpButton.setText("关于帮助");
        helpButton.setMnemonic('帮');
        helpButton.setDisplayedMnemonicIndex(2);
        panel3.add(helpButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        driverFilePath = new JTextField();
        panel4.add(driverFilePath, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        driverChoseButton = new JButton();
        driverChoseButton.setText("选择驱动");
        panel4.add(driverChoseButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        driverChoseLabel = new JLabel();
        driverChoseLabel.setText("浏览器驱动（目前仅支持chrome）");
        panel4.add(driverChoseLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchQueryLabel = new JLabel();
        searchQueryLabel.setText("关键字");
        content.add(searchQueryLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        main.add(logScrollPane, gbc);
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logScrollPane.setViewportView(logArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return main;
    }
}
