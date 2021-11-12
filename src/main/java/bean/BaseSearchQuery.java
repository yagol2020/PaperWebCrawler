package bean;

/**
 * @author yagol
 * @TIME 2021/11/12 - 8:22 上午
 * @Description
 **/
public interface BaseSearchQuery {
    /**
     * 生成查询条件
     *
     * @return 查询条件
     */
    String gen();
}
