package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.essentials.GetConn;
import com.objects.ScannerManager;
import com.styles.Console;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class DisplayAccountsPage {
    static Scanner scanner = ScannerManager.getScanner();

    public static void show(String nric) {
        boolean isExit = false;
        int choice;

        while (!isExit) {
            int count = 1;
            Console.clear();
            System.out.println(FontStyle.yellow + "Please select from the following options" + FontStyle.reset);
            System.out.println("-----------------------------------------");
            
            String sql = "SELECT account_id, account_type FROM account WHERE nric = upper(?)";
            
            PreparedStatement stmt = GetConn.getPreparedStatement(sql);
            ResultSet resultSet;
            ArrayList<Integer> accounts = new ArrayList<>();
            try {
                stmt.setString(1, nric);
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    System.out.println(FontStyle.blue + count + ". " 
                    + resultSet.getString(2) 
                    + " - " + resultSet.getInt(1)); 

                    accounts.add(resultSet.getInt(1)); //account id
                    count++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            GetConn.closeConn();
            System.out.println(count + ". Back" + FontStyle.reset);
            choice = MenuChoices.getUserChoice(count);
            if (choice < count){
                System.out.println(FontStyle.green + "Account selected: " + accounts.get(choice - 1) + FontStyle.reset);
                Console.pause(scanner);
                AccountPage.run(accounts.get(choice - 1));
            } else {
                isExit = true;
            }
        }
    }
}
