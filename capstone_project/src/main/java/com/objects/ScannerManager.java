package com.objects;

import java.util.Scanner;

public class ScannerManager {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner customScanner) {
        scanner = customScanner;
    }

    public static void closeScanner() {
        scanner.close();
    }
}