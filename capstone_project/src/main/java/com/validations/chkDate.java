package com.validations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chkDate {
    public static boolean checkDateFormat(String strInput){
        String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}";
        Matcher matcher = Pattern.compile(dateFormatPattern).matcher(strInput);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkDateValidity(String strInput) {
        try {
            LocalDate.parse(strInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true; // Date is valid
        } catch (java.time.format.DateTimeParseException e) {
            return false; // Date is invalid
        }
    }
}
