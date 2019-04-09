package com.bio;

import com.JsonGenerator.FetchData;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author: xyx
 * @Date: 2019-03-05 21:53
 * @Version 1.0
 */


public class JDBCTest {
    private String URL = "jdbc:mysql://localhost:3306/cdcDev?useUnicode=true&characterEncoding=gbk&allowMultiQueries=true";

    private String USERNAME = "root";

    private String PASSWORD = "root";

    @Test
    public void test() {
        try {
            Connection connection = FetchData.getLocalConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from persons");
            rs.last();
            System.out.println(rs.getRow());
            ResultSet rs2 = statement.executeQuery("select * from centers");
            rs2.last();
            System.out.println(rs2.getRow());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String sql = "update persons set name = 'aaasd' where idperson=550;" +
            "update persons set age=19 where idperson=550;"+
            "#1age#\n" +
            "(select age ,(case when age between 1 and 50 then 50 else age end) from persons where idperson =IDPERSON) union all\n" +
            "#2Sigmoid_Polyps#\n" +
            "(select answers,(case when answers = '0' then '0' when answers = '1' then '0' when answers = '2' then '2' else '3' end)as ad from `answers` where idquestionnaire = IDQUESTIONNAIRE and idquestion =83) union all\n" +
            "#3Vigorus_Xrcis#\n" +
            "(select answers,(case when answers = '0' then '0' when answers in('1','2') then '1' when answers ='3' then '2' else '3' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 44) union all\n" +
            "#4Vegetable#\n" +
            "(select answers,(case when answers in (0,1,2) then '0' when answers in (3,4,5)then '1' end)from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion =14) union all\n" +
            "#5Cig_Yrs#\n" +
            "(select answers,(case when substring_index(substring_index(answers,'\",',1),':\"',-1) regexp '[0-9]' then '0' when substring_index(substring_index(answers,'\",',1),':\"',-1)  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 41) union all\n" +
            "#6Num_Cigs#\n" +
            "(select answers,(case when substring_index(substring_index(answers,':\"',-1),'\"',1) regexp '[0-9]' then '0' when substring_index(substring_index(answers,':\"',-1),'\"',1) like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 41) union all\n" +
            "#7BMI_Trnd#\n" +
            "(select @a, (case when @a < 25 then '0' when @a >30 then '2' else '1' end)) union all\n" +
            "#8Fam_Hist_CRC有小问题#\n" +
            "(select answers,(case when answers like '%结%' then '1' else '0' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion =89) union all\n" +
            "#9No_NSaids#\n" +
            "(select answers,(case when substring_index(substring_index(answers,'\",',1),':\"',-1) > 3 then '0' when answers  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 81) union all\n" +
            "#10NoIBuprofin#\n" +
            "(select answers,(case when answers regexp '[0-9]' then '0' when answers  like \"%/%\" then '1' else '1' end) from answers where idquestionnaire = IDQUESTIONNAIRE and idquestion = 82)";

    @Test
    public void testMultiQueries() throws SQLException {

        Connection connection = FetchData.getLocalConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }


}
