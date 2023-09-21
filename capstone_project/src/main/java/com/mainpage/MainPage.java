package com.mainpage;

import java.util.Scanner;

public class MainPage {
    
    public static void main(String[] args) {
        int choice;
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while(isExit == false){
            displayMenu();
            choice = getUserChoice();

            switch(choice){
                case 1:
                    // create account
                    break;
                case 2: 
                    // display accounts
                    break;
                case 3: 
                    // edit account detail
                    break;
                case 4: 
                    // close account 
                    break;
                case 5: 
                    // deposit 
                    break;
                case 6: 
                    // wtihdraw 
                    break;
                case 7: 
                    // display balance
                    break;
                case 8: 
                    // Exit
                    System.out.print("Are you sure you want to exit the application? Y/N: ");
                    String input = scanner.nextLine();

                    while(true){
                        if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")){
                            if(input.equalsIgnoreCase("y")){
                                isExit = true;
                                System.out.println("Goodbye :(");
                                scanner.close();
                            }
                            break;
                            
                        } else{
                            System.out.println("Invalid choice. Please enter either Y or N");
                            System.out.print("Are you sure you want to exit the application? Y/N: ");
                            input = scanner.nextLine();
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
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

    private static int getUserChoice() {
        int userChoice;
        Scanner scanner = new Scanner(System.in);
        
        do{
            System.out.print("Enter your choice (1-8): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                System.out.print("Enter your choice (1-8): ");
                scanner.next(); // Clear the invalid input
            }
            userChoice = scanner.nextInt();
    
            if (userChoice < 1 || userChoice > 8) {
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        } while (userChoice < 1 || userChoice > 8);

        return userChoice;
    }
}
