package com.pages;
import com.objects.ScannerManager;

public class MainPage {
    public static void main(String[] args) {
        TellerLogin.login();

        ScannerManager.closeScanner();
    }

}
