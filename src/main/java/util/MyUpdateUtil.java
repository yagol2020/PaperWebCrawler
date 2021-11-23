package util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/22 - 6:31 下午
 * @Description 检查PWC是否存在更新
 **/
public class MyUpdateUtil {
    static final String GITHUB_RELEASES_JSON_FILE_URL = "";
    static final String PER_GITHUB_RELEASES_URL = "https://github.com/yagol2020/PaperWebCrawler/releases/";

    public void checkUpdate(JProgressBar jProgressBar, JTextArea logTextArea) throws IOException {
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
        webDriver.quit();
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "找到的更新文件为：" + newestPwcExeDownloadUrl + "\n");
        String finalNewestPwcExeDownloadUrl = newestPwcExeDownloadUrl;
        int fileSize = new URL(finalNewestPwcExeDownloadUrl).openConnection().getContentLength();
        jProgressBar.setMaximum(fileSize);
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "待下载文件大小为：" + fileSize + "\n");
        HttpUtil.downloadFile(finalNewestPwcExeDownloadUrl, FileUtil.file("PWC.exe"), new StreamProgress() {
            @Override
            public void start() {
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "开始下载" + "\n");
            }

            @Override
            public void progress(long progressSize) {
                jProgressBar.setValue((int) progressSize);
                if (progressSize * 100 / fileSize >= 30) {
                    logTextArea.append(DateUtil.now() + StrUtil.SPACE + "已下载30%" + "\n");
                }
                if (progressSize * 100 / fileSize >= 50) {
                    logTextArea.append(DateUtil.now() + StrUtil.SPACE + "已下载50%" + "\n");
                }
                if (progressSize * 100 / fileSize >= 70) {
                    logTextArea.append(DateUtil.now() + StrUtil.SPACE + "已下载70%" + "\n");
                }
            }

            @Override
            public void finish() {
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "完成下载" + "\n");
            }
        });

    }
}
