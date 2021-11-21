package xpath;

import bean.result.BaseResult;
import bean.searchquery.impl.AcmSearchQuery;
import core.processor.impl.AcmResultProcessor;
import org.junit.Test;

/**
 * @author yagol
 * @TIME 2021/11/21 - 7:24 下午
 * @Description
 **/
public class AcmXPathTest {
    @Test
    public void testSearchResult() {
        AcmSearchQuery acmSearchQuery = new AcmSearchQuery("smart contract bug");
        AcmResultProcessor acmResultProcessor = new AcmResultProcessor();
        BaseResult baseResult = acmResultProcessor.run(acmSearchQuery, 50);
        System.out.println(baseResult.getPaperSize());

    }
}
