package com.validations;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validations {
    public static double validateDouble(Scanner scanner) {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Invalid input, please try again: ");
            }
        }
    }
}
