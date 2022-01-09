package gui.view;

import bean.analysis.CountDataPerYear;
import bean.result.BaseResult;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gui.bean.ChartData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
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
    private final ChartDGui chartGui = new ChartDGui().init();
    private BaseResult baseResult;

    public PaperInfoGui() {
        initComponentFunctions();
    }

    public void cleanTable() {
        DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
        //清空table的内容
        tableModel.getDataVector().clear();
        //更新table
        tableModel.fireTableDataChanged();
    }

    @Override
    public void start(HashMap<String, Object> data) {
        DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
        if (data.containsKey(BaseResult.class.getSimpleName())) {
            if (data.get(BaseResult.class.getSimpleName()) instanceof BaseResult) {
                BaseResult result = (BaseResult) data.get(BaseResult.class.getSimpleName());
                //将数据保存到本地class中
                baseResult = result;
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
            ChartData chartData = new ChartData();
            chartData.setWebsiteInfos(new LinkedHashMap<>());
            chartData.setPaperInfo(new HashMap<String, Object>(16) {
                {
                    put(CountDataPerYear.class.getSimpleName(), new CountDataPerYear().getDataByBaseResult(baseResult));
                }
            });
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
