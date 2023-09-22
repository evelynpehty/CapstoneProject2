package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.essentials.GetConn;
import com.validations.MenuChoices;

public class ExistingCustomer {

    public static void checkExistingCustomer(Scanner scanner) {
        System.out.println("-------------------------------");
        System.out.print("Please key in cutomer's NRIC: ");
        String nric = scanner.nextLine().trim();
        try {
            String sqlQuery = "select * from customer where upper(nric) = upper(?)";
            PreparedStatement pstmt = GetConn.getPreparedStatement(sqlQuery);
            pstmt.setString(1, nric);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Customer exist.");
                showAccountsMenu(scanner, nric);
            } else {
                System.out.println("Customer does not exist.");
            }
            resultSet.close();
            pstmt.close();
            GetConn.closeConn();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAccountsMenu(Scanner scanner, String nric) {
        int choice;
        boolean isExit = false;
        while (!isExit) {
            System.out.println("-----------------------------------------");
            System.out.println("Please select from the following options");
            System.out.println("1. Create Account");
            System.out.println("2. Display Accounts");
            System.out.println("3. Edit Customer Details");
            System.out.println("4. Exit");

            choice = MenuChoices.getUserChoice(scanner, 8);

            switch (choice) {
                case 1:
                    // CreateAccount.show(scanner, nric);
                    break;
                case 2:
                    // DisplayAccountsPage(scanner,nric);
                    break;
                case 3:
                    EditCustomerType.show(scanner, "A1234567Z");
                    // EditCustomerDetails.show(scanner,nric);
                    break;
                case 4:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to exit the application? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
