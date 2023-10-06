package com.objects;
import java.util.Scanner;

public class Mock {
    Scanner scanner = new Scanner(System.in);

    public void test(Scanner scanner){
        System.out.print("Please key in your value: ");
        Double ans = scanner.nextDouble();

        if(ans>100){
            System.out.println("Yes");
        } else {
            System.out.println("False");
        }

    }
    
}
