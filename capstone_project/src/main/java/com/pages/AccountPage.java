package com.pages;

import java.util.Scanner;

import com.objects.Account;
import com.objects.ScannerManager;
import com.styles.Console;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class AccountPage {
    static Scanner scanner = ScannerManager.getScanner();

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

    public static void run(int id) {
        Account account = new Account(id);
        boolean exit = false;

        while (!exit) {
            Console.clear();
            System.out.println(FontStyle.cyan + "This is your " + account.getType() + " account " + account.getId() + "..." + FontStyle.reset);
            int n = AccountPage.show();
            int choice = MenuChoices.getUserChoice(n);

            switch (choice) {
                case 1:
                    account.viewBalance();
                    break;
                case 2:
                    account.deposit();
                    break;
                case 3:
                    account.withdraw();
                    break;
                case 4:
                    account.transfer();
                    break;
                case 5:
                    account.viewTransaction();
                    break;
                case 6:
                    account.toggleActive();
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
