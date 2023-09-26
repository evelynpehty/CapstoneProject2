package com.pages;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.validations.chkText;
import com.validations.chkDigits;
import com.validations.chkEmail;
import com.validations.chkDate;
import com.essentials.GetConn;
import com.styles.Console;
import com.styles.FontStyle;;

public class CreateCustomer {
    static String strDate;
    static LocalDate dob;
    static String nric;
    static String firstName;
    static String lastName;
    static String gender;
    static String nationality;
    static String phoneNumber;
    static String email;
    static String choiceInput;

    static Period getAge(Scanner scanner){
        System.out.print(FontStyle.bold + FontStyle.yellow + "Enter DOB (YYYY-MM-DD): " + FontStyle.reset);
        strDate = scanner.nextLine();
    
        while (true){
            if(chkDate.checkDateFormat(strDate) && chkDate.checkDateValidity(strDate)){         
                dob = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            }
            else{
                System.out.println(FontStyle.red + "Invalid Date Format!" + FontStyle.reset);
                System.out.print(FontStyle.bold + FontStyle.yellow + "Please re-enter DOB in YYYY-MM-DD format: " + FontStyle.reset);
                strDate = scanner.nextLine();
            }   
        }
        
        // Calculate age
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(dob, currentDate);

        return age;
    }
    
    static void strInputField(Scanner scanner, String type){
         // FIRST NAME - required field and only contain alphabet
        System.out.print(FontStyle.yellow + FontStyle.bold + "Enter " + type + ": " + FontStyle.reset);
        String temp = scanner.nextLine();
        
        while(true){
            if(!chkText.checkText(temp)){
                System.out.println(FontStyle.red + "Invalid input!" + FontStyle.reset);
                System.out.print(FontStyle.bold + FontStyle.yellow + "Please re-Enter " + type + " : " + FontStyle.reset);
                temp = scanner.nextLine();
            }else{
                break;
            }
        }

        if(type == "first name"){
            firstName = temp;
        } else if(type == "last name"){
            lastName = temp;
        } else if(type == "nationality"){
            nationality = temp;
        }
    }

