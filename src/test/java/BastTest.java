import cn.hutool.core.util.StrUtil;
import core.impl.LoveScienceDetector;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import param.IeeeParam;
import util.ChromeUtil;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:59 下午
 * @Description
 **/
public class BastTest {
    @Test
    public void test() {
        WebDriver webDriver = new ChromeUtil().initChrome();
        webDriver.get("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=smart%20contract%20bug&returnFacets=ALL&returnType=SEARCH&matchPubs=true&rowsPerPage=75&pageNumber=1");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IeeeParam.UNTIL_CONDITION_XPATH)));
        webDriver.findElements(By.xpath("//xpl-search-results//xpl-results-list//*[@id>0]")).forEach(webElement -> {
            List<String> webPaperInfos = StrUtil.split(webElement.getText(), "\n");
            System.out.println("title: " + webPaperInfos.get(0));
            System.out.println("authors: " + webPaperInfos.get(1));
            System.out.println("source: " + webPaperInfos.get(2));
            System.out.println("year: " + StrUtil.splitTrim(webPaperInfos.get(3), "|").get(0));
            System.out.println("paper type: " + StrUtil.splitTrim(webPaperInfos.get(3), "|").get(1));
            System.out.println("=====================");
        });
        webDriver.quit();
    }

    @Test
    public void testLoveScience() {
        LoveScienceDetector detector = new LoveScienceDetector();
        String result = detector.detector("IEEE Transactions on Software Engineering");
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
}
