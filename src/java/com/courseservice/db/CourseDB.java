package com.courseservice.db;

import java.sql.*;

public class CourseDB {
    
    //connection
    public static Connection dbConnection() {
        Connection conn = null;
        String url = "jdbc:oracle:thin:@dbsvcs.cs.uno.edu:1521:orcl";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String userName = "bwalters"; 
        String password = "TZZQGkMQ";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url,userName, password);
        } catch (ClassNotFoundException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
