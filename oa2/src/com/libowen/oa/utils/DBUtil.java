package com.libowen.oa.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    private static final ResourceBundle bundle=ResourceBundle.getBundle("resources.jdbc");

    public static String driver= bundle.getString("driver");
    public static String url= bundle.getString("url");
    public static String user= bundle.getString("user");
    public static String password= bundle.getString("password");

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
