package util;

/**
 * @author yagol
 * @TIME 2021/11/22 - 6:58 下午
 * @Description 测试专用
 **/
public class JarUtil {
    public static String PWC_JAR_PATH = JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();

}
