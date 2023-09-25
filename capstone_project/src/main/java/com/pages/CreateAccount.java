package com.pages;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.essentials.GetConn;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class CreateAccount {

    public static void show(Scanner scanner, String nric) {
        boolean isExit = false;

        while (!isExit) {
            System.out.println("Please select from the following options");
            System.out.println("-----------------------------------------");
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            System.out.println("3. Fixed Deposit Account");
            System.out.println("4. Back");

            PreparedStatement pstmt;
            ResultSet resultSet;
            int choice;
            String sql;
            String acctType = "";
            Boolean createAcc = true;

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
                    createAcc = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (createAcc) {
                try {
                    sql = "SELECT * FROM ACCOUNT WHERE nric = upper(?) AND ACCOUNT_TYPE =?";
                    pstmt = GetConn.getPreparedStatement(sql);
                    pstmt.setString(1, nric);
                    pstmt.setString(2, acctType);
                    resultSet = pstmt.executeQuery();

                    if (!resultSet.next()) {
                        sql = "INSERT INTO ACCOUNT(ACCOUNT_ID, NRIC, ACCOUNT_BALANCE, ACCOUNT_CREATED_DATE, ISACCOUNTACTIVE, ACCOUNT_TYPE)"
                                + "VALUES (account_id_seq.NEXTVAL,upper(?),?,?,?,?)";
                        pstmt = GetConn.getPreparedStatement(sql);
                        pstmt.setString(1, nric);
                        pstmt.setInt(2, 0);
                        pstmt.setDate(3, Date.valueOf(LocalDate.now()));
                        pstmt.setInt(4, 1);
                        pstmt.setString(5, acctType);
                        pstmt.executeUpdate();

                        sql = "SELECT * FROM ACCOUNT WHERE nric = upper(?) AND ACCOUNT_TYPE = ?";
                        pstmt = GetConn.getPreparedStatement(sql);
                        pstmt.setString(1, nric);
                        pstmt.setString(2, acctType);
                        resultSet = pstmt.executeQuery();
                        resultSet.next();
                        System.out.println(FontStyle.green + acctType + " Account Created! " + FontStyle.reset);
                        System.out.print(FontStyle.cyan + acctType + " Account ID: " + FontStyle.reset);
                        System.out.println(FontStyle.yellow + FontStyle.bold +  resultSet.getInt(1) + FontStyle.reset);
                        GetConn.closeConn();
                        GetConn.closeConn();
                        GetConn.closeConn();

                        isExit = true;
                        break;
                    } else {
                        System.out.println(FontStyle.red + acctType + " Account exists already!" + FontStyle.reset);
                        System.out.print(FontStyle.cyan + acctType + " Account ID: " + FontStyle.reset);
                        System.out.println(FontStyle.yellow + FontStyle.bold + resultSet.getInt(1) + FontStyle.reset);
                        GetConn.closeConn();
                        break;
                    }
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            isExit = true;

        }

    }
}
