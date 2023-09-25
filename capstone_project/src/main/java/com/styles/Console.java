package com.styles;

import java.util.Scanner;

public class Console {
    public static void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause(Scanner scanner) {
        System.out.println("Press ENTER key to continue...");
        System.out.println(FontStyle.hide);
        scanner.nextLine();
        System.out.println(FontStyle.reset);
    }
}
