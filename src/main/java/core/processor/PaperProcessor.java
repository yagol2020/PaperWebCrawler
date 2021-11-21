package core.processor;

import bean.searchquery.BaseSearchQuery;
import bean.result.BaseResult;

import javax.swing.*;

/**
 * @author yagol
 * @TIME 2021/11/12 - 8:21 上午
 * @Description 网站数据爬虫
 **/
public interface PaperProcessor<SearchQuery extends BaseSearchQuery, Result extends BaseResult> {


    /**
     * 执行浏览器查询
     *
     * @param searchQuery 查询条件类，能够生成查询条件字符串
     * @param searchLimit 结果数量的最大值
     * @return Result 文献查询结果
     */
    Result run(SearchQuery searchQuery, Integer searchLimit);

    /**
     * 执行浏览器查询，将日志输出到swing中
     *
     * @param searchQuery 查询条件类，能够生成查询条件字符串
     * @param jTextArea   swing组件
     * @param searchLimit 结果数量的最大值
     * @return Result 文献查询结果
     */
    Result run(SearchQuery searchQuery, JTextArea jTextArea, Integer searchLimit);
}