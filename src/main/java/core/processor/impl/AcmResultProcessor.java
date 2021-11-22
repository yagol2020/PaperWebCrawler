package core.processor.impl;

import bean.result.AcmResult;
import bean.searchquery.impl.AcmSearchQuery;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import core.processor.PaperProcessor;
import log.MyLogFactory;
import log.MySwingTextAreaLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import param.AcmParam;
import util.ChromeUtil;
import util.MySeleniumUtil;

import javax.swing.*;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/21 - 7:12 下午
 * @Description
 **/
public class AcmResultProcessor implements PaperProcessor<AcmSearchQuery, AcmResult> {
    private final MySwingTextAreaLog log = MyLogFactory.get();
    public volatile Boolean runnable = true;

    @Override
    public AcmResult run(AcmSearchQuery searchQuery, Integer searchLimit) {
        WebDriver webDriver = new ChromeUtil().initChrome();
        AcmResult acmResult = new AcmResult();
        acmResult.setSearchQuery(searchQuery.getAllField());
        try {
            getAcmResult(searchQuery, acmResult, searchLimit, webDriver);
            int totalPage = acmResult.getPaperSize() / (searchQuery.getPageSize()) + 1;
            for (int i = searchQuery.getStartPage() + 1; i <= totalPage; i++) {
                //接收了外部指令，用户觉得该停止了
                if (!runnable) {
                    log.info("用户请求停止，结束本次爬虫");
                    break;
                }
                //如果已经爬取了足够数量，就不要再执行了
                if (acmResult.getPaperList().size() >= searchLimit) {
                    break;
                }
                searchQuery.setStartPage(i);
                getAcmResult(searchQuery, acmResult, searchLimit, webDriver);
            }
        } catch (Exception e) {
            log.info("建立链接时出现异常！请检查您的网络链接是否正常！爬虫的地址为{}", AcmParam.BASE_SEARCH_URL + searchQuery.gen());
            e.printStackTrace();
        }
        webDriver.quit();
        return acmResult;
    }

    @Override
    public AcmResult run(AcmSearchQuery searchQuery, JTextArea jTextArea, Integer searchLimit) {
        log.setLogTextArea(jTextArea);
        return run(searchQuery, searchLimit);
    }

    private void getAcmResult(AcmSearchQuery acmSearchQuery, AcmResult acmResult, Integer searchLimit, WebDriver webDriver) {
        if (acmResult.getPaperList().size() >= searchLimit) {
            return;
        }
        String webUrl = AcmParam.BASE_SEARCH_URL + acmSearchQuery.gen();
        log.info("准备提取的网站是：{}", webUrl);
        webDriver.get(webUrl);
        acmResult.parserTotalSize(webDriver.findElement(By.xpath(AcmParam.TOTAL_PAPER_SIZE_XPATH)).getText());
        List<WebElement> elements = webDriver.findElements(By.xpath(AcmParam.PAPER_INFO_PER_XPATH));
        for (WebElement webElement : elements) {
            log.info(webElement.getText());
            String title = MySeleniumUtil.findElementByXpath(webElement, AcmParam.TITLE_XPATH);
            String authors = MySeleniumUtil.findElementByXpath(webElement, AcmParam.AUTHORS_XPATH);
            String source = MySeleniumUtil.findElementByXpath(webElement, AcmParam.SOURCE_XPATH);
            String paperType = MySeleniumUtil.findElementByXpath(webElement, AcmParam.PAPER_TYPE_XPATH);
            String publishYear = "N/A";
            List<String> publishData = StrUtil.splitTrim(MySeleniumUtil.findElementByXpath(webElement, AcmParam.PUBLISH_DATA_XPATH), StrUtil.SPACE);
            if (publishData.size() == 2) {
                if (NumberUtil.isInteger(publishData.get(1))) {
                    publishYear = publishData.get(1);
                }
            }
            String paperUrl = webElement.findElement(By.xpath(AcmParam.TITLE_XPATH + "//a")).getAttribute("href");
            acmResult.addPaperInfo(title, source, StrUtil.splitTrim(authors, StrUtil.COMMA), publishYear, paperType, paperUrl);
            if (acmResult.getPaperList().size() >= searchLimit) {
                break;
            }
        }
    }
}
