package com.pages;

import java.util.Scanner;

import com.objects.Account;
import com.styles.Console;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class AccountPage {
    private static int show() {
        int n = 7;

        System.out.println("Please select an action from the following list.");
        System.out.println(FontStyle.blue + "1. View balance");
        System.out.println("2. Deposit fund");
        System.out.println("3. Withdraw fund");
        System.out.println("4. Transfer fund");
        System.out.println("5. View transaction history");
        System.out.println("6. Activate/Deactivate account");
        System.out.println("7. Back to list of accounts" + FontStyle.reset);

        return n;
    }

    public static void run(Scanner scanner, int id) {
        Account account = new Account(id);
        boolean exit = false;

        while (!exit) {
            Console.clear();
            System.out.println(FontStyle.cyan + "This is your " + account.getType() + " account " + account.getId() + "..." + FontStyle.reset);
            int n = AccountPage.show();
            int choice = MenuChoices.getUserChoice(scanner, n);

            switch (choice) {
                case 1:
                    account.viewBalance(scanner);
                    break;
                case 2:
                    account.deposit(scanner);
                    break;
                case 3:
                    account.withdraw(scanner);
                    break;
                case 4:
                    account.transfer(scanner);
                    break;
                case 5:
                    account.viewTransaction(scanner);
                    break;
                case 6:
                    account.toggleActive(scanner);
                    break;
                case 7:
                    exit = true;
                    break;

                default:
                    break;
            }
        }
    }
}
