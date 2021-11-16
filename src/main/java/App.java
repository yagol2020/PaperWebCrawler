import bean.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.impl.IeeeResultProcessor;
import core.impl.LoveScienceDetector;
import result.BaseResult;
import result.IeeeResult;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description
 **/
public class App {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        log.info("控制台级别的启动程序");
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery("NLP Model per");
        IeeeResultProcessor processor = new IeeeResultProcessor();
        IeeeResult ieeeResult = processor.run(ieeeSearchQuery);
        LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
        BaseResult result = loveScienceDetector.detector(ieeeResult);
        ieeeResult.save2File();
    }
}
