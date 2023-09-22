package com.validations;

public class chkDigits {
    public static boolean checkDigits(String strInput){
        if(strInput.matches("\\d+")){
            return true;
        }else{
            return false;
        }
    }
    
}
