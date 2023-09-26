package com.pages;

import java.util.Scanner;

public class MainPage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // TellerLogin.login(scanner);
        EditCustomerDetails.show(scanner, "A1234567Z");

        scanner.close();
    }

}
