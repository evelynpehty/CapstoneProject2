package com.pages;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.essentials.GetConn;
import com.validations.MenuChoices;

public class CreateAccount {

    public static void show(Scanner scanner, String nric) {
        PreparedStatement pstmt;
        ResultSet resultSet;
        int choice;
        boolean isExit = false;
        String sql;
        String acctType = "";

        while (!isExit) {
            System.out.println("Please select from the following options");
            System.out.println("-----------------------------------------");
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            System.out.println("3. Fixed Deposit Account");
            System.out.println("4. Back");

            choice = MenuChoices.getUserChoice(scanner, 4);

            switch (choice) {
                case 1:
                    acctType = "Savings";
                    break;
                case 2:
                    acctType = "Checking";
                    break;
                case 3:
                    acctType = "Fixed Deposit";
                    break;
                case 4:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to exit the application? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            try {
                System.out.println(acctType);
                sql = "SELECT * FROM ACCOUNT WHERE nric = upper(?) AND ACCOUNT_TYPE =?";
                pstmt = GetConn.getPreparedStatement(sql);
                pstmt.setString(1, nric);
                pstmt.setString(2, acctType);
                resultSet = pstmt.executeQuery();

                if (!resultSet.next()) {
                    sql = "INSERT INTO ACCOUNT(ACCOUNT_ID, NRIC, ACCOUNT_BALANCE, ACCOUNT_CREATED_DATE, ISACCOUNTACTIVE, ACCOUNT_TYPE)"
                            + "VALUES (account_id_seq.NEXTVAL,?,?,?,?,?)";
                    pstmt = GetConn.getPreparedStatement(sql);
                    pstmt.setString(1, nric);
                    pstmt.setInt(2, 0);
                    pstmt.setDate(3, Date.valueOf(LocalDate.now()));
                    pstmt.setInt(4, 1);
                    pstmt.setString(5, acctType);
                    int res = pstmt.executeUpdate();
                    System.out.println(res + " records inserted");

                    sql = "SELECT * FROM ACCOUNT WHERE nric = upper(?) AND ACCOUNT_TYPE = ?";
                    pstmt = GetConn.getPreparedStatement(sql);
                    pstmt.setString(1, nric);
                    pstmt.setString(2, acctType);
                    resultSet = pstmt.executeQuery();
                    resultSet.next();
                    System.out.println(acctType + " Account Created! " + acctType + " ID: " + resultSet.getInt(1));
                    GetConn.closeConn();
                    GetConn.closeConn();
                    GetConn.closeConn();
                    
                    isExit = true;
                    break;
                } else {
                    System.out.println(acctType + " Account exists already!");
                    System.out.print(acctType + " Account ID: ");
                    System.out.println(resultSet.getInt(1));
                    GetConn.closeConn();
                    break;
                }
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            isExit = true;

        }

    }
}
