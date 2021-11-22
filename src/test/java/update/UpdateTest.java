package update;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import config.MyConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.ChromeUtil;
import util.JarUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/22 - 6:39 下午
 * @Description
 **/
public class UpdateTest {
    public static void main(String[] args) throws IOException {
        UpdateTest updateTest = new UpdateTest();
        updateTest.testGitHubReleases();
    }

    public void testGitHubReleases() throws IOException {
        MyConfig config = MyConfig.initConfig(JarUtil.PWC_JAR_PATH);
        String PER_GITHUB_RELEASES_URL = "https://github.com/yagol2020/PaperWebCrawler/releases/";
        WebDriver webDriver = new ChromeUtil().initChrome();
        webDriver.get(PER_GITHUB_RELEASES_URL);
        List<WebElement> list = webDriver.findElements(By.xpath("//div[@data-test-selector='release-card']//h1//a"));
        String newestPwcExeDownloadUrl = StrUtil.EMPTY;
        for (WebElement webElement : list) {
            String url = webElement.getAttribute("href").replace("tag", "download") + "/PWC.exe";
            if (url.contains("exe")) {
                newestPwcExeDownloadUrl = url;
                break;
            }
        }
        System.out.println(newestPwcExeDownloadUrl);
        String finalNewestPwcExeDownloadUrl = newestPwcExeDownloadUrl;
        int fileSize = new URL(finalNewestPwcExeDownloadUrl).openConnection().getContentLength();
        System.out.println("文件大小" + fileSize);
        HttpUtil.downloadFile(finalNewestPwcExeDownloadUrl, FileUtil.file("PWC.exe"), new StreamProgress() {
            @Override
            public void start() {
                System.out.println("开始下载");
            }

            @Override
            public void progress(long progressSize) {
                System.out.println("下载了" + progressSize);
            }

            @Override
            public void finish() {
                System.out.println("完成了");
            }
        });
        webDriver.quit();
    }
}
