package core;

import result.BaseResult;

/**
 * @author yagol
 * @TIME 2021/11/12 - 2:09 下午
 * @Description 论文等级识别器
 **/
public interface PaperLevelDetector {

    /**
     * 根据查询的result，完善论文等级信息
     *
     * @param result
     * @return
     */
    BaseResult detector(BaseResult result);

}
