package com.pages;

import java.util.Scanner;

import com.validations.MenuChoices;

public class ExistingCustomers {
    public static void showAccountsMenu(Scanner scanner){
        int choice;
        boolean isExit = false;
        while (!isExit) {
                System.out.println("Please select from the following options");
                System.out.println("-----------------------------------------");
                System.out.println("1. Create Account");
                System.out.println("2. Display Accounts");
                System.out.println("3. Edit Account Details");
                System.out.println("4. Close Account");
                System.out.println("5. Deposit Money");
                System.out.println("6. Withdraw Balance");
                System.out.println("7. View Balance");
                System.out.println("8. Exit");

            choice = MenuChoices.getUserChoice(scanner, 8);

            switch (choice) {
                case 1:
                    // Add logic for creating a new account
                    break;
                case 2:
                    // displayAccounts();
                    break;
                case 3:
                    // Add logic for editing account details
                    break;
                case 4:
                    // Add logic for closing an account
                    break;
                case 5:
                    // Add logic for depositing money
                    break;
                case 6:
                    // Add logic for withdrawing money
                    break;
                case 7:
                    // Add logic for displaying balance
                    break;
                case 8:
                    isExit = MenuChoices.exitConfirmation(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
