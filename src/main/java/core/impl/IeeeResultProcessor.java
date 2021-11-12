package core.impl;

import bean.impl.IeeeSearchQuery;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.PaperProcessor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import result.IeeeResult;
import util.ChromeUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yagol
 * @TIME 2021/11/10 - 8:23 下午
 * @Description
 **/
public class IeeeResultProcessor implements PaperProcessor<IeeeSearchQuery, IeeeResult> {
    private static final Log log = LogFactory.get();

    @Override
    public IeeeResult run(IeeeSearchQuery ieeeSearchQuery) {
        WebDriver webDriver = new ChromeUtil().initChrome();
        IeeeResult ieeeResult = new IeeeResult();
        ieeeResult.setSearchQuery(ieeeSearchQuery.getQueryText());
        try {
            //第一轮需要外部执行，这一轮中，获得总共的论文数量
            getIeeeResult(ieeeSearchQuery, webDriver, ieeeResult);
            //计算总共有多少页，循环更新论文信息
            int totalPage = ieeeResult.getPaperSize() / (ieeeSearchQuery.getRowsPerPage()) + 1;
            for (int i = ieeeSearchQuery.getPageNumber() + 1; i <= totalPage; i++) {
                ieeeSearchQuery.setPageNumber(i);
                getIeeeResult(ieeeSearchQuery, webDriver, ieeeResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        webDriver.quit();
        return ieeeResult;
    }

    private void getIeeeResult(IeeeSearchQuery ieeeSearchQuery, WebDriver webDriver, IeeeResult ieeeResult) {
        String webUri = IeeeParam.BASE_SEARCH_URL + ieeeSearchQuery.gen();
        log.info("准备提取的网站是：{}", webUri);
        webDriver.get(webUri);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));

        ieeeResult.parserTotalSize(webDriver.findElement(By.xpath(IeeeParam.PAPER_SIZE_XPATH)).getText());

        webDriver.findElements(By.xpath("//xpl-search-results//xpl-results-list//*[@id>0]")).forEach(webElement -> {
            log.info(webElement.getText());
            List<String> webPaperInfos = StrUtil.split(webElement.getText(), "\n");
            String title = webPaperInfos.get(0);
            String year = StrUtil.EMPTY;
            String paperType = StrUtil.EMPTY;
            String source = StrUtil.EMPTY;
            List<String> authors = new ArrayList<>();
            int infoIndex = 0;
            for (int i = webPaperInfos.size() - 1; i >= 1; i--) {
                String content = webPaperInfos.get(i);
                if (content.startsWith("Year")) {
                    year = StrUtil.splitTrim(content, "|").get(0).replace("Year:", "").trim();
                    paperType = StrUtil.splitTrim(content, "|").get(1);
                    infoIndex = i;
                } else {
                    if (infoIndex != 0) {
                        if (source.equals(StrUtil.EMPTY)) {
                            source = content;
                        } else {
                            authors = StrUtil.splitTrim(content, ";");
                        }
                    }
                }
            }
            ieeeResult.updatePaperInfo(title, authors, source, year, paperType);
        });
    }

}
