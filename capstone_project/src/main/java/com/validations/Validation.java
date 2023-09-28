package com.validations;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.styles.FontStyle;

public class Validation {
    public static double validatePositiveDouble(Scanner scanner) {
        while (true) {
            try {
                double input = validatePositive(scanner.nextDouble());
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print(FontStyle.red + "Invalid input. Please try again: "+ FontStyle.reset);
            }
        }
    }

    public static int validatePositiveInt(Scanner scanner) {
        while (true) {
            try {
                int input = validatePositive(scanner.nextInt());
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                // TODO: handle exception
                scanner.nextLine();
                System.out.print(FontStyle.red + "Invalid input. Please try again: " + FontStyle.reset);
            }
        }
    }

    private static <T extends Number> T validatePositive(T x) {
        if (Math.signum(x.doubleValue()) > 0) {
            return x;
        } else {
            throw new InputMismatchException();
        }
    }
}
