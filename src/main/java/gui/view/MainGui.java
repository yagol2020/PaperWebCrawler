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
    public JPanel main;

    private final PaperInfoGui paperInfoGui = new PaperInfoGui().init();
    private final HelpGui helpGui = new HelpGui().init();
    private final UpdateGui updateGui = new UpdateGui().init();

    IeeeResultProcessor processor = new IeeeResultProcessor();
    AcmResultProcessor acmResultProcessor = new AcmResultProcessor();


    public MainGui() {
        log.setLogTextArea(logArea);
        initComponent();
        initComponentFunctions();
        log.info("检测到执行路径为{}", JarUtil.PWC_JAR_PATH);
    }

    private void initComponent() {
        if (StrUtil.isNotEmpty(config.getChrome().getDriverPath())) {
            output.setText(config.getChrome().getDriverPath());
        }
        versionLabel.setText("版本号：" + config.getVersion());
    }


    private void saveResult2Csv(BaseResult result) {
        result.save2File();
    }

    private void initProgressBars() {
        ieeeProgressBar.setValue(0);
        acmProgressBar.setValue(0);
        ieeeDetectorProgressBar.setValue(0);
        acmDetectorProgressBar.setValue(0);
    }

    private void initComponentFunctions() {
        searchButton.addActionListener(e -> {
            //点击开始的时候，将进度条清空
            initProgressBars();
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
                if (!ieeeChooseBox.isSelected() && !acmChooseBox.isSelected()) {
                    log.info("请至少选择一个文献平台！");
                    return;
                }
                log.info("执行爬虫任务，请稍后。请勿再次点击\"开始\"按钮");
                ThreadUtil.execute(() -> {
                    showPaperInfoGuiButton.setEnabled(false);
                    searchButton.setEnabled(false);
                    //因为是新的关键字，所以清空表格中的数据
                    paperInfoGui.cleanTable();
                    processor.runnable = true;
                    acmResultProcessor.runnable = true;
                    //2代表有最大有几个文献查询网站，目前指IEEE和ACM
                    CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(2);
                    if (ieeeChooseBox.isSelected()) {
                        ThreadUtil.execute(() -> {
                            try {
                                BaseResult result = loadPaperInfoDataTable(PaperWebSiteEnum.IEEE_XPLORE);
                                //为了防止找到的文献数量不够限制数量，引起用户的误解，在完成爬虫后将进度条设为100
                                ieeeProgressBar.setValue(100);
                                saveResult(result);
                            } catch (Exception exception) {
                                log.info("IEEE XPLORE爬虫过程出现异常，异常信息为{}", exception.getMessage());
                            }
                            countDownLatch.countDown();
                        });
                    } else {
                        countDownLatch.countDown();
                    }
                    if (acmChooseBox.isSelected()) {
                        ThreadUtil.execute(() -> {
                            try {
                                BaseResult result = loadPaperInfoDataTable(PaperWebSiteEnum.ACM);
                                //为了防止找到的文献数量不够限制数量，引起用户的误解，在完成爬虫后将进度条设为100
                                acmProgressBar.setValue(100);
                                saveResult(result);
                            } catch (Exception exception) {
                                log.info("ACM爬虫过程出现异常，异常信息为{}", exception.getMessage());
                            }
                            countDownLatch.countDown();
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
                    showPaperInfoGuiButton.setEnabled(true);
                    searchButton.setEnabled(true);
                });
            } else {
                log.info("驱动未选定，请选定驱动地址！");
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
        helpButton.addActionListener(e -> helpGui.show());
        driverChoseButton.addActionListener(e -> {
            JFileChooser driverChooser = new JFileChooser();
            driverChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (driverChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                output.setText(driverChooser.getSelectedFile().getAbsolutePath());
                config.getChrome().setDriverPath(output.getText());
                log.info("设置驱动地址为:{}", output.getText());
                MyConfig.createOrUpdateConfig(config, JarUtil.PWC_JAR_PATH);
            }
        });
        resultLimitCheck.addActionListener(e -> {
            if (resultLimitCheck.isSelected()) {
                resultLimitChoose.insertItemAt(GuiParam.RESULT_UN_LIMIT, 0);
                resultLimitChoose.setSelectedIndex(0);
                resultLimitChoose.setEnabled(false);
            } else {
                resultLimitChoose.removeItem(GuiParam.RESULT_UN_LIMIT);
                resultLimitChoose.setEnabled(true);
            }
        });
        outputPathChooserButton.addActionListener(e -> {
            JFileChooser outputFileChooser = new JFileChooser();
            outputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (outputFileChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                outputPath.setText(outputFileChooser.getSelectedFile().getAbsolutePath());
                log.info("设置的输出目录为:{}", outputPath.getText());
            }
        });
        configPathChooserButton.addActionListener(e -> {
            JFileChooser configPathChooser = new JFileChooser();
            configPathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (configPathChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                configPath.setText(configPathChooser.getSelectedFile().getAbsolutePath());
                log.info("选择的配置文件目录为:{}", configPath.getText());
            }
        });
        stopButton.addActionListener(e -> {
            log.info("您请求终止爬虫，将在本次爬虫结束后终止！");
            processor.runnable = false;
            acmResultProcessor.runnable = false;
        });
        showPaperInfoGuiButton.setEnabled(false);
        showPaperInfoGuiButton.addActionListener(e -> paperInfoGui.show());
        checkUpdateButton.addActionListener(e -> updateGui.show());
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
                log.info("文件保存失败：爬虫结果为空，请检查！爬虫启动错误，请检查网络链接是否正常");
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
            ieeeResult = processor.run(ieeeSearchQuery, logArea, ieeeProgressBar, GuiParam.RESULT_UN_LIMIT_NUM);
        } else {
            ieeeResult = processor.run(ieeeSearchQuery, logArea, ieeeProgressBar, Integer.parseInt(String.valueOf(resultLimitChoose.getSelectedItem())));
        }
        return getPaperInfoFromLoveScience(ieeeResult, PaperWebSiteEnum.IEEE_XPLORE);
    }

    private BaseResult getPaperInfoFromLoveScience(BaseResult baseResult, PaperWebSiteEnum paperWebSite) {
        if (ObjectUtil.isNotNull(baseResult) && ArrayUtil.isNotEmpty(baseResult.getPaperList())) {
            LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
            BaseResult result = null;
            switch (paperWebSite) {
                case ACM:
                    result = loveScienceDetector.detector(baseResult, logArea, acmDetectorProgressBar);
                    break;
                case IEEE_XPLORE:
                    result = loveScienceDetector.detector(baseResult, logArea, ieeeDetectorProgressBar);
                    break;
                default:
                    break;
            }
            if (ObjectUtil.isNotNull(result)) {
                HashMap<String, Object> inputData = new HashMap<>(16);
                inputData.put(BaseResult.class.getSimpleName(), result);
                inputData.put(PaperWebSiteEnum.class.getSimpleName(), paperWebSite);
                //将爬虫的数据传递给表格
                paperInfoGui.start(inputData);
                log.info("该批次文献信息已完成【爱科学】处理");
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private BaseResult loadPaperInfoData2TableInAcm() {
        AcmSearchQuery acmSearchQuery = new AcmSearchQuery(searchQueryInput.getText());
        AcmResult acmResult;
        if (Objects.equals(resultLimitChoose.getSelectedItem(), GuiParam.RESULT_UN_LIMIT)) {
            acmResult = acmResultProcessor.run(acmSearchQuery,
                    logArea,
                    acmProgressBar,
                    GuiParam.RESULT_UN_LIMIT_NUM);
        } else {
            acmResult = acmResultProcessor.run(acmSearchQuery,
                    logArea,
                    acmProgressBar,
                    Integer.parseInt(String.valueOf(resultLimitChoose.getSelectedItem())));
        }
        return getPaperInfoFromLoveScience(acmResult, PaperWebSiteEnum.ACM);
    }

    private JPanel content;
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
    private JButton showPaperInfoGuiButton;
    private JCheckBox saveResult2Db;
    private JProgressBar ieeeProgressBar;
    private JLabel searchProgressLabel;
    private JProgressBar ieeeDetectorProgressBar;
    private JLabel ieeeDetectorLabel;
    private JProgressBar acmProgressBar;
    private JLabel acmProgressLabel;
    private JProgressBar acmDetectorProgressBar;
    private JLabel acmDetectorLabel;
    private JButton checkUpdateButton;
    private JLabel versionLabel;

    private void createUIComponents() {
        resultLimitChoose = new JComboBox<>(GuiParam.RESULT_LIMIT);
    }
}
