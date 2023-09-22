package com.pages;

import java.util.Scanner;

import com.validations.MenuChoices;

import objects.Account;

public class AccountPage {
    private static int show() {
        int n = 7;

        System.out.println("Please select an action from the follwing list.");
        System.out.println("1. View balance");
        System.out.println("2. Deposit fund");
        System.out.println("3. Withdraw fund");
        System.out.println("4. Transfer fund");
        System.out.println("5. View transaction history");
        System.out.println("6. Close account");
        System.out.println("7. Back to list of accounts");

        return n;
    }

    public static void run(Scanner scanner, int id) {
        Account account = new Account(id);

        System.out.println("This is your " + account.getType() + " account " + account.getId() + "...");
        int n = AccountPage.show();
        int choice = MenuChoices.getUserChoice(scanner, n);

        switch (choice) {
            case 1:
                account.viewBalance();
                break;
            case 2:
                account.deposit(scanner);
                break;
            case 3:
                account.withdraw(scanner);
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;

            default:
                break;
        }
    }
}
