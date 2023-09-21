package com.essentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GetConn {
    static Connection conn;
    static Statement statement;
    static PreparedStatement preparedStatement;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USERNAME = "capstone";
    private static final String PASSWORD = "pass";

    // ESTABLISH CONNECTION
    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        assert conn != null : "connection fail";
            
        return conn;
    }
    
    // STATEMENT
    public static Statement getStatement(){
        try{
            conn = getConnection();
            statement = conn.createStatement();
            return statement;
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // PREPARED STATEMENT
    static public PreparedStatement getPreparedStatement(String query){
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(query);
            return preparedStatement;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    // CLOSE CONNECTION
    static public void closeConn(){
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
