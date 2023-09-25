package com.validations;

import java.util.Scanner;

public class MenuChoices {
    public static int getUserChoice(Scanner scanner, int noOfChoice) {
        int userChoice;

        do {
            System.out.print("Enter your choice (1-" + noOfChoice + "): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1-" + noOfChoice + ".");
                System.out.print("Enter your choice (1-" + noOfChoice + "): ");
                scanner.next(); // Clear the invalid input
            }
            userChoice = scanner.nextInt();
            scanner.nextLine();

            if (userChoice < 1 || userChoice > noOfChoice) {
                System.out.println("Invalid input. Please enter a number between 1-" + noOfChoice + ".");
            }
        } while (userChoice < 1 || userChoice > noOfChoice);

        return userChoice;
    }

    public static boolean yesnoConfirmation(Scanner scanner, String message) {
        System.out.print(message);
        String input = scanner.next().toLowerCase();
        scanner.nextLine();
        while (true) {
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice. Please enter either Y or N");
                System.out.print(message);
                input = scanner.next().toLowerCase();
                scanner.nextLine();
            }
        }
    }
}
