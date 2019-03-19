package com.wechat.utils;

import com.bio.beans.Answer;
import com.bio.service.IAnswerService;

import java.util.List;

/**
 * @Author: xyx
 * @Date: 2019-03-18 22:33
 * @Version 1.0
 */
public class SqlUtil {
    public static String riskModelValue(String lifetime_risk, String fyrs_risk) {

        if (lifetime_risk == null && fyrs_risk == null) return ";";

        if (fyrs_risk != null) return fyrs_risk + ";" + lifetimeRiskNullString(lifetime_risk);
        else return ";" + lifetimeRiskNullString(lifetime_risk);
    }

    public static String lifetimeRiskNullString(String str) {
        return stringEmptyNull(str);
    }

    public static Integer countDuplicateQustions(List<Integer> firstValues, IAnswerService answerService, Integer idquestionnaire) {
        // 匹配重复问卷题目，求问卷得(0,20,40,60,80,100)
        int count = 0;
        for (Integer idquestion : firstValues) {
            // size of answers = 2
            List<Answer> answers = answerService.findByIdquestionIdQuestionnaire(idquestion, idquestionnaire);
            if (answers != null && answers.size() > 1) {
                if (answers.get(0).getAnswers().equalsIgnoreCase(answers.get(1).getAnswers()))
                    count += 20;
            } else break;
        }
        return count;
    }

    public static Double countRisk(List<Double> list) {
        double res = 1.0;
        for (double l : list)
            res *= l;
        return 1 - res;
    }

    public static String stringEmptyNull(String str) {
        return str == null ? "" : str;
    }
}
