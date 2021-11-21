package core.impl;

import bean.searchquery.impl.LoveScienceSearchQuery;
import cn.hutool.core.util.StrUtil;
import core.PaperLevelDetector;
import log.MyLogFactory;
import log.MySwingTextAreaLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.LoveScienceParam;
import param.NormalParam;
import param.PaperLevelParam;
import param.PaperTypeParam;
import bean.result.BaseResult;
import util.ChromeUtil;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/12 - 7:09 下午
 * @Description 爱科学期刊等级检测器
 **/
public class LoveScienceDetector implements PaperLevelDetector {
    private final MySwingTextAreaLog log = MyLogFactory.get();
    private final WebDriver webDriver;

    public LoveScienceDetector() {
        webDriver = new ChromeUtil().initChrome();
    }

    private void quitWebDriver() {
        webDriver.quit();
    }

    /**
     * 去除期刊名称中的无用信息
     *
     * @param name 期刊名称
     * @return 去除后的期刊名称
     */
    private String simplifyName(String name) {
        List<String> bracketContentList = NormalParam.getBracketContentList(name);
        for (String s : bracketContentList) {
            name = name.replace(s, StrUtil.EMPTY);
        }
        if (name.length() >= LoveScienceParam.MAX_QUERY_LENGTH) {
            List<String> splitList = StrUtil.splitTrim(name, StrUtil.SPACE);
            StringBuilder sb = new StringBuilder();
            for (String s : splitList) {
                if (!s.contains(NormalParam.ORDINAL_WORD_TH) && !NormalParam.NUMBER_PATTERN.matcher(s).matches()
                        && !s.contains(NormalParam.APOSTROPHE) &&
                        !s.contains(NormalParam.TRANSACTIONS)) {
                    sb.append(s);
                    sb.append(StrUtil.SPACE);
                }
            }
            if (sb.length() >= LoveScienceParam.MAX_QUERY_LENGTH) {
                sb.delete(0, sb.length() - LoveScienceParam.MAX_QUERY_LENGTH);
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    private String detector(String name) {
        String result = StrUtil.EMPTY;
        name = simplifyName(name);
        LoveScienceSearchQuery loveScienceSearchQuery = new LoveScienceSearchQuery();
        loveScienceSearchQuery.setTitle(name);
        String webUrl = LoveScienceParam.BASE_SEARCH_URL + loveScienceSearchQuery.gen();
        log.info(webUrl);
        try {
            webDriver.get(webUrl);
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoveScienceParam.UNTIL_CONDITION_XPATH)));
            List<WebElement> sciInfos = webDriver.findElements(By.xpath(LoveScienceParam.SCI_INFO_TABLE_XPATH));
            List<WebElement> influenceFactorInfos = webDriver.findElements(By.xpath(LoveScienceParam.INFLUENCE_FACTOR_XPATH));
            if (sciInfos.size() == 1 && influenceFactorInfos.size() == 1) {
                log.info("查找到唯一期刊，原始信息为：{}", sciInfos.get(0).getText());
                result = influenceFactorInfos.get(0).getText();
            } else {
                if (sciInfos.size() == 0) {
                    log.info("该期刊没有信息，被查找的期刊名称为：{}", name);
                    result = PaperLevelParam.THREE_NO_JOURNAL;
                } else {
                    log.info("该期刊查找到复数信息，被查找的期刊名称为：{}", name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public BaseResult detector(BaseResult result) {
        result.getPaperList().forEach(paperInfo -> {
            if (PaperTypeParam.CONFERENCE_PAPER.contains(paperInfo.getPaperType())) {
                paperInfo.setInfluenceFactor(paperInfo.getPaperType());
            } else {
                paperInfo.setInfluenceFactor(this.detector(paperInfo.getSource()));
            }
        });
        this.quitWebDriver();
        return result;
    }

    @Override
    public BaseResult detector(BaseResult result, JTextArea jTextArea) {
        log.setLogTextArea(jTextArea);
        return this.detector(result);
    }
}
