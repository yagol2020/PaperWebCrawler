import bean.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.impl.IeeeResultProcessor;
import core.impl.LoveScienceDetector;
import param.PaperTypeParam;
import result.IeeeResult;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description
 **/
public class App {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery("NLP Model Parameter");
        IeeeResultProcessor processor = new IeeeResultProcessor();
        IeeeResult ieeeResult = processor.run(ieeeSearchQuery);
        ieeeResult.save2File();
        LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
        ieeeResult.getPaperList().forEach(paperInfo -> {
            if (PaperTypeParam.CONFERENCE_PAPER.contains(paperInfo.getPaperType())) {
                log.info("是会议，暂时跳过");
            } else {
                System.out.println(loveScienceDetector.detector(paperInfo.getSource()));
            }
        });
        loveScienceDetector.quitWebDriver();
    }
}
