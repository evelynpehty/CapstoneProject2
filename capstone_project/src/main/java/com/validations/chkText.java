package com.validations;

public class chkText {
    public static boolean checkText(String strInput) {
        if (strInput.matches("^[a-zA-Z]+$")) {
            return true;
        } else {
            return false;
        }
    }
}
