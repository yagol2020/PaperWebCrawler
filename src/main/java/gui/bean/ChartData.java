package gui.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author yagol
 * @TIME 2021/11/24 - 7:14 下午
 * @Description
 **/
@Data
public class ChartData {
    LinkedHashMap<String, Double> websiteInfos;
    HashMap<String, Object> paperInfo;

}
