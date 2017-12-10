package com.dbpool;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
    private static Connection conn = null;

    //获取一个数据库连接
    public static Connection getConnection() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.注册驱动
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //3.获取连接
            String dbUrl = "jdbc:mysql://127.0.0.1:3306/test";
            conn = DriverManager.getConnection(dbUrl, "root", "123456");
            System.out.println("========数据库连接成功========");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("========数据库连接失败========");
            return null;
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
