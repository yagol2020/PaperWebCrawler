import bean.impl.IeeeSearchQuery;
import core.impl.IeeeResultProcessor;
import result.IeeeResult;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description
 **/
public class App {
    public static void main(String[] args) {
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery("NLP Model Parameter");
        IeeeResultProcessor processor = new IeeeResultProcessor();
        IeeeResult ieeeResult = processor.run(ieeeSearchQuery);
        ieeeResult.save2File();
    }
}
