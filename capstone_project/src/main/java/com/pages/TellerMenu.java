package com.pages;

import java.util.Scanner;

import com.styles.Console;
import com.validations.MenuChoices;

public class TellerMenu {
    
    public static void displayTeller(Scanner scanner) {
        Console.clear();
        int choice;
        boolean isExit = false;
        while (!isExit) {
            Console.clear();
            System.out.println("----------------------------");
            System.out.println("Welcome to the Bank Teller");
            System.out.println("----------------------------");
            System.out.println("Please select an option:");
            System.out.println("1. New Customer");
            System.out.println("2. Existing Customer");
            System.out.println("3. Exit");
            
            choice = MenuChoices.getUserChoice(scanner, 3);
            switch (choice) {
                case 1:
                    CreateCustomer.show(scanner);
                    break;
                case 2:
                    ExistingCustomer.checkExistingCustomer(scanner);
                    break;                   
                case 3:
                    isExit = MenuChoices.yesnoConfirmation(scanner, "Are you sure you want to exit the application? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
