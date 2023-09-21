package com.mainpage;

import java.util.Scanner;

public class MainPage {
    public static void main(String[] args) {
        int choice;
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            displayTeller();
            choice = getUserChoice(scanner, 3);
            displayMenu();
            choice = getUserChoice(scanner, 8);

            switch (choice) {
                case 1:
                    // Add logic for creating a new account
                    break;
                case 2:
                    displayAccounts();
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
                    isExit = exitConfirmation(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayTeller() {
        System.out.println("Welcome to the Bank Teller");
        System.out.println("----------------------------");
        System.out.println("Please select an option:");
        System.out.println("1. New Customer");
        System.out.println("2. Existing Customer");
        System.out.println("3. Exit");
    }

    private static void displayMenu() {
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
    }

    private static int getUserChoice(Scanner scanner, int noOfChoice) {
        int userChoice;

        do {
            System.out.print("Enter your choice (1-" + noOfChoice + "): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1-" + noOfChoice + ".");
                System.out.print("Enter your choice (1-" + noOfChoice + "): ");
                scanner.next(); // Clear the invalid input
            }
            userChoice = scanner.nextInt();

            if (userChoice < 1 || userChoice > noOfChoice) {
                System.out.println("Invalid input. Please enter a number between 1-" + noOfChoice + ".");
            }
        } while (userChoice < 1 || userChoice > noOfChoice);

        return userChoice;
    }

    private static void displayAccounts() {
        // Add logic for displaying accounts
        System.out.println("Displaying accounts...");
        // Implement the logic to retrieve and display accounts
    }

    private static boolean exitConfirmation(Scanner scanner) {
        System.out.print("Are you sure you want to exit the application? Y/N: ");
        String input = scanner.next().toLowerCase();

        while (true) {
            if (input.equals("y")) {
                System.out.println("Goodbye :(");
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice. Please enter either Y or N");
                System.out.print("Are you sure you want to exit the application? Y/N: ");
                input = scanner.next().toLowerCase();
            }
        }
    }
}
