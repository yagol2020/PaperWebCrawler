package core.impl;

import core.PaperProcessor;
import result.IeeeResult;
import bean.impl.IeeeSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import util.ChromeUtil;
import util.MyFileUtil;

import java.time.Duration;


/**
 * @author yagol
 * @TIME 2021/11/10 - 8:23 下午
 * @Description
 **/
public class IeeeResultProcessor implements PaperProcessor<IeeeSearchQuery> {
    private static final Log log = LogFactory.get();

    @Override
    public void run(IeeeSearchQuery ieeeSearchQuery) {
        WebDriver webDriver = new ChromeUtil().initChrome();
        try {
            webDriver.get(IeeeParam.BASE_SEARCH_URL + ieeeSearchQuery.gen());
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
            IeeeResult ieeeResult = new IeeeResult();
            ieeeResult.parserTotalSize(webDriver.findElement(By.xpath(IeeeParam.PAPER_SIZE_XPATH)).getText());
            if (ieeeResult.getPaperSize() > ieeeSearchQuery.getSearchSize()) {

            }
            MyFileUtil.write("src/main/output/ieee/result.txt", ieeeResult.genResultStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }

}
