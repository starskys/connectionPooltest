package com;

import com.dbpool.JDBCUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class EfficiencyTest {
    @Autowired
    private BasicDataSource dataSource;

    @Test
    public void useDbPool() throws SQLException {
        long start = System.currentTimeMillis();
        System.out.println(start);
        for(int i=0;i<10000;i++){
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST (ID)VALUES(?)");
            preparedStatement.setInt(1,i);
            preparedStatement.execute();
            connection.close();
        }
        //耗时：11384
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public void noDbPool() throws SQLException {
        long start = System.currentTimeMillis();
        System.out.println(start);
        for(int i=0;i<10000;i++){
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST (ID)VALUES(?)");
            preparedStatement.setInt(1,i);
            preparedStatement.execute();
            connection.close();
        }
        //耗时59077
        System.out.println(System.currentTimeMillis()-start);
    }
}
