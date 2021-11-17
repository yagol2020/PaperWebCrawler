package util;

/**
 * @author yagol
 * @TIME 2021/11/17 - 8:36 下午
 * @Description 因为要打包成jar，因此出现了这个不该出现的类
 **/
public class JarUtil {
    public static String PWC_JAR_PATH = JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
}
