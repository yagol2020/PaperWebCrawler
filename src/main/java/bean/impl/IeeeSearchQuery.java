package bean.impl;

import bean.BaseSearchQuery;
import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/10 - 7:27 下午
 * @Description
 **/
@Data
@Slf4j
public class IeeeSearchQuery implements BaseSearchQuery {
    final static Integer ROW_PER_PAGE_10 = 10;
    final static Integer ROW_PER_PAGE_25 = 25;
    final static Integer ROW_PER_PAGE_50 = 50;
    final static Integer ROW_PER_PAGE_75 = 75;
    Integer rowsPerPage = 75;
    Integer pageNumber = 1;
    String queryText;
    Boolean highlight = true;
    List<String> returnFacets = ArrayUtil.map(new String[]{"ALL"}, s -> s);
    String returnType = "SEARCH";


    public IeeeSearchQuery(String queryText) {
        this(queryText, 75, 1);
    }

    public IeeeSearchQuery(String queryText, Integer rowsPerPage, Integer pageNumber) {
        if (!rowsPerPage.equals(ROW_PER_PAGE_10) && !rowsPerPage.equals(ROW_PER_PAGE_25)
                && !rowsPerPage.equals(ROW_PER_PAGE_50) && !rowsPerPage.equals(ROW_PER_PAGE_75)) {
            this.rowsPerPage = ROW_PER_PAGE_10;
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

    @Override
    public Integer getSearchSize() {
        return rowsPerPage;
    }
}
