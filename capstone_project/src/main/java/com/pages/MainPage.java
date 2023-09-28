package com.pages;

import java.util.Scanner;

public class MainPage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TellerLogin.login(scanner);
        // EditCustomerType.show(scanner, "a1234567z");

        scanner.close();
    }

}
