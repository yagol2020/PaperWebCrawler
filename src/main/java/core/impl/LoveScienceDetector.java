package core.impl;

import bean.impl.LoveScienceSearchQuery;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import core.PaperLevelDetector;
import param.LoveScienceParam;

/**
 * @author yagol
 * @TIME 2021/11/12 - 7:09 下午
 * @Description
 **/
public class LoveScienceDetector implements PaperLevelDetector {
    private static final Log log = LogFactory.get();

    @Override
    public String detector(String name) {
        LoveScienceSearchQuery loveScienceSearchQuery = new LoveScienceSearchQuery();
        loveScienceSearchQuery.setTitle(name);
        String webUrl = LoveScienceParam.BASE_SEARCH_URL + loveScienceSearchQuery.gen();
        log.info(webUrl);
        return null;
    }
}
