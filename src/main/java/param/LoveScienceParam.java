package param;

import lombok.Data;

/**
 * @author yagol
 * @TIME 2021/11/12 - 7:10 下午
 * @Description
 **/
@Data
public class LoveScienceParam {
    public static final String BASE_SEARCH_URL = "https://www.iikx.com/e/action/ListInfo.php?";
    public static final String SEARCH_TYPE_URL = "123%2C124%2C125%2C126%2C127%2C128%2C129%2C130%2C131%2C132%2C133%2C134%2C135%2C136";
    public final static String UNTIL_CONDITION_XPATH = "//section//table[@class='sci-table-info']//tbody";
    public final static String SCI_INFO_TABLE_XPATH = "//section//table[@class='sci-table-info']//tbody//tr";
    public final static String INFLUENCE_FACTOR_XPATH = "//section//table[@class='sci-table-info']//tbody//tr//td[@data-del]";
}
