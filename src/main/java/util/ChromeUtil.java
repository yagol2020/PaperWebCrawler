package util;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author yagol
 * @TIME 2021/11/11 - 4:24 下午
 * @Description
 **/
public class ChromeUtil {
    static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    static final String CHROME_DRIVER_PATH = "src/main/resources/chromedriver";
    static ChromeOptions CHROME_OPTIONS;

    public ChromeUtil() {
        System.setProperty(CHROME_DRIVER_PROPERTY, CHROME_DRIVER_PATH);
        CHROME_OPTIONS = new ChromeOptions();
        CHROME_OPTIONS.setPageLoadStrategy(PageLoadStrategy.EAGER);
        //隐藏浏览器不显示
        CHROME_OPTIONS.addArguments("--headless");
    }

    public WebDriver initChrome() {
        return new ChromeDriver(CHROME_OPTIONS);
    }
}
