package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.essentials.GetConn;

public class DisplayAccounts {
    public static void main(String[] args) {
        try {
            String sqlQuery = "select * from account where NRIC = ?";
            PreparedStatement pstmt = GetConn.getPreparedStatement(sqlQuery);
            pstmt.setString(1, "ironman");
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){

            }       
            // Close the prepared statement and the connection when done
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
