package gui.view;

import bean.result.AcmResult;
import bean.result.BaseResult;
import bean.result.IeeeResult;
import bean.searchquery.impl.AcmSearchQuery;
import bean.searchquery.impl.IeeeSearchQuery;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import config.MyConfig;
import core.detector.impl.LoveScienceDetector;
import core.processor.impl.AcmResultProcessor;
import core.processor.impl.IeeeResultProcessor;
import gui.param.GuiParam;
import log.MyLogFactory;
import log.MySwingTextAreaLog;
import param.PaperWebSiteEnum;
import util.JarUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author yagol
 * @TIME 2021/11/15 - 11:05 上午
 * @Description 工具界面启动类
 **/
public class MainGui {
    private MyConfig config = MyConfig.initConfig(JarUtil.PWC_JAR_PATH);

    private final MySwingTextAreaLog log = MyLogFactory.get();
    private JTextField searchQueryInput;
    private JTextArea logArea;
    private JPanel main;

    private final PaperInfoGui paperInfoGui = new PaperInfoGui().init();
    private final HelpGui helpGui = new HelpGui().init();

    IeeeResultProcessor processor = new IeeeResultProcessor();
    AcmResultProcessor acmResultProcessor = new AcmResultProcessor();


    public MainGui() {
        $$$setupUI$$$();
        log.setLogTextArea(logArea);
        initComponent();
        initComponentFunctions();
        log.info("检测到执行路径为{}", JarUtil.PWC_JAR_PATH);


    }

    private void initComponent() {
        if (StrUtil.isNotEmpty(config.getChrome().getDriverPath())) {
            output.setText(config.getChrome().getDriverPath());
        }
    }


    private void saveResult2Csv(BaseResult result) {
        result.save2File();
    }

