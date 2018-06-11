package com.bio.Utils;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    public static SqlSession getSqlSession(){
        try {
            InputStream is = Resources.getResourceAsStream("junit-applicationContext.xml");
            if (sqlSessionFactory == null){
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            }
            return sqlSessionFactory.openSession();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
