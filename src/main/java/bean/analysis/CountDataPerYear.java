package bean.analysis;

import bean.result.BaseResult;
import bean.result.PaperInfo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author yagol
 * @date 10:29
 * @description 论文每年的数量，用于画图
 **/
@Data
public class CountDataPerYear {
    Integer startYear;
    Integer endYear;
    List<CountDataOneYear> data = new ArrayList<>();

    public CountDataPerYear getDataByBaseResult(BaseResult baseResult) {
        List<PaperInfo> paperInfos = baseResult.getPaperList();
        if (paperInfos.size() == 0) {
            startYear = 0;
            endYear = startYear;
        } else {
            if (paperInfos.size() == 1) {
                startYear = Integer.parseInt(paperInfos.get(0).getYear());
                endYear = startYear;
                data.add(new CountDataOneYear(paperInfos.get(0).getYear(), 1));
            } else {
                paperInfos.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getYear())));
                HashMap<Integer, Integer> countHashMap = new HashMap<>(16);
                for (PaperInfo paperInfo : paperInfos) {
                    Integer count = countHashMap.getOrDefault(Integer.parseInt(paperInfo.getYear()), 0);
                    countHashMap.put(Integer.parseInt(paperInfo.getYear()), count + 1);
                }
                startYear = Integer.parseInt(CollectionUtil.getFirst(paperInfos).getYear());
                endYear = Integer.parseInt(CollectionUtil.getLast(paperInfos).getYear());
                for (int i = startYear; i <= endYear; i++) {
                    data.add(new CountDataOneYear(
                            String.valueOf(i),
                            countHashMap.getOrDefault(i, 0)
                    ));
                }

            }
        }
        return this;
    }

}