    private void initComponentFunctions() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StrUtil.isNotEmpty(configPath.getText())) {
                    String configPathStr = configPath.getText();
                    //判断地址是不是以/结尾，如果不是，则自己加一个
                    if (!StrUtil.endWith(configPathStr, StrUtil.SLASH)) {
                        configPathStr += "/";
                    }
                    config = MyConfig.createOrUpdateConfig(config, configPathStr);
                }
                if (saveResult2Csv.isSelected() && StrUtil.isEmpty(outputPath.getText())) {
                    log.info("如果您选择了【输出结果】选项，请选择输出文件夹；否则，请取消【输出结果】选项");
                    return;
                }
                if (StrUtil.isNotEmpty(config.getChrome().getDriverPath())) {
                    log.info("驱动地址为{}", config.getChrome().getDriverPath());
                    log.info("执行爬虫任务，请稍后。请勿再次点击\"开始\"按钮");
                    ThreadUtil.execute(() -> {
                        searchButton.setEnabled(false);
                        paperInfoGui.cleanTable();
                        processor.runnable = true;
                        acmResultProcessor.runnable = true;
                        CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(2);
                        if (ieeeChooseBox.isSelected()) {
                            ThreadUtil.execute(new Runnable() {
                                @Override
                                public void run() {
                                    BaseResult result = loadPaperInfoDataTable(PaperWebSiteEnum.IEEE_XPLORE);
                                    saveResult(result);
                                    countDownLatch.countDown();
                                }
                            });
                        } else {
                            countDownLatch.countDown();
                        }
                        if (acmChooseBox.isSelected()) {
                            ThreadUtil.execute(new Runnable() {
                                @Override
                                public void run() {
                                    BaseResult result = loadPaperInfoDataTable(PaperWebSiteEnum.ACM);
                                    saveResult(result);
                                    countDownLatch.countDown();
                                }
                            });
                        } else {
                            countDownLatch.countDown();
                        }
                        try {
                            log.info("等待爬虫进程完成...");
                            countDownLatch.await();
                        } catch (InterruptedException ex) {
                            log.info("等待爬虫进程同步时发生错误！错误信息为{}", ex.getMessage());
                            ex.printStackTrace();
                        }
                        paperInfoGui.show();
                        searchButton.setEnabled(true);
                    });
                } else {
                    log.info("驱动未选定，请选定驱动地址！");
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
                    output.setText(driverChooser.getSelectedFile().getAbsolutePath());
                    config.getChrome().setDriverPath(output.getText());
                    log.info("设置驱动地址为:{}", output.getText());
                    MyConfig.createOrUpdateConfig(config, JarUtil.PWC_JAR_PATH);
                }
            }
        });
        resultLimitCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultLimitCheck.isSelected()) {
                    resultLimitChoose.insertItemAt(GuiParam.RESULT_UN_LIMIT, 0);
                    resultLimitChoose.setSelectedIndex(0);
                    resultLimitChoose.setEnabled(false);
                } else {
                    resultLimitChoose.removeItem(GuiParam.RESULT_UN_LIMIT);
                    resultLimitChoose.setEnabled(true);
                }
            }
        });
        outputPathChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser outputFileChooser = new JFileChooser();
                outputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (outputFileChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                    outputPath.setText(outputFileChooser.getSelectedFile().getAbsolutePath());
                    log.info("设置的输出目录为:{}", outputPath.getText());
                }
            }
        });
        configPathChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser configPathChooser = new JFileChooser();
                configPathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (configPathChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                    configPath.setText(configPathChooser.getSelectedFile().getAbsolutePath());
                    log.info("选择的配置文件目录为:{}", configPath.getText());
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("您请求终止爬虫，将在本次爬虫结束后终止！");
                processor.runnable = false;
                acmResultProcessor.runnable = false;
            }
        });
    }

    /**
     * 根据BaseResult的save2File方法，将爬去的文献信息保存到文件中
     * 本方法会判断result是否有效
     *
     * @param result 需要被保存的result
     */
    private void saveResult(BaseResult result) {
        if (saveResult2Csv.isSelected() && ObjectUtil.isNotNull(result) && CollUtil.isNotEmpty(Objects.requireNonNull(result).getPaperList())) {
            String outputPathStr = outputPath.getText();
            if (!StrUtil.endWith(outputPathStr, StrUtil.SLASH)) {
                outputPathStr += "/";
            }
            result.setCsvResultPath(outputPathStr);
            log.info("存储到csv中，地址为{}", result.getCsvResultPath());
            saveResult2Csv(result);
        } else {
            if (!saveResult2Csv.isSelected()) {
                log.info("未选择输出到文件，跳过输出");
            }
            if (ObjectUtil.isNull(result) || CollUtil.isEmpty(Objects.requireNonNull(result).getPaperList())) {
                log.info("爬虫启动错误，请检查网络链接是否正常");
            }
        }
    }

    private BaseResult loadPaperInfoDataTable(PaperWebSiteEnum paperWebSite) {
        switch (paperWebSite) {
            case ACM:
                return loadPaperInfoData2TableInAcm();
            case IEEE_XPLORE:
                return loadPaperInfoData2TableInIeee();
            default:
                return null;
        }
    }

    private BaseResult loadPaperInfoData2TableInIeee() {
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery(searchQueryInput.getText());
        IeeeResult ieeeResult;
        if (Objects.equals(resultLimitChoose.getSelectedItem(), GuiParam.RESULT_UN_LIMIT)) {
            ieeeResult = processor.run(ieeeSearchQuery, logArea, GuiParam.RESULT_UN_LIMIT_NUM);
        } else {
            ieeeResult = processor.run(ieeeSearchQuery, logArea, Integer.parseInt(String.valueOf(resultLimitChoose.getSelectedItem())));
        }
        return getPaperInfoFromLoveScience(ieeeResult);
    }

    private BaseResult getPaperInfoFromLoveScience(BaseResult baseResult) {
        if (ObjectUtil.isNotNull(baseResult) && ArrayUtil.isNotEmpty(baseResult.getPaperList())) {
            LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
            BaseResult result = loveScienceDetector.detector(baseResult, logArea);
            paperInfoGui.start(new HashMap<String, Object>(16) {
                {
                    put(BaseResult.class.getSimpleName(), result);
                }
            });
            log.info("该批次文献信息已完成【爱科学】处理");
            return result;
        } else {
            return null;
        }
    }

    private BaseResult loadPaperInfoData2TableInAcm() {
        AcmSearchQuery acmSearchQuery = new AcmSearchQuery(searchQueryInput.getText());
        AcmResult acmResult;
        if (Objects.equals(resultLimitChoose.getSelectedItem(), GuiParam.RESULT_UN_LIMIT)) {
            acmResult = acmResultProcessor.run(acmSearchQuery, logArea, GuiParam.RESULT_UN_LIMIT_NUM);
        } else {
            acmResult = acmResultProcessor.run(acmSearchQuery, logArea, Integer.parseInt(String.valueOf(resultLimitChoose.getSelectedItem())));
        }
        return getPaperInfoFromLoveScience(acmResult);
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
    private JTextField output;
    private JButton driverChoseButton;
    private JLabel driverChoseLabel;
    private JLabel resultLimit;
    private JComboBox<Object> resultLimitChoose;
    private JCheckBox resultLimitCheck;
    private JTextField outputPath;
    private JButton outputPathChooserButton;
    private JLabel outputPathLabel;
    private JTextField configPath;
    private JButton configPathChooserButton;
    private JLabel configPathLabel;
    private JLabel optionLabel;
    private JButton stopButton;
    private JLabel webSiteLabel;
    private JCheckBox ieeeChooseBox;
    private JCheckBox acmChooseBox;

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setPreferredSize(new Dimension(900, 500));
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$(null, Font.ITALIC, 20, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
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
        panel2.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("开始");
        panel2.add(searchButton, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveResult2Csv = new JCheckBox();
        saveResult2Csv.setSelected(true);
        saveResult2Csv.setText("是否保存到csv");
        panel2.add(saveResult2Csv, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        panel3.add(resultLimitChoose, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultLimitCheck = new JCheckBox();
        resultLimitCheck.setText("是否限制查询数量");
        panel3.add(resultLimitCheck, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultLimit = new JLabel();
        resultLimit.setText("查询数量");
        panel2.add(resultLimit, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        optionLabel = new JLabel();
        optionLabel.setText("选项");
        panel2.add(optionLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("终止");
        panel2.add(stopButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        webSiteLabel = new JLabel();
        webSiteLabel.setText("文献网站");
        panel2.add(webSiteLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ieeeChooseBox = new JCheckBox();
        ieeeChooseBox.setText("IEEE XPLORE");
        panel4.add(ieeeChooseBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        acmChooseBox = new JCheckBox();
        acmChooseBox.setText("ACM");
        panel4.add(acmChooseBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        helpButton = new JButton();
        helpButton.setText("关于帮助");
        helpButton.setMnemonic('帮');
        helpButton.setDisplayedMnemonicIndex(2);
        panel5.add(helpButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        output = new JTextField();
        output.setEditable(false);
        panel6.add(output, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        driverChoseButton = new JButton();
        driverChoseButton.setText("选择驱动");
        panel6.add(driverChoseButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        driverChoseLabel = new JLabel();
        driverChoseLabel.setText("浏览器驱动（目前仅支持chrome）");
        panel6.add(driverChoseLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputPath = new JTextField();
        outputPath.setEditable(false);
        panel6.add(outputPath, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        outputPathLabel = new JLabel();
        outputPathLabel.setText("输出目录");
        panel6.add(outputPathLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputPathChooserButton = new JButton();
        outputPathChooserButton.setText("选择输出目录");
        panel6.add(outputPathChooserButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configPathLabel = new JLabel();
        configPathLabel.setText("配置文件目录（选填）");
        panel6.add(configPathLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configPath = new JTextField();
        configPath.setEditable(false);
        panel6.add(configPath, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        configPathChooserButton = new JButton();
        configPathChooserButton.setText("选择配置文件目录");
        panel6.add(configPathChooserButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchQueryLabel = new JLabel();
        Font searchQueryLabelFont = this.$$$getFont$$$(null, Font.BOLD, 18, searchQueryLabel.getFont());
        if (searchQueryLabelFont != null) searchQueryLabel.setFont(searchQueryLabelFont);
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
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return main;
    }

    private void createUIComponents() {
        resultLimitChoose = new JComboBox<>(GuiParam.RESULT_LIMIT);
    }
}