    static void genderInput(Scanner scanner){
        // Gender - required field and only contain M or F
        System.out.print(FontStyle.yellow + FontStyle.bold + "Enter Gender (M/F): " + FontStyle.reset);
        gender = scanner.nextLine();

        while(true){
            if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")){
                if (gender.equalsIgnoreCase("M")){
                    gender = "Male";
                } else{
                    gender = "Female";
                }
                break;
            } else{
                System.out.println(FontStyle.red + "Invalid input! Please enter only M or F" + FontStyle.reset);
                System.out.print(FontStyle.yellow + FontStyle.bold + "Re-Enter gender (M/F):  " + FontStyle.reset);
                gender = scanner.nextLine();
            }
        }
    }

    static void phoneNumberInput(Scanner scanner){
        System.out.print(FontStyle.yellow + FontStyle.bold + "Enter phone number: " + FontStyle.reset);
        phoneNumber = scanner.nextLine();

        while (true){
            if(chkDigits.checkDigits(phoneNumber)){
                break;
            } else{
                System.out.println(FontStyle.red + "Invalid input! Please enter only numbers" + FontStyle.reset);
                System.out.print(FontStyle.yellow + FontStyle.bold + "Re-Enter phone number:  " + FontStyle.reset);
                phoneNumber = scanner.nextLine();
            }
        }
    }

    static void emailInput(Scanner scanner){
        System.out.print(FontStyle.yellow + FontStyle.bold + "Enter email: " + FontStyle.reset);
        email = scanner.nextLine();

        while (true){
            if(email.isEmpty() == false){

                if(chkEmail.checkEmailFormat(email)){
                    break;
                } else{
                    System.out.println(FontStyle.red + "Invalid email!" + FontStyle.reset);
                    System.out.print(FontStyle.yellow + FontStyle.bold + "Re-Enter email:  " + FontStyle.reset);
                    email = scanner.nextLine();
                }

            } else{
                email = null;
                break;
            }
        }
        
    }
    
    static ResultSet scanNRIC(Scanner scanner){
        System.out.print(FontStyle.yellow + FontStyle.bold + "Enter NRIC: " + FontStyle.reset);
        nric = scanner.nextLine();
        
        while(true){
            if(nric.trim().isEmpty()){
                System.out.print(FontStyle.red + "NRIC is required, please enter NRIC: " + FontStyle.reset);
                nric = scanner.nextLine();
            }else{
                break;
            }
        }
        
        try {
            // check if NRIC exists
            String query = "SELECT * FROM CUSTOMER WHERE UPPER(NRIC) = UPPER(?)";
            PreparedStatement preparedStatement = GetConn.getPreparedStatement(query);
            preparedStatement.setString(1, nric);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            return null;
        }
    }

    static void displayCustomerDetails(){
        System.out.println(FontStyle.cyan);
        System.out.println(FontStyle.bold + "Customer Details" + FontStyle.reset);
        System.out.println("+---+---------------------+---------------------+");
        System.out.printf("| %1s | %-19s | %-19s |%n", "1", "Date of Birth", strDate);
        System.out.printf("| %1s | %-19s | %-19s |%n", "2", "NRIC", nric);
        System.out.printf("| %1s | %-19s | %-19s |%n", "3", "First Name", firstName);
        System.out.printf("| %1s | %-19s | %-19s |%n", "4", "Last Name", lastName);
        System.out.printf("| %1s | %-19s | %-19s |%n", "5", "Gender", gender);
        System.out.printf("| %1s | %-19s | %-19s |%n", "6", "Phone", phoneNumber);
        
        if (email == null) {
            System.out.printf("| %1s | %-19s | %-19s |%n", "7", "Email", "");
        } else {
            System.out.printf("| %1s | %-19s | %-19s |%n", "7", "Email", email);
        }
        
        System.out.printf("| %1s | %-19s | %-19s |%n", "8", "Nationality", nationality);
        System.out.println("+---+---------------------+---------------------+");
    }

    static void choiceInput(Scanner scanner){
        System.out.print(FontStyle.yellow + "Key Y to confirm creation, N to cancel creation or (1-8) to edit fields: " + FontStyle.reset);
        choiceInput = scanner.nextLine();

          while(true){
            boolean isYes = choiceInput.equalsIgnoreCase("y");
            boolean isNo = choiceInput.equalsIgnoreCase("n"); 
            boolean isValidIntChoice = false;

            if (!isYes && !isNo) {
                try {
                    int numericChoice = Integer.parseInt(choiceInput);
                    isValidIntChoice = numericChoice >= 1 && numericChoice <= 8;
                } catch (NumberFormatException e) {
                    
                }
            } 
            if(isYes || isNo || isValidIntChoice){
                break;
            }else{
                System.out.println(FontStyle.red + "Invalid input choice! " + FontStyle.reset);
                System.out.print(FontStyle.yellow + "Key Y to confirm creation, N to cancel creation or (1-8) to edit fields: " + FontStyle.reset);
                choiceInput = scanner.nextLine();
            }
        }
    }

    public static void show(Scanner scanner){
        try {
            Console.clear();
            System.out.println(FontStyle.bold + FontStyle.cyan + "Create New Customer\n" + FontStyle.reset);

            if (getAge(scanner).getYears() >= 18) {
                if(scanNRIC(scanner).next()){
                    System.out.println(FontStyle.red + "Customer Exists!" + FontStyle.reset);
                } else{
                    strInputField(scanner, "first name"); // first name
                    strInputField(scanner, "last name");  // last name
                    genderInput(scanner); // gender
                    phoneNumberInput(scanner); //phpne number 
                    emailInput(scanner); //email
                    strInputField(scanner, "nationality");  // nationality

                    displayCustomerDetails();
                    boolean isConfirmed = false;

                    while(true){
                        choiceInput(scanner);
                        if(choiceInput.equalsIgnoreCase("y")){
                            isConfirmed = true;
                            break;
                        }
                        if(choiceInput.equalsIgnoreCase("n")){
                            isConfirmed = false;  
                            break; 
                        } else if(Integer.parseInt(choiceInput) >= 1 && Integer.parseInt(choiceInput) <= 8){
                            switch (Integer.parseInt(choiceInput)) {
                                case 1:
                                    if ((getAge(scanner).getYears() >= 18) == false) {
                                        System.out.println(FontStyle.red + "Customer must be atleast 18 years old!" + FontStyle.reset);
                                    }
                                    break;                   
                                case 2:
                                    if (scanNRIC(scanner).next()){
                                        System.out.println(FontStyle.red + "Invalid NRIC, customer already existed!" + FontStyle.reset);
                                    }
                                    break;
                                case 3:
                                    strInputField(scanner, "first name");
                                    break;
                                case 4:
                                    strInputField(scanner, "last name");
                                    break;
                                case 5:
                                    genderInput(scanner);
                                    break;
                                case 6:
                                    phoneNumberInput(scanner);
                                    break;
                                case 7:
                                    emailInput(scanner);
                                    break;
                                case 8:
                                    strInputField(scanner, "nationality");
                                    break;
                                default:
                                    System.out.println(FontStyle.red + "Invalid choice. Please try again." + FontStyle.reset);
                            }
                        
                        displayCustomerDetails();
                        }
                    }
                    
                    if (isConfirmed){
                        //add database logic
                        String insertQuery = "INSERT INTO CUSTOMER (nric, first_name, last_name, gender, phone_number, dob, email, nationality) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pStat = GetConn.getPreparedStatement(insertQuery);
                        pStat.setString(1, nric);
                        pStat.setString(2, firstName);
                        pStat.setString(3, lastName);
                        pStat.setString(4, gender);
                        pStat.setString(5, phoneNumber);
                        pStat.setDate(6, Date.valueOf(dob));
                        pStat.setString(7, email);
                        pStat.setString(8,  nationality);

                        // Execute the INSERT statement
                        int rowsInserted = pStat.executeUpdate();
                        if (rowsInserted > 0) {                    
                            System.out.println(FontStyle.green + "Customer Created Successfully!" + FontStyle.reset);
                        } else {
                            System.out.println(FontStyle.red + "Failed to insert data. Please try again" + FontStyle.reset);
                        }
                    } 
                }
            } else {
                System.out.println(FontStyle.red + "Unable to Create Customer. He/she must be atleast 18 years old." + FontStyle.reset);
            }
            Console.pause(scanner);
        } catch (SQLException se) {
            System.out.println(FontStyle.red + se.getMessage() + FontStyle.reset);
        } finally{
            GetConn.closeConn();
        }
    }
}
