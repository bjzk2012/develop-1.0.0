package cn.kcyf.commons.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tom
 */
public class ArrayUtils {
    public static List<Integer> convertStrArrayToInt(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            List<Integer> array = new ArrayList<>();
            for (String str : strArray){
                if (!StringUtils.isEmpty(str)){
                    array.add(Integer.parseInt(str));
                }
            }
            return array;
        } else {
            return null;
        }
    }

    public static List<Long> convertStrArrayToLong(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            List<Long> array = new ArrayList<>();
            for (String str : strArray){
                if (!StringUtils.isEmpty(str)){
                    array.add(Long.parseLong(str));
                }
            }
            return array;
        } else {
            return null;
        }
    }

    public static Map<String, Integer> toMapList(List<Object[]> objects){
        Map<String, Integer> result = new HashMap<>();
        if (objects == null){
            return result;
        }
        for (Object[] object : objects){
            if (object == null || object.length == 0){
                return result;
            }
            if (object[0] != null) {
                result.put(object[0].toString(), Integer.parseInt(object[1].toString()));
            }
        }
        return result;
    }
}
