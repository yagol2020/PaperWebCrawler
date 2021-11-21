package core.impl;

import bean.searchquery.impl.IeeeSearchQuery;
import cn.hutool.core.util.StrUtil;
import core.PaperProcessor;
import log.MyLogFactory;
import log.MySwingTextAreaLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import bean.result.IeeeResult;
import util.ChromeUtil;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yagol
 * @TIME 2021/11/10 - 8:23 下午
 * @Description
 **/
public class IeeeResultProcessor implements PaperProcessor<IeeeSearchQuery, IeeeResult> {
    private final MySwingTextAreaLog log = MyLogFactory.get();
    public volatile Boolean runnable = true;

    @Override
    public IeeeResult run(IeeeSearchQuery ieeeSearchQuery, Integer searchLimit) {
        WebDriver webDriver = new ChromeUtil().initChrome();
        IeeeResult ieeeResult = new IeeeResult();
        ieeeResult.setSearchQuery(ieeeSearchQuery.getQueryText());
        try {
            //第一轮需要外部执行，这一轮中，获得总共的论文数量
            getIeeeResult(ieeeSearchQuery, webDriver, ieeeResult, searchLimit);
            //计算总共有多少页，循环更新论文信息
            int totalPage = ieeeResult.getPaperSize() / (ieeeSearchQuery.getRowsPerPage()) + 1;
            for (int i = ieeeSearchQuery.getPageNumber() + 1; i <= totalPage; i++) {
                //接收了外部指令，用户觉得该停止了
                if (!runnable) {
                    log.info("用户请求停止，结束本次爬虫");
                    break;
                }
                //如果已经爬取了足够数量，就不要再执行了
                if (ieeeResult.getPaperList().size() >= searchLimit) {
                    break;
                }
                ieeeSearchQuery.setPageNumber(i);
                getIeeeResult(ieeeSearchQuery, webDriver, ieeeResult, searchLimit);
            }
        } catch (Exception e) {
            log.error("建立链接时出现异常！请检查您的网络链接是否正常！爬虫的地址为{}", IeeeParam.BASE_SEARCH_URL + ieeeSearchQuery.gen());
            e.printStackTrace();
        }
        webDriver.quit();
        return ieeeResult;
    }

    @Override
    public IeeeResult run(IeeeSearchQuery searchQuery, JTextArea jTextArea, Integer searchLimit) {
        log.setLogTextArea(jTextArea);
        return run(searchQuery, searchLimit);
    }

    private void getIeeeResult(IeeeSearchQuery ieeeSearchQuery, WebDriver webDriver, IeeeResult ieeeResult, Integer searchLimit) {
        //如果已经爬取了足够数量，就不要再执行了
        if (ieeeResult.getPaperList().size() >= searchLimit) {
            return;
        }
        String webUri = IeeeParam.BASE_SEARCH_URL + ieeeSearchQuery.gen();
        log.info("准备提取的网站是：{}", webUri);
        webDriver.get(webUri);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
        ieeeResult.parserTotalSize(webDriver.findElement(By.xpath(IeeeParam.PAPER_SIZE_XPATH)).getText());
        for (WebElement webElement : webDriver.findElements(By.xpath(IeeeParam.IEEE_PAPER_XPATH))) {
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
            String paperUrl = IeeeParam.PAPER_URL_PREFIX + webElement.getAttribute(IeeeParam.PAPER_ID_ATTRIBUTE);
            ieeeResult.addPaperInfo(title, authors, source, year, paperType, paperUrl);
            //循环过程中如果够数量了，就不要再执行了
            if (ieeeResult.getPaperList().size() >= searchLimit) {
                break;
            }
        }
    }

}
