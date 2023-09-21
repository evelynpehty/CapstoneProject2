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
                System.out.println("4. Exit");

            choice = MenuChoices.getUserChoice(scanner, 4);

            switch (choice) {
                case 1:
                    CreateAccount.show(scanner);
                    break;
                case 2:
                    // displayAccounts();
                    break;
                case 3:
                    // Add logic for editing account details
                    break;
                case 4:
                    isExit = MenuChoices.yesnoConfirmation(scanner, "Are you sure you want to exit the application? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
