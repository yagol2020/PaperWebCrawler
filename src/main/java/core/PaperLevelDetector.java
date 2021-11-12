package core;

/**
 * @author yagol
 * @TIME 2021/11/12 - 2:09 下午
 * @Description 论文等级识别器
 **/
public interface PaperLevelDetector {
    /**
     * 根据名称识别论文等级信息
     * 对于会议，识别 CCF分类
     * 对于期刊，识别 影响因子和CCF分类
     *
     * @param name 需要被检测的期刊或会议名称
     * @return 信息
     */
    String detector(String name);

}
