package com.bio;

/**
 * @Author: xyx
 * @Date: 2019-03-04 20:34
 * @Version 1.0
 */
public class SqlSelectFactor {
    public static void main(String[] args) {
        String sql = "#1age#\n" +
                "(select age ,age as ad from persons where idperson =IDPERSON) union all\n" +
                "#2Sigmoid_Polyps#\n" +
                "(select answers,(case when answers = '0' then '0' when answers = '1' then '0' when answers = '2' then '2' else '3' end)as ad from `answers` where idquestionnaire = IDQUESTIONNAIRE and idquestion =83) union all\n" +
                "#3NoIBuprofin#\n" +
                "(select answers,(case when answers regexp '[0-9]' then '0' when answers  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 82) union all\n" +
                "#4Vigorus_Xrcis#\n" +
                "(select answers,(case when answers = '0' then '0' when answers in('1','2') then '1' when answers ='3' then '2' else '3' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 44) union all\n" +
                "#5Cig_Yrs#\n" +
                "(select answers,(case when substring_index(substring_index(answers,'\",',1),':\"',-1) regexp '[0-9]' then '0' when substring_index(substring_index(answers,'\",',1),':\"',-1)  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 41) union all\n" +
                "#6Vegetable#\n" +
                "(select answers,(case when answers in (0,1,2) then '0' when answers in (3,4,5)then '1' end)from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion =14) union all\n" +
                "#7Num_Cigs#\n" +
                "(select answers,(case when substring_index(substring_index(answers,':\"',-1),'\"',1) regexp '[0-9]' then '0' when substring_index(substring_index(answers,':\"',-1),'\"',1) like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 41) union all\n" +
                "#8BMI_Trnd#\n" +
                "(select a.answers/pow((select b.answers/100 from `answers` as b where idquestion = 4 and idquestionnaire = IDQUESTIONNAIRE),2) as imb, (case when a.answers/pow((select b.answers/100 from `answers` as b where idquestion = 4 and idquestionnaire = IDQUESTIONNAIRE),2) < 25 then '0' when a.answers/pow((select b.answers/100 from `answers` as b where idquestion = 4 and idquestionnaire = IDQUESTIONNAIRE),2) >30 then '2' else '1' end)as ad  from `answers` as a where idquestionnaire = IDQUESTIONNAIRE and idquestion =5) union all\n" +
                "#9No_NSaids#\n" +
                "(select answers,(case when substring_index(substring_index(answers,'\",',1),':\"',-1) > 3 then '0' when answers  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 82) union all\n" +
                "#10Fam_Hist_CRC????#\n" +
                "(select answers,(case when answers like '%?%' then '1' else '0' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion =89)";
        System.out.println(sql.replaceAll("IDPERSON", "1").replaceAll("IDQUESTIONNAIRE", "2"));
    }
}
