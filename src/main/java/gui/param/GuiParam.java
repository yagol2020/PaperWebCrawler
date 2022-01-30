package gui.param;

/**
 * @author yagol
 * @TIME 2021/11/15 - 10:46 上午
 * @Description
 **/
public class GuiParam {
    /**
     * 回车的code
     */
    public final static int ENTER_CODE = 10;

    public final static Integer[] RESULT_LIMIT = {10, 20, 50, 70, 90, 100, 200, 500};

    public final static String RESULT_UN_LIMIT = "无限制";
    /**
     * 虽然是无限制，但还是得指定数量
     */
    public final static Integer RESULT_UN_LIMIT_NUM = 10000;

    /**
     * 向图表组件传送数据时，网站信息
     */
    public final static String CHART_WEB_SITE_INFO_HASH_MAP_KEY = "webSiteInfos";
}
