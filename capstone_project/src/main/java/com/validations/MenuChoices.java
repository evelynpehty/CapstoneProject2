package com.validations;

import java.util.Scanner;

import com.objects.ScannerManager;
import com.styles.FontStyle;

public class MenuChoices {
    static Scanner scanner = ScannerManager.getScanner();
    public static int getUserChoice(int noOfChoice) {
        int userChoice;

        do {
            System.out.print("Enter your choice (1-" + noOfChoice + "): ");

            while (!scanner.hasNextInt()) {
                System.out.println(FontStyle.red + "Invalid input. Please enter a number between 1-" + noOfChoice + "." + FontStyle.reset);
                System.out.print("Enter your choice (1-" + noOfChoice + "): ");
                scanner.next(); // Clear the invalid input
            }
            userChoice = scanner.nextInt();
            scanner.nextLine();

            if (userChoice < 1 || userChoice > noOfChoice) {
                System.out.println(FontStyle.red + "Invalid input. Please enter a number between 1-" + noOfChoice + "." + FontStyle.reset);
            }
        } while (userChoice < 1 || userChoice > noOfChoice);

        return userChoice;
    }

    public static boolean yesnoConfirmation(String message) {
        System.out.print(message);
        String input = scanner.next().toLowerCase();
        scanner.nextLine();
        while (true) {
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println(FontStyle.red + "Invalid choice. Please enter either Y or N" + FontStyle.reset);
                System.out.print(message);
                input = scanner.next().toLowerCase();
                scanner.nextLine();
            }
        }
    }
}
