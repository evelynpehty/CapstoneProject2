package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.essentials.GetConn;
import com.validations.MenuChoices;

public class DisplayAccountsPage {
    public static void show(Scanner scanner, String nric) {
        boolean isExit = false;
        int choice;

        while (!isExit) {
            int count = 1;
            System.out.println("Please select from the following options");
            System.out.println("-----------------------------------------");
            
            String sql = "SELECT account_id, account_type FROM account WHERE nric = ?";
            
            PreparedStatement stmt = GetConn.getPreparedStatement(sql);
            ResultSet resultSet;
            ArrayList<String> accounts = new ArrayList<>();
            try {
                stmt.setString(1, nric);
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    System.out.println(count + ". " 
                    + resultSet.getString(2) 
                    + " - " + resultSet.getInt(1)); 

                    accounts.add(resultSet.getString(1)); //account id
                    count++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            GetConn.closeConn();
            choice = MenuChoices.getUserChoice(scanner, accounts.size());
            System.out.println("Account selected: " + accounts.get(choice - 1));
            // AccountPage.run(accounts.get(choice - 1));
        }
    }
}
