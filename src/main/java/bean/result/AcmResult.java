package bean.result;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/21 - 7:14 下午
 * @Description
 **/
public class AcmResult extends BaseResult {
    @Override
    public void save2File() {

    }

    @Override
    public void save2Db() {

    }

    public void parserTotalSize(String originStr) {
        if (paperSize == 0) {
            paperSize = Integer.parseInt(originStr.replace("Results", StrUtil.EMPTY).replace(",", StrUtil.EMPTY).trim());
        }
    }

    public void addPaperInfo(String title, String source, List<String> authors, String publishYear, String paperType, String paperUrl) {
        this.addPaperInfo(title, authors, source, publishYear, paperType, "N/A", paperUrl);

    }

    public void addPaperInfo(String title, List<String> authors, String source, String year, String paperType, String influenceFactor, String paperUrl) {
        paperList.add(new PaperInfo(
                title, authors, source, year, paperType, influenceFactor, paperUrl
        ));
    }
}
