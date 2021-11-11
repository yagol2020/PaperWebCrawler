package core;

import bean.IeeeResult;
import bean.SearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import result.ResultHandler;
import util.ChromeUtil;

import java.time.Duration;


/**
 * @author yagol
 * @TIME 2021/11/10 - 8:23 下午
 * @Description
 **/
public class IeeeResultProcessor {
    private static final Log log = LogFactory.get();

    public void run(SearchQuery searchQuery) {
        WebDriver webDriver = new ChromeUtil().initChrome();
        try {
            webDriver.get(IeeeParam.BASE_SEARCH_URL + searchQuery.gen());
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
            IeeeResult ieeeResult = new IeeeResult();
            ieeeResult.parserTotalSize(webDriver.findElement(By.xpath(IeeeParam.PAPER_SIZE_XPATH)).getText());
            ResultHandler resultHandler = new ResultHandler();
            resultHandler.reWrite("src/main/output/ieee/result.txt", ieeeResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }
}
