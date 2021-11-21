package bean.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yagol
 * @date 11:04
 * @description
 **/
@Data
@AllArgsConstructor
public class CountDataOneYear {
    String year;
    Integer count;
}