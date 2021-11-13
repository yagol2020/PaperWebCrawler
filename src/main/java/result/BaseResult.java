package result;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/11 - 9:11 下午
 * @Description 论文统计结果
 **/
@Data
public abstract class BaseResult {
    String databaseName;
    Integer paperSize = 0;
    List<PaperInfo> paperList = new ArrayList<>();
    String searchQuery;

    /**
     * 保存论文查询的结果，到文件
     */
    public abstract void save2File();

    /**
     * 保存论文查询的结果，到数据库
     */
    public abstract void save2Db();

    public List<String[]> genResults() {
        List<String[]> results = new ArrayList<>();
        for (PaperInfo paperInfo : paperList) {
            results.add(new String[]{
                    searchQuery,
                    paperInfo.title,
                    paperInfo.getYear(),
                    paperInfo.getInfluenceFactor(),
                    paperInfo.source,
                    paperInfo.getPaperType(),
                    CollUtil.join(paperInfo.getAuthors(), ","),
            });
        }
        return results;
    }

    protected String[] genHeader() {
        return new String[]{
                "searchQuery",
                "title",
                "year",
                "influenceFactor",
                "source",
                "paperType",
                "authors",
        };
    }
}
