package com.bio.Utils;

import com.bio.exception.FlupException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: xyx
 * @Date: 2019-05-21 09:15
 * @Version 1.0
 */
public class SurveyUtil {
    public static String parseIdquestion(Map.Entry<String, Object> item) {
        return parseIdquestion(item.getKey());

        /*if (item.getKey().contains("_"))
            return item.getKey().substring(0, item.getKey().length() - 1);
        else return item.getKey();*/
    }

    public static String parseIdquestion(String str) {
        if (str == null || "".equals(str)) {
            throw new FlupException(110, "empty String");
        }
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
