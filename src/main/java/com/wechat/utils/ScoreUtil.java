package com.wechat.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: xyx
 * @Date: 2019-03-14 22:45
 * @Version 1.0
 */
public class ScoreUtil {

    public static double fyrs_risk_score(Double min, Double max, double val) {
        //todo
        if (min == null || max == null)
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

    public static Double getRisk(String risk) {
        if (StringUtils.isEmpty(risk)) {
            return 0.0;
        }
        return Double.valueOf(risk);

    }

    /**
     * @param low
     * @param high
     * @param target
     * @return 1: 高, 0: 中, -1:低
     */
    public static int evaluateRisk(double low, double high, double target) {
        if (target > high) {
            return 1;
        } else if (target < low) {
            return -1;
        } else {
            return 0;
        }

    }

    public static String displayQuestionnaireResult(int count, String lifetime_risk, String fyrs_risk,
                                                    int riskEvaluation,
                                                    List<String> missingModels,
                                                    String low,
                                                    String high
    ) {
        StringBuilder builder = new StringBuilder();
        String template = "您的问卷已完成， 感谢参与\n\n" +
                "您本次问卷答题的分数为: COUNT。如果您的分数低于60分，系统计算的患癌风险值可能无法反映真实情况，建议您退回主界面重新答题。\n\n"
                + "您的近期患癌风险值是：FYRS_SCORE。 您的终身患癌风险值是：LIFETIME_SCORE。";
        builder.append(template.replaceAll("COUNT", "" + count)
                .replaceAll("FYRS_SCORE", "" + fyrs_risk).replaceAll("LIFETIME_SCORE", lifetime_risk));

        String str = "根据您的问卷答案无法计算出 MODELNAME " +
                "癌的风险值，综合风险值中不包含 MODELNAME " +
                "癌的风险值。可能是由于您的答案前后有逻辑冲突，您可以返回主页面重新答题。如若仍无法解决请联系管理员询问具体情况";
        if (!CollectionUtils.isEmpty(missingModels)) {

            String miss = missingModels.stream().reduce((a, b) -> (a + ", " + b)).get();

            builder.append(str.replaceAll("MODELNAME", miss.substring(0, miss.length() - 1)));
        }
        String str2 = "如果您需要参与免费的相关基因检测，进一步完善风险预测信息，请联系单位管理员。";
        builder.append(ScoreUtil.evaluateRisk(ScoreUtil.getRisk(low), ScoreUtil.getRisk(high), ScoreUtil.scoreEmptyNull(lifetime_risk)))
                .append("\n\n");
        builder.append(str2);

        return builder.toString();
    }

}
