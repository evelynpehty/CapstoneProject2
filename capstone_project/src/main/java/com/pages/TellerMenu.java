package com.pages;

import java.util.Scanner;

import com.validations.MenuChoices;

public class TellerMenu {
    
    public static void displayTeller(Scanner scanner) {
        int choice;
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Welcome to the Bank Teller");
            System.out.println("----------------------------");
            System.out.println("Please select an option:");
            System.out.println("1. New Customer");
            System.out.println("2. Existing Customer");
            System.out.println("3. Exit");
            
            choice = MenuChoices.getUserChoice(scanner, 3);
            switch (choice) {
                case 1:
                    // Add logic for creating a new account
                    break;
                case 2:
                    ExistingCustomers.showAccountsMenu(scanner);
                    break;                   
                case 3:
                    isExit = MenuChoices.exitConfirmation(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
