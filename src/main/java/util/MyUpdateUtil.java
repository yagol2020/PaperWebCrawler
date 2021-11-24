package util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import config.MyConfig;
import param.NormalParam;

import javax.swing.*;
import java.net.URL;

/**
 * @author yagol
 * @TIME 2021/11/22 - 6:31 下午
 * @Description 检查PWC是否存在更新
 **/
public class MyUpdateUtil {
    static MyConfig config = MyConfig.getConfig(JarUtil.PWC_JAR_PATH);
    /**
     * 存放releases的json信息的原始数据地址，直接获得json文本
     */
    static final String GITHUB_RELEASES_JSON_FILE_URL = "https://raw.githubusercontent.com/yagol2020/PaperWebCrawler/master/releases.json";
    static final String NEWEST_RELEASES_JSON_KEY = "newest_releases";
    static final String EXE_DOWNLOAD_PATH_JSON_KEY = "exe_download_path";
    static final String VERSION_JSON_KEY = "version";
    static final String PER_GITHUB_RELEASES_URL = "https://github.com/yagol2020/PaperWebCrawler/releases/";
    static final String NEW_VERSION_SAVE_FILE_NAME = "PWC_NEW.exe";
    public static final String ALREADY_NEWEST_VERSION = "已为最新版";

    /**
     * 获得最新exe下载地址
     *
     * @return 最新exe的下载地址
     */
    private static String getNewestExeFileDownloadUrl() {
        String rowReleasesData = HttpUtil.get(GITHUB_RELEASES_JSON_FILE_URL);
        JSONObject jsonObject = JSONUtil.parseObj(rowReleasesData);
        double newestVersion = Double.parseDouble(String.valueOf(jsonObject.getByPath(NEWEST_RELEASES_JSON_KEY + StrUtil.DOT + VERSION_JSON_KEY)));
        double thisVersion = Double.parseDouble(config.getVersion());
        if (NumberUtil.compare(newestVersion, thisVersion) <= 0) {
            return ALREADY_NEWEST_VERSION;
        } else {
            return String.valueOf(jsonObject.getByPath(NEWEST_RELEASES_JSON_KEY + StrUtil.DOT + EXE_DOWNLOAD_PATH_JSON_KEY));
        }
    }

    public static String checkUpdate(JProgressBar jProgressBar, JTextArea logTextArea) throws Exception {
        String exePath = System.getProperty("exe.path");
        if (StrUtil.isEmpty(exePath)) {
            exePath = JarUtil.PWC_JAR_PATH;
        }
        if (exePath.contains(NormalParam.QUESTION_MARK)) {
            logTextArea.append(DateUtil.now() + StrUtil.SPACE + "不支持在非全中文目录下更新系统，请将本文件夹移动到英文目录下" + StrUtil.LF);
            throw new Exception("不支持非全英文目录更新");
        }
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "检测到exe运行环境为：" + exePath + StrUtil.LF);
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "正在获得GitHUb的更新信息..." + StrUtil.LF);
        String downloadUrl = getNewestExeFileDownloadUrl();
        if (StrUtil.equals(downloadUrl, ALREADY_NEWEST_VERSION)) {
            logTextArea.append(DateUtil.now() + StrUtil.SPACE + "当前版本已经是最新版，无需升级！" + StrUtil.LF);
            return ALREADY_NEWEST_VERSION;
        }
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "找到的更新文件为：" + downloadUrl + "\n");
        int fileSize = new URL(downloadUrl).openConnection().getContentLength();
        jProgressBar.setMaximum(fileSize);
        logTextArea.append(DateUtil.now() + StrUtil.SPACE + "待下载文件大小为：" + fileSize + "\n");
        String savePath = exePath + NEW_VERSION_SAVE_FILE_NAME;
        if (FileUtil.exist(savePath)) {
            logTextArea.append(DateUtil.now() + StrUtil.SPACE + "已存在PWC_NEW.exe，尝试将其删除" + "\n");
            try {
                FileUtil.del(savePath);
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "删除成功！" + "\n");
            } catch (IORuntimeException e) {
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "删除失败！可能是当前运行文件是PWC_NEW.exe" + "\n");
                savePath = exePath + RandomUtil.randomString(5) + StrUtil.UNDERLINE + NEW_VERSION_SAVE_FILE_NAME;
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "已将更新文件保存到" + savePath + "\n");
            }
        }
        HttpUtil.downloadFile(downloadUrl, FileUtil.file(savePath), new StreamProgress() {
            @Override
            public void start() {
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "开始下载" + "\n");
            }

            @Override
            public void progress(long progressSize) {
                jProgressBar.setValue((int) progressSize);
            }

            @Override
            public void finish() {
                logTextArea.append(DateUtil.now() + StrUtil.SPACE + "完成下载" + "\n");
            }
        });
        return savePath;
    }
}
