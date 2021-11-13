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

        LoveScienceDetector loveScienceDetector = new LoveScienceDetector();
        ieeeResult.getPaperList().forEach(paperInfo -> {
            if (PaperTypeParam.CONFERENCE_PAPER.contains(paperInfo.getPaperType())) {
                paperInfo.setInfluenceFactor("会议论文");
            } else {
                String influenceFactor = loveScienceDetector.detector(paperInfo.getSource());
                paperInfo.setInfluenceFactor(influenceFactor);
            }
        });
        loveScienceDetector.quitWebDriver();
        ieeeResult.save2File();
    }
}
