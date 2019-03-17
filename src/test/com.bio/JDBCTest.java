package com.bio;

import com.JsonGenerator.FetchData;
import org.junit.Test;

import java.sql.*;

/**
 * @Author: xyx
 * @Date: 2019-03-05 21:53
 * @Version 1.0
 */

public class JDBCTest {
    @Test
    public void test() {
        try {
            Connection connection = FetchData.getConnection();
            String sql = "update questionnaire set risk_bra= '999;000' where idquestionnaire=80";
            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery("select * from persons");
//            rs.last();
//            System.out.println(rs.getRow());
//            ResultSet rs2 = statement.executeQuery("select * from centers");
//            rs2.last();
//            System.out.println(rs2.getRow());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
