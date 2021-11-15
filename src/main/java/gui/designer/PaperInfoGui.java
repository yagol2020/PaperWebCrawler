package gui.designer;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import result.BaseResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.HashMap;

/**
 * @author yagol
 * @TIME 2021/11/15 - 1:50 下午
 * @Description
 **/
public class PaperInfoGui implements BaseGui {
    private final Log log = LogFactory.get();
    private JPanel paperInfo;
    private JTable paperInfoTable;
    private JLabel searchInputLabel;
    private JLabel searchLabel;
    private JScrollPane jScrollPane;
    private JPanel upperPanel;
    private JFrame frame;

    @Override
    public void start(HashMap<String, Object> data) {
        DefaultTableModel tableModel = (DefaultTableModel) paperInfoTable.getModel();
        if (data.containsKey(BaseResult.class.getSimpleName())) {
            if (data.get(BaseResult.class.getSimpleName()) instanceof BaseResult) {
                BaseResult result = (BaseResult) data.get(BaseResult.class.getSimpleName());
                tableModel.setColumnIdentifiers(result.genHeader());
                result.genResults().forEach(tableModel::addRow);
                RowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
                paperInfoTable.setRowSorter(rowSorter);
                paperInfoTable.setModel(tableModel);
            }
        } else {
            log.info("文献表格初始化错误");
        }

    }

    @Override
    public PaperInfoGui init() {
        frame = new JFrame("PaperInfoGui");
        frame.setContentPane(paperInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        return this;
    }

    @Override
    public BaseGui show() {
        frame.setVisible(true);
        return this;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PaperInfoGui");
        frame.setContentPane(new PaperInfoGui().paperInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        paperInfoTable = new JTable();
        paperInfoTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
}
