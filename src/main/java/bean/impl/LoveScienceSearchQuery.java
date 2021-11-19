package bean.impl;

import bean.BaseSearchQuery;
import lombok.Data;
import param.LoveScienceParam;

/**
 * @author yagol
 * @TIME 2021/11/12 - 7:12 下午
 * @Description 爱科学网站的查询条件生成器
 **/
@Data
public class LoveScienceSearchQuery implements BaseSearchQuery {
    String title;
    /**
     * 神秘属性，但不加就会错误
     */
    Integer ph = 1;
    String searchType = LoveScienceParam.SEARCH_TYPE_URL;

    @Override
    public String gen() {
        return "title=" + title + "&ph=" + ph + "&classid=" + searchType;
    }
}
