package bean.searchquery.impl;

import bean.searchquery.BaseSearchQuery;
import cn.hutool.core.util.ArrayUtil;
import lombok.Data;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:27 下午
 * @Description
 **/
@Data
public class IeeeSearchQuery implements BaseSearchQuery {
    final static Integer ROW_PER_PAGE_10 = 10;
    final static Integer ROW_PER_PAGE_25 = 25;
    final static Integer ROW_PER_PAGE_50 = 50;
    final static Integer ROW_PER_PAGE_75 = 75;
    final static Integer ROW_PER_PAGE_100 = 100;
    Integer rowsPerPage;
    Integer pageNumber;
    String queryText;
    Boolean highlight = true;
    List<String> returnFacets = ArrayUtil.map(new String[]{"ALL"}, s -> s);
    String returnType = "SEARCH";


    public IeeeSearchQuery(String queryText) {
        this(queryText, ROW_PER_PAGE_100, 1);
    }

    public IeeeSearchQuery(String queryText, Integer rowsPerPage, Integer pageNumber) {
        if (!rowsPerPage.equals(ROW_PER_PAGE_10) && !rowsPerPage.equals(ROW_PER_PAGE_25)
                && !rowsPerPage.equals(ROW_PER_PAGE_50) && !rowsPerPage.equals(ROW_PER_PAGE_75)) {
            this.rowsPerPage = ROW_PER_PAGE_100;
        } else {
            this.rowsPerPage = rowsPerPage;
        }
        this.queryText = queryText;
        this.pageNumber = pageNumber;
    }

    @Override
    public String gen() {
        return "queryText=" + queryText +
                "&" +
                "rowsPerPage=" + rowsPerPage +
                "&" +
                "pageNumber=" + pageNumber;
    }
}
