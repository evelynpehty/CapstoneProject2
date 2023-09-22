package com.pages;

import java.util.Scanner;

public class TellerLogin {
    
    public static void login(Scanner scanner){
        String username;
        String password;
        boolean isExit = false;
        while (!isExit){
            System.out.println("-------------");
            System.out.println("Teller Login");
            System.out.println("-------------");
            System.out.print("Enter your teller username: ");
            username = scanner.nextLine();

            System.out.print("Enter your teller password: ");
            password = scanner.nextLine();

            // assume correct authentication

            TellerMenu.displayTeller(scanner);
        }
    }
}
