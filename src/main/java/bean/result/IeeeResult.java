package bean.result;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import util.MyFileUtil;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/11 - 8:27 下午
 * @Description IEEE论文统计结果
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class IeeeResult extends BaseResult {
    public IeeeResult() {
        databaseName = "IEEE XPLORE";
    }

    /**
     * 从页面的原始字符串获得论文数量
     *
     * @param origin 形如 Showing 1-12 of <strong>30</strong> for YourKeyWords
     */
    public void parserTotalSize(String origin) {
        if (paperSize == 0) {
            paperSize = Integer.parseInt(StrUtil.split(origin.replace(",", StrUtil.EMPTY), " ").get(3));
        }
    }

    public void addPaperInfo(String title, List<String> authors, String source, String year, String paperType, String paperUrl) {
        this.addPaperInfo(title, authors, source, year, paperType, "N/A", paperUrl);
    }

    private String convertUrl2DownloadUrl(String url) {
        return url.replace("/document/", "/stamp/stamp.jsp?tp=&arnumber=");
    }


    public void addPaperInfo(String title, List<String> authors, String source, String year, String paperType, String influenceFactor, String paperUrl) {
        paperList.add(new PaperInfo(title, authors, source, year, paperType, influenceFactor, paperUrl, convertUrl2DownloadUrl(paperUrl)));
    }

    @Override
    public void save2File() {
        MyFileUtil.writeMultiLine2Csv(getCsvResultPath(), genHeader(), genResults());
    }

    @Override
    public void save2Db() {

    }

}
