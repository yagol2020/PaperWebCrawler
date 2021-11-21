package bean.searchquery.impl;

import bean.searchquery.BaseSearchQuery;
import lombok.Data;

/**
 * @author yagol
 * @TIME 2021/11/21 - 7:13 下午
 * @Description acm搜索条件
 **/
@Data
public class AcmSearchQuery implements BaseSearchQuery {
    final static Integer ROW_PER_PAGE_10 = 10;
    final static Integer ROW_PER_PAGE_20 = 20;
    final static Integer ROW_PER_PAGE_50 = 50;
    Integer pageSize;
    /**
     * 从第几页开始
     */
    Integer startPage;
    /**
     * 搜索的关键字
     */
    String allField;

    public AcmSearchQuery(String queryText) {
        this(queryText, ROW_PER_PAGE_50, 1);
    }

    public AcmSearchQuery(String queryText, Integer pageSize, Integer startPage) {
        if (pageSize.equals(ROW_PER_PAGE_10) && !pageSize.equals(ROW_PER_PAGE_20) && !pageSize.equals(ROW_PER_PAGE_50)) {
            this.pageSize = ROW_PER_PAGE_50;
        } else {
            this.pageSize = pageSize;
        }
        this.allField = queryText;
        this.startPage = startPage;
    }

    @Override
    public String gen() {
        return "AllField=" + allField +
                "&" +
                "startPage=" + startPage +
                "&" +
                "pageSize=" + pageSize;
    }
}
