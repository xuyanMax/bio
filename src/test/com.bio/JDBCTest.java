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
    @Test
    public void test() {
        try {
            Connection connection = FetchData.getConnection();
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
}
