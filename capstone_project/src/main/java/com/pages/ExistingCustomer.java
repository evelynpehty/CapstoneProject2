package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import com.essentials.GetConn;
import com.styles.Console;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class ExistingCustomer {

    public static void checkExistingCustomer(Scanner scanner) {
        Console.clear();
        System.out.println(FontStyle.bold + FontStyle.blue + "2. Existing Customer" + FontStyle.reset);
        System.out.print(FontStyle.bold + FontStyle.yellow +"Please key in cutomer's NRIC: " + FontStyle.reset);
        String nric = scanner.nextLine().trim();
        
        try {
            String sqlQuery = "select * from customer where upper(nric) = upper(?)";
            PreparedStatement pstmt = GetConn.getPreparedStatement(sqlQuery);
            pstmt.setString(1, nric);
            ResultSet resultSet = pstmt.executeQuery();    
            if (resultSet.next()) {
                nric = resultSet.getString("nric");
                showAccountsMenu(scanner, nric);

                resultSet.close();
                pstmt.close();
            } else {
                System.out.println(FontStyle.red + "Customer does not exist." + FontStyle.reset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            GetConn.closeConn();
            Console.pause(scanner);
        }

    }

    public static void showAccountsMenu(Scanner scanner, String nric) {
        int choice;
        boolean isExit = false;
        while (!isExit) {
            System.out.println(FontStyle.bold + "Please select from the following options" + FontStyle.reset);
            System.out.println("1. Create Account");
            System.out.println("2. Display Accounts");
            System.out.println("3. View Customer Details");
            System.out.println("4. Edit Customer Details");
            System.out.println("5. Exit");

            choice = MenuChoices.getUserChoice(scanner, 5);

            switch (choice) {
                case 1:
                    CreateAccount.show(scanner, nric);
                    break;
                case 2:
                    DisplayAccountsPage.show(scanner, nric);
                    break;
                case 3:
                    ViewCustomerDetails.show(scanner, nric);
                    break;
                case 4:
                    EditCustomerType.show(scanner, nric);
                    // EditCustomerDetails.show(scanner,nric);
                    break;
                case 5:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to exit the application? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
    } 
}
