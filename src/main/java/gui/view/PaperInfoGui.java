package gui.view;

import bean.analysis.CountDataPerYear;
import bean.result.AcmResult;
import bean.result.BaseResult;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import gui.bean.ChartData;
import gui.util.ResultSummer4Plot;
import param.PaperWebSiteEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 1:50 下午
 * @description
 **/
public class PaperInfoGui implements BaseGui {
    private final Log log = LogFactory.get();
    private JPanel paperInfo;
    private JTable paperInfoTable;
    private JLabel searchInputLabel;
    private JLabel searchLabel;
    private JScrollPane jScrollPane;
    private JPanel upperPanel;
    private JButton showChartButton;
    private JFrame frame;
    private final ChartGui chartGui = new ChartGui().init();
    private BaseResult baseResult;
    /**
     * 这个用于连续保存爬虫数据，从而将多个数据源的数据结合起来
     * key为文献平台名称，value是该平台的爬虫数据
     */
    private HashMap<PaperWebSiteEnum, BaseResult> sumBaseResult = new HashMap<>(16);

    public PaperInfoGui() {
        initComponentFunctions();
    }

    public void cleanTable() {
        DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
        //清空table的内容
        tableModel.getDataVector().clear();
        //更新table
        tableModel.fireTableDataChanged();
        //清空上一次保存的数据
        sumBaseResult = new HashMap<>(16);
    }

    @Override
    public void start(HashMap<String, Object> data) {
        DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
        if (data.containsKey(BaseResult.class.getSimpleName())) {
            if (data.get(BaseResult.class.getSimpleName()) instanceof BaseResult) {
                BaseResult result = (BaseResult) data.get(BaseResult.class.getSimpleName());
                //将数据保存到本地class中
                baseResult = result;
                //将平台数据集成起来，用于绘制图
                PaperWebSiteEnum paperWebSite = (PaperWebSiteEnum) data.get(PaperWebSiteEnum.class.getSimpleName());
                sumBaseResult.put(paperWebSite, result);
                tableModel.setColumnIdentifiers(result.genHeader());
                result.genResults().forEach(tableModel::addRow);
                RowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
                paperInfoTable.setRowSorter(rowSorter);
                paperInfoTable.setModel(tableModel);
                searchInputLabel.setText(result.getSearchQuery());
            }
        } else {
            log.info("文献表格初始化错误");
        }

    }

    @Override
    public PaperInfoGui init() {
        frame = new JFrame("PaperInfoGui");
        frame.setContentPane(paperInfo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        return this;
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void initComponentFunctions() {
        showChartButton.addActionListener(e -> {
            //将数据传递给绘图界面
            ChartData chartData = ResultSummer4Plot.genData4Plot(sumBaseResult);
            //顺手初始化了图标
            chartGui.start(new HashMap<String, Object>(16) {
                {
                    put(ChartData.class.getSimpleName(), chartData);
                }
            });
            chartGui.show();
        });

    }


    private void createUIComponents() {
        paperInfoTable = new JTable();
        paperInfoTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

}
