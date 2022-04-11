import bean.searchquery.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.processor.impl.IeeeResultProcessor;
import core.detector.impl.LoveScienceDetector;
import bean.result.BaseResult;
import bean.result.IeeeResult;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description 控制台级别启动，弃用
 **/
@Deprecated
public class App {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        System.out.println("控制台启动已经作废，请使用GUI启动本项目");
    }
}
