package com.dbpool;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;


/**
 * 效率对比
*@author 许效银
*@date 11/20/2017 1:34 PM
*/
public class DBCPEfficiencyTest {

    /**
     * 不用连接池：每写一条数据前,就新建一个连接
     * @throws Exception
     */
    @Test
    public void testWriteDBByEveryConn() throws Exception{
        long start=System.currentTimeMillis();
        for(int i = 0; i < 100; i++){
            writeDBByEveryConnNoPool(i);
        }
        long end=System.currentTimeMillis();
        //time elapse:5912
        System.out.println("time elapse:"+(end-start));

    }

    /**
     * 使用连接池：每写一条数据前,从连接池中获取一个连接
     * @throws Exception
     */
    @Test
    public void testWriteDBByDBCP() throws Exception{
        long start=System.currentTimeMillis();
        for(int i = 0; i < 100; i++){
            writeDBByDBCPWithPool(i);
        }
        long end=System.currentTimeMillis();
        //time elapse:6897
        System.out.println("time elapse:"+(end-start));
    }

    /**
     * 不用连接池：只建一条连接,写入所有数据
     * @throws Exception
     */
    @Test
    public void testWriteDBByOneConn() throws Exception{
        long start=System.currentTimeMillis();
        Connection conn = JDBCUtil.getConnection();
        Statement stat = conn.createStatement();
        for(int i = 0; i < 100; i++){
            writeDBByOneConnNoPool(i, stat);
        }
        conn.close();
        long end=System.currentTimeMillis();
        //time elapse:4551
        System.out.println("time elapse:"+(end-start));
    }

    /**
     * 使用连接池：只建一条连接,写入所有数据
     * @throws Exception
     */
    @Test
    public void testWriteDBByDBCPOneConn() throws Exception{
        long start=System.currentTimeMillis();
        Connection connection = DBCPUtil.getConnection();
        Statement stat = connection.createStatement();
        for(int i = 0; i < 100; i++){
            writeDBByDBCPWithPoolOne(i, stat);
        }
        stat.close();
        connection.commit();
        connection.close();
        long end=System.currentTimeMillis();
        //time elapse:1179
        System.out.println("time elapse:"+(end-start));
    }


    //不使用连接池写数据库,每写一条数据创建一个连接
    public void writeDBByEveryConnNoPool(int data){
        String sql = "insert into n2 (age) values (" + data + ")";
        Connection conn = JDBCUtil.getConnection();
        try{
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    //不使用连接池写数据库,只用一个连接,写所有数据
    public void writeDBByOneConnNoPool(int data, Statement stat){
        String sql = "insert into n2 (age) values (" + data + ")";
        try{
            stat.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace() ;
        }
    }

    //通过DBCP连接池写数据库
    public void writeDBByDBCPWithPool(int data){
        String sql = "insert into n2(age) values (" + data + ")";
        try {
            Connection conn = DBCPUtil.getConnection();
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //通过DBCP连接池写数据库
    public void writeDBByDBCPWithPoolOne(int data,Statement stat){
        String sql = "insert into n2(age) values (" + data + ")";
        try {
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
