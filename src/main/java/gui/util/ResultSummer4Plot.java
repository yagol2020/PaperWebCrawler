package gui.util;

import bean.analysis.CountDataPerYear;
import bean.result.BaseResult;
import bean.result.PaperInfo;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.NumberUtil;
import gui.bean.ChartData;
import org.jsoup.Connection;
import param.PaperWebSiteEnum;

import java.util.*;

/**
 * @author yagol
 * @Description 将BaseResult集成起来，为绘制图做数据准备
 */
public class ResultSummer4Plot {
    public static ChartData genData4Plot(HashMap<PaperWebSiteEnum, BaseResult> results) {
        ChartData chartData = new ChartData();
        LinkedHashMap<String, Double> linkedHashMap = new LinkedHashMap<>();
        List<PaperInfo> paperInfos = new ArrayList<>();
        for (Map.Entry<PaperWebSiteEnum, BaseResult> entry : results.entrySet()) {
            linkedHashMap.put(entry.getKey().name(), NumberUtil.toDouble(entry.getValue().getPaperList().size()));
            paperInfos.addAll(entry.getValue().getPaperList());
        }
        chartData.setWebsiteInfos(linkedHashMap);
        chartData.setPaperInfo(new HashMap<String, Object>(16) {
            {
                put(CountDataPerYear.class.getSimpleName(), new CountDataPerYear().getDataByBaseResult(paperInfos));
            }
        });
        return chartData;
    }
}
