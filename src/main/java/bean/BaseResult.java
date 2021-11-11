package bean;

import lombok.Data;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/11 - 9:11 下午
 * @Description
 **/
@Data
public class BaseResult {
    String databaseName;
    Integer paperSize;
    List<PaperInfo> paperList;

    public String genResultStr() {
        return databaseName + ", " + paperSize;
    }
}

@Data
class PaperInfo {
    String title;
    String source;
}
