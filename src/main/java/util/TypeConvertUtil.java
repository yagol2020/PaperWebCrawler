package util;

import java.util.Arrays;

/**
 * @author yagol
 * @date 18:13
 * @description 类型转换工具类
 **/
public class TypeConvertUtil {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(TypeConvertUtil.objArray2Type(Double.class, new String[]{"1.3", "4", "-3"})));
    }

    public static <T> T[] objArray2Type(Class<T> clazz, Object[] dataArray) {
        if (clazz.equals(Double.class)) {
            Double[] result = new Double[dataArray.length];
            try {
                for (int i = 0; i < dataArray.length; i++) {
                    result[i] = Double.parseDouble(String.valueOf(dataArray[i]));
                }
            } catch (Exception e) {
                return null;
            }
            return (T[]) result;
        } else if (clazz.equals(String.class)) {
            String[] result = new String[dataArray.length];
            try {
                for (int i = 0; i < dataArray.length; i++) {
                    result[i] = String.valueOf(dataArray[i]);
                }
            } catch (Exception e) {
                return null;
            }
            return (T[]) result;
        } else {
            return null;
        }
    }
}
