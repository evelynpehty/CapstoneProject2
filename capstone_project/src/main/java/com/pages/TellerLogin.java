package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.essentials.GetConn;
import com.styles.FontStyle;

public class TellerLogin {

    public static void login(Scanner scanner) {
        String username;
        String password;
        PreparedStatement pStmt;
        // boolean isExit = false;
        System.out.print("Enter your teller username: ");
        username = scanner.nextLine();

        System.out.print("Enter your teller password: ");
        System.out.print(FontStyle.hide); // Hide input
        password = scanner.nextLine();
        System.out.print(FontStyle.reset); // Show input

        while (true) {
            try {
                String sqlQuery = "SELECT * FROM teller where UPPER(teller_username) = UPPER(?) and teller_password = ?";
                pStmt = GetConn.getPreparedStatement(sqlQuery);
                pStmt.setString(1, username);
                pStmt.setString(2, password);
                ResultSet resultSet = pStmt.executeQuery();

                if (resultSet.next()) {
                    break;
                } else {
                    System.out.println(FontStyle.red + "Incorrect login credential!" + FontStyle.reset);
                    System.out.print("Enter your teller username: ");
                    username = scanner.nextLine();

                    System.out.print("Enter your teller password: ");
                    System.out.print(FontStyle.hide); // Hide input
                    password = scanner.nextLine();
                    System.out.print(FontStyle.reset); // Show input
                }
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            } finally {
                GetConn.closeConn();
            }
        }
        TellerMenu.displayTeller(scanner, password);
    }
}
