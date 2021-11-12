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

    /**
     * 获得分页查询中，限制的每页论文数量
     *
     * @return 限制的论文数量
     */
    Integer getSearchSize();
}
