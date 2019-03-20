package com.wechat.utils;

/**
 * @Author: xyx
 * @Date: 2019-03-14 22:45
 * @Version 1.0
 */
public class ScoreUtil {

    public static double fyrs_risk_score(Double min, Double max, double val) {
        //todo
        if (min == null || max == null )
            return 1.0;
        return 100 * (val - min) / (max - min);
    }

    public static double lifetime_risk_score(double min, double max, double val) {
        return fyrs_risk_score(min, max, val);
    }

    public static Double scoreEmptyNull(String val) {
        if (val == null || val.equals("")) return 0.0;
        return Double.valueOf(val);
    }
}
