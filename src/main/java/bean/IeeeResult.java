package bean;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:27 下午
 * @Description
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class IeeeResult extends BaseResult {
    public IeeeResult() {
        databaseName = "IEEE XPLORE";
    }

    public void parserTotalSize(String origin) {
        paperSize = Integer.parseInt(StrUtil.split(origin, " ").get(3));
    }
}
