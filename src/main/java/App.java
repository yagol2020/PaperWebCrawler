import bean.SearchQuery;
import core.IeeeResultProcessor;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:23 下午
 * @Description
 **/
public class App {
    public static void main(String[] args) {
        SearchQuery searchQuery = new SearchQuery("smart contract analysis", 75, 1);
        IeeeResultProcessor processor = new IeeeResultProcessor();
        processor.run(searchQuery);
    }
}
