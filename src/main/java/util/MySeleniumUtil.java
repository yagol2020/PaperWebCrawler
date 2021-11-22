package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author yagol
 * @TIME 2021/11/22 - 11:01 上午
 * @Description
 **/
public class MySeleniumUtil {
    public static String findElementByXpath(WebElement element, String xPath) {
        try {
            return element.findElement(By.xpath(xPath)).getText();
        } catch (Exception e) {
            return "N/A";
        }
    }
}
