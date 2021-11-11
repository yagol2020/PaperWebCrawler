import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:59 下午
 * @Description
 **/
public class BastTest {
    public static void main(String[] args) {
        FileUtil.writeString("321", "src/main/resources/ieee/result.txt", CharsetUtil.UTF_8);
    }
}
