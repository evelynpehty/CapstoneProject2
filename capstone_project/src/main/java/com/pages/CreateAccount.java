package com.pages;

import java.util.Scanner;

import com.validations.MenuChoices;

public class CreateAccount {
    public static void show(Scanner scanner) {
        int choice;
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Please select from the following options");
            System.out.println("-----------------------------------------");
            System.out.println("1. Savings Account");
            System.out.println("2. Current Account");
            System.out.println("3. Fixed Deposit Account");
            System.out.println("4. Back");

            choice = MenuChoices.getUserChoice(scanner, 3);

            switch (choice) {
                case 1:
                    isExit = true;
                    System.out.println("Savings Account Created!");
                    break;
                case 2:
                    isExit = true;
                    // displayAccounts();
                    System.out.println("Current Account Created!");
                    break;
                case 3:
                    isExit = true;
                    System.out.println("Fixed Deposit Account Created!");
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
