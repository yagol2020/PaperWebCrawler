package param;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yagol
 * @TIME 2021/11/13 - 8:40 上午
 * @Description
 **/
public class NormalParam {
    public final static String ORDINAL_WORD_TH = "th";
    public final static String APOSTROPHE = "'";
    public final static String TRANSACTIONS = "Transaction";
    public final static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");
    /**
     * 小、中、大括号内容匹配，注意需要循环遍历
     */
    private final static Pattern BRACKET_CONTENT_PATTERN = Pattern.compile("[\\(\\[\\{](.*?)[\\)\\]\\}]");


    public static List<String> getBracketContentList(String input) {
        List<String> result = new ArrayList<>();
        Matcher matcher = BRACKET_CONTENT_PATTERN.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }
}
