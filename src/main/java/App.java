import bean.searchquery.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.processor.impl.IeeeResultProcessor;
import core.detector.impl.LoveScienceDetector;
import bean.result.BaseResult;
import bean.result.IeeeResult;
import gui.view.MainGui;

import javax.swing.*;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description 主程序gui
 **/
public class App {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGui");
        frame.setContentPane(new MainGui().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
