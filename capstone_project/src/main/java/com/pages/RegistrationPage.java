package com.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.validations.MenuChoices;

public class RegistrationPage {
    public static void show(Scanner scanner){
        System.out.println("Enter your NRIC: ");
        String nric = scanner.nextLine();
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your gender: ");
        String gender = scanner.nextLine();
        System.out.println("Enter your phone number: ");
        String number = scanner.nextLine();
        System.out.println("Enter your DOB (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your nationality: ");
        String nationality = scanner.nextLine();

        boolean isConfirmed = MenuChoices.yesnoConfirmation(scanner, "Are you sure you want to create an account? Y/N: ");
        
        if (isConfirmed){
            //add database logic
            System.out.println("Customer created!");
        }

    }
}
