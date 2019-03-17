package com.wechat.utils;

/**
 * @Author: xyx
 * @Date: 2019-03-14 22:45
 * @Version 1.0
 */
public class ScoreUtil {

    public static double fyrs_risk_score(double min, double max, double val) {
        return Math.abs((Math.log(val) - Math.log(min)) / (Math.log(max) - Math.log(min)));
    }

    public static double lifetime_risk_score(double min, double max, double val) {
        return Math.abs((Math.log(val) - Math.log(min)) / (Math.log(max) - Math.log(min)));
    }
}
