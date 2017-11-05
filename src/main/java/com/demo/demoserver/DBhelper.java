package com.demo.demoserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by xjl on 2017/10/24.
 */
public class DBhelper {
    private String driver = "com.mysql.jdbc.Driver";
    private Connection conn;
    // URL指向要访问的数据库名demo
    String url = "jdbc:mysql://39.108.80.242/demo";
    // MySQL配置时的用户名
    String user = "root";
    // MySQL配置时的密码
    String password = "19940902*xjl";

    public Connection getConn(){
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            conn = DriverManager.getConnection(url, user, password);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void Close(){
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
