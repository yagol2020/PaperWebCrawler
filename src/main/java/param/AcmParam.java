package param;

/**
 * @author yagol
 * @TIME 2021/11/21 - 7:49 下午
 * @Description
 **/
public class AcmParam {
    public static final String BASE_SEARCH_URL = "https://dl.acm.org/action/doSearch?";
    public static final String TOTAL_PAPER_SIZE_XPATH = "//span[@class='result__count']";
    public static final String PAPER_INFO_PER_XPATH = "//li[@class='search__item issue-item-container']";
    public static final String PAPER_TYPE_XPATH = ".//div[@class='issue-heading']";
    public static final String PUBLISH_DATA_XPATH = ".//div[@class='bookPubDate simple-tooltip__block--b']";
    public static final String TITLE_XPATH = ".//span[@class='hlFld-Title']";
    public static final String AUTHORS_XPATH = ".//ul[@aria-label='authors']";
    public static final String SOURCE_XPATH = ".//span[@class='epub-section__title']";
}
