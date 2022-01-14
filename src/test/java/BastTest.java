import cn.hutool.log.Log;
import cn.hutool.setting.yaml.YamlUtil;
import config.MyConfig;
import org.junit.Test;
import param.NormalParam;
import util.JarUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:59 下午
 * @Description
 **/
public class BastTest {

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
        System.out.println(JarUtil.PWC_JAR_PATH);
        System.out.println(BastTest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    @Test
    public void testResourcesPath() {
        System.out.println(NormalParam.BASE_RESOURCES_PATH);
    }

    @Test
    public void testConfig() {
        MyConfig properties = YamlUtil.loadByPath("config/base.yaml", MyConfig.class);
        System.out.println(properties.getChrome().getDriverPath());
        System.out.println(JarUtil.PWC_JAR_PATH);
    }

    @Test
    public void testExePath() {
        System.out.println(System.getProperty("exe.path"));
    }

    @Test
    public void logFileTest(){
        Log.get().info("this log from junit, INFO");
        Log.get().warn("this log from junit, WARN");
        Log.get().error("this log from junit, ERROR");
    }
}
