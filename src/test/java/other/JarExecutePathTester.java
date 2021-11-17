package other;

/**
 * @author yagol
 * @TIME 2021/11/17 - 8:18 下午
 * @Description
 **/
public class JarExecutePathTester {
    public static void main(String[] args) {
        System.out.println(JarExecutePathTester.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

}
