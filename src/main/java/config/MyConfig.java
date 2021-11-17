package config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.yaml.YamlUtil;
import config.property.ChromeProperties;
import lombok.Data;
import param.NormalParam;

/**
 * @author yagol
 * @TIME 2021/11/17 - 7:16 下午
 * @Description 配置类获取类
 **/
@Data
public class MyConfig {
    ChromeProperties chrome;

    /**
     * 初始化config，并获得jar外部的config
     *
     * @param configSavePath
     * @return
     */
    public static MyConfig initConfig(String configSavePath) {
        MyConfig config = YamlUtil.loadByPath("config/" + NormalParam.CONFIG_FILE_NAME, MyConfig.class);
        return createOrUpdateConfig(config, configSavePath);
    }

    /**
     * 在不修改的情况下，获得config
     *
     * @param configPath
     * @return
     */
    public static MyConfig getConfig(String configPath) {
        return YamlUtil.loadByPath(configPath + NormalParam.CONFIG_FILE_NAME, MyConfig.class);
    }

    /**
     * 将传进来的config保存到制定文件中
     *
     * @param config
     * @param configPath
     * @return
     */
    public static MyConfig createOrUpdateConfig(MyConfig config, String configPath) {
        YamlUtil.dump(config,
                FileUtil.getWriter
                        (configPath + NormalParam.CONFIG_FILE_NAME,
                                CharsetUtil.CHARSET_UTF_8,
                                false
                        )
        );
        return getConfig(configPath);
    }
}
