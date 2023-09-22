package com.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chkEmail {
    public static boolean checkEmailFormat(String strInput){
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Matcher matcher = Pattern.compile(emailPattern).matcher(strInput);
        
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }
}
