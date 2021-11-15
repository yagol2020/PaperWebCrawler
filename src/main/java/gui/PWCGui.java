package gui;

import bean.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.impl.IeeeResultProcessor;
import core.impl.LoveScienceDetector;
import gui.param.GuiParam;
import param.NormalParam;
import result.BaseResult;
import result.IeeeResult;
import util.MyFileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * PWC Gui
 *
 * @author yagol
 */
public class PWCGui {
    private final Log log = LogFactory.get();
    private final JFrame mainFrame = new JFrame("Paper Web Crawler 文献网站爬虫工具");
    private final Container mainContainer = mainFrame.getContentPane();
    private final JLabel searchQueryLabel = new JLabel("关键字");
    private final JTextField searchQueryInput = new JTextField();
    private final JButton searchButton = new JButton("开始");
    private final JButton helpButton = new JButton("关于&使用说明");
    private final JTextArea logTextArea = new JTextArea();

    private final JFrame helpFrame = new JFrame("PWC 帮助");
    private final Container helpContainer = helpFrame.getContentPane();
    private final JTextArea helpContent = new JTextArea(5, 20);
    private final JButton closeHelpButton = new JButton("关闭");

    private final JFrame paperInfoFrame = new JFrame("搜索结果");
    private final Container paperInfoContainer = paperInfoFrame.getContentPane();
    private final JTable paperInfoTable = new JTable();


    public PWCGui() {
        mainFrame.setBounds(600, 200, 400, 220);
        mainContainer.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        helpFrame.setBounds(700, 100, 300, 220);
        helpContainer.setLayout(new BorderLayout());
        helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        paperInfoFrame.setBounds(300, 300, 700, 700);
        paperInfoFrame.setLayout(new BorderLayout());
        paperInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        init();
        setButtonFunction();
        mainFrame.setVisible(true);
    }

    private void init() {
        JPanel authorInfo = new JPanel();
        authorInfo.setLayout(new FlowLayout());
        authorInfo.add(new JLabel("author : @yagol, @vencerk"));
        mainContainer.add(authorInfo, "North");

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        searchQueryLabel.setBounds(50, 20, 50, 20);
        fieldPanel.add(searchQueryLabel);
        searchQueryInput.setBounds(110, 20, 220, 20);
        fieldPanel.add(searchQueryInput);
        mainContainer.add(fieldPanel, "Center");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(searchButton);
        buttonPanel.add(helpButton);
        mainContainer.add(buttonPanel, "South");

        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        helpContent.setText(MyFileUtil.readFile(NormalParam.HELP_FILE_PATH));
        helpContent.setLineWrap(true);
        helpPanel.add(helpContent);
        helpPanel.add(closeHelpButton);
        helpContainer.add(helpPanel, "Center");

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new FlowLayout());
        helpPanel.add(logTextArea);
        mainContainer.add(logPanel, "East");

        JScrollPane paperInfoPanel = new JScrollPane();
        paperInfoTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        paperInfoPanel.setViewportView(paperInfoTable);
        paperInfoContainer.add(paperInfoPanel, "Center");
    }

    private void setButtonFunction() {
        helpButton.addActionListener(e -> helpFrame.setVisible(true));
        closeHelpButton.addActionListener(e -> helpFrame.setVisible(false));
        searchButton.addActionListener(e -> {
            IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery(searchQueryInput.getText());
            IeeeResultProcessor processor = new IeeeResultProcessor();
            IeeeResult ieeeResult = processor.run(ieeeSearchQuery);
            LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
            BaseResult result = loveScienceDetector.detector(ieeeResult);
            DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
            tableModel.setColumnIdentifiers(result.genHeader());
            result.genResults().forEach(tableModel::addRow);
            RowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
            paperInfoTable.setRowSorter(rowSorter);
            paperInfoTable.setModel(tableModel);
            paperInfoFrame.setVisible(true);
            log.info("论文信息窗口渲染完毕");
        });

        searchQueryInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == GuiParam.ENTER_CODE) {
                    searchButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public static void main(String[] args) {
        new PWCGui();
    }
}
