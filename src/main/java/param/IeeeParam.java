package param;

import lombok.Data;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:32 下午
 * @Description IEEE静态参数
 **/
@Data
public class IeeeParam {
    public final static String BASE_SEARCH_URL = "https://ieeexplore.ieee.org/search/searchresult.jsp?";
    public final static String UNTIL_CONDITION_XPATH = "//xpl-results-list//*[@id>0]";
    public final static String PAPER_SIZE_XPATH = "//xpl-search-results//*[@class='Dashboard-header col-12']";
    public final static String[] SEARCH_INDEX_INFO_ALL = {"title", "authors", "source", "paper info", "cited info", "abstract", "html"};
    public final static String[] SEARCH_INDEX_INFO_NO_CITED = {"title", "authors", "source", "paper info", "abstract", "html"};
    public final static String[] SEARCH_INDEX_INFO_NO_AUTHORS = {"title", "source", "paper info", "abstract", "html"};
    public final static String PAPER_URL_PREFIX = "https://ieeexplore.ieee.org/document/";
    public final static String PAPER_ID_ATTRIBUTE = "id";
}
