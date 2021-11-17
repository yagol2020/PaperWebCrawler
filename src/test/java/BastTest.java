import core.impl.LoveScienceDetector;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import result.BaseResult;
import result.IeeeResult;
import util.ChromeUtil;

import java.time.Duration;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:59 下午
 * @Description
 **/
public class BastTest {
    public void test() throws Exception {
        WebDriver webDriver = new ChromeUtil().initChrome();
        webDriver.get("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=smart%20contract%20bug&returnFacets=ALL&returnType=SEARCH&matchPubs=true&rowsPerPage=75&pageNumber=1");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
        webDriver.quit();
    }

    @Test
    public void testLoveScience() {
        IeeeResult ieeeResult = new IeeeResult();
        ieeeResult.addPaperInfo("test", Collections.singletonList("yagol"), "IEEE Transactions on Software Engineering", "2021", "Journal", "https://ieeexplore.ieee.org/document/9247056");
        LoveScienceDetector detector = new LoveScienceDetector();
        BaseResult result = detector.detector(ieeeResult);
        System.out.println(result);
    }

    @Test
    public void testRegexBracket() {
        String nameInput = "(start bracket) prefix text [Test Software] inner text (second) last text {last bracket} end";
        try {
            String pattern = "[\\(\\[\\{](.*?)[\\)\\]\\}]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(nameInput);
            int count = 0;
            while (m.find()) {
                System.out.println(count + ":" + m.group());
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegexNumber() {
        String input = "202211";
        String pattern = "[0-9]+";
        Pattern r = Pattern.compile(pattern);
        System.out.println(r.matcher(input).matches());
    }

    public void testIeeePaperIdXpath() {
        WebDriver webDriver = new ChromeUtil().initChrome();
        webDriver.get("https://ieeexplore.ieee.org/search/searchresult.jsp?queryText=smart%20contract%20bug&returnFacets=ALL&returnType=SEARCH&matchPubs=true&rowsPerPage=10&pageNumber=1");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
        webDriver.findElements(By.xpath("//xpl-search-results//xpl-results-list//div[@id>0]")).forEach(webElement -> {
            System.out.println(webElement.getText());
            System.out.println(webElement.getAttribute("id"));
            System.out.println("==========");
        });
        webDriver.quit();
    }
}
