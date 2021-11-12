package result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author yagol
 * @TIME 2021/11/12 - 9:55 下午
 * @Description
 **/
@Data
@AllArgsConstructor
public class PaperInfo {
    String title;
    List<String> authors;
    String source;
    String year;
    String paperType;
}
