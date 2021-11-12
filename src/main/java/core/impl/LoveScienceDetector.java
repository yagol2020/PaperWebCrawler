package core.impl;

import bean.impl.LoveScienceSearchQuery;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.PaperLevelDetector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.LoveScienceParam;
import param.PaperLevelParam;
import util.ChromeUtil;

import java.time.Duration;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/12 - 7:09 下午
 * @Description 爱科学期刊等级检测器
 **/
public class LoveScienceDetector implements PaperLevelDetector {
    private static final Log log = LogFactory.get();
    private final WebDriver webDriver;

    public LoveScienceDetector() {
        webDriver = new ChromeUtil().initChrome();
    }

    public void quitWebDriver() {
        webDriver.quit();
    }

    @Override
    public String detector(String name) {
        String result = StrUtil.EMPTY;
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
}
