package com.validations;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {
    public static double validateDouble(Scanner scanner) {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Invalid input. Please try again: ");
            }
        }
    }

    public static int validateInt(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                // TODO: handle exception
                scanner.nextLine();
                System.out.print("Invalid input. Please try again: ");
            }
        }
    }
}
