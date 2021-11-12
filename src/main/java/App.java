import bean.impl.IeeeSearchQuery;
import core.impl.IeeeResultProcessor;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description
 **/
public class App {
    public static void main(String[] args) {
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery("smart contract bug");
        IeeeResultProcessor processor = new IeeeResultProcessor();
        processor.run(ieeeSearchQuery);
    }
}
