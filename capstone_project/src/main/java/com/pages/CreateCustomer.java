package com.pages;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.validations.MenuChoices;
import com.validations.chkText;
import com.validations.chkDigits;
import com.validations.chkEmail;
import com.validations.chkDate;
import com.essentials.GetConn;;

public class CreateCustomer {
    public static void show(Scanner scanner){
        try {
            // dob - required field and check date format and age atleast 18
            System.out.print("Enter your DOB (YYYY-MM-DD): ");
            String strDate = scanner.nextLine();
            LocalDate dob;
            while (true){
                if(chkDate.checkDateFormat(strDate) && chkDate.checkDateValidity(strDate)){         
                    dob = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    break;
                }
                else{
                    System.out.print("Invalid date format. Please re-enter DOB in YYYY-MM-DD format: ");
                    strDate = scanner.nextLine();
                }   
            }
            
            // Calculate age
            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(dob, currentDate);
            
            // Check if the person is at least 18 years old
            if (age.getYears() >= 18) {
                System.out.print("Enter your NRIC: ");
                String nric = scanner.nextLine();

                while(true){
                    if(nric.trim().isEmpty()){
                        System.out.print("NRIC is required, please enter NRIC: ");
                        nric = scanner.nextLine();
                    }else{
                        break;
                    }
                }
                
                // check if NRIC exists
                String query = "SELECT * FROM CUSTOMER WHERE UPPER(NRIC) = UPPER(?)";
                PreparedStatement preparedStatement = GetConn.getPreparedStatement(query);
                preparedStatement.setString(1, nric);

                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet.next()){
                    // customer exist;
                    System.out.println("Customer Exists!");
                } else{
                    // customer odes not exists
                    
                    // FIRST NAME - required field and only contain alphabet
                    System.out.print("Enter your first name: ");
                    String firstName = scanner.nextLine();

                    while(true){
                        if(!chkText.checkText(firstName)){
                            System.out.print("Invalid input. Re-Enter First Name: ");
                            firstName = scanner.nextLine();
                        }else{
                            break;
                        }
                    }

                    // LAST NAME - required field and only contain alphabet
                    System.out.print("Enter your last name: ");
                    String lastName = scanner.nextLine();

                    while(true){
                        if(!chkText.checkText(lastName)){
                            System.out.print("Invalid input. Re-Enter Last Name: ");
                            lastName = scanner.nextLine();
                        }else{
                            break;
                        }
                    }

                    // Gender - required field and only contain M or F
                    System.out.print("Enter gender (M/F): ");
                    String gender = scanner.nextLine();

                    while(true){
                        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")){
                            if (gender.equalsIgnoreCase("M")){
                                gender = "Male";
                            } else{
                                gender = "Female";
                            }
                            break;
                        } else{
                            System.out.print("Invalid input. Re-Enter gender (M/F):  ");
                            gender = scanner.nextLine();
                        }
                    }

                    // phone number - required field and only contain digits
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();

                    while (true){
                        if(chkDigits.checkDigits(phoneNumber)){
                            break;
                        } else{
                            System.out.print("Invalid input. Re-Enter phone number:  ");
                            phoneNumber = scanner.nextLine();
                        }
                    }
                
                    // EMAIL - not required field and can be empty
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    if(email.isEmpty() == false){
                        while (true){
                            if(chkEmail.checkEmailFormat(email)){
                                break;
                            }else{
                                System.out.print("Invalid email format. Re-Enter email: ");
                                email = scanner.nextLine();
                            }
                        }
                    } else{
                        email = null;
                    }

                    // NATIONALITY - required and only contain alphabet
                    System.out.print("Enter your nationality: ");
                    String nationality = scanner.nextLine();

                    while(true){
                        if(chkText.checkText(nationality)){
                            break;
                        }else{
                            System.out.print("Invalid input. Re-Enter Nationality: ");
                            nationality = scanner.nextLine();
                        }
                    }

                    // Display customer details
                    String bold = "\033[1m";
                    String reset = "\033[0m";

                    System.out.println();
                    System.out.println(bold+ "Customer Details"+reset);
                    System.out.println("+---------------------+---------------------+");
                    System.out.printf("| %-19s | %-19s |%n", "Date of Birth:", strDate);
                    System.out.printf("| %-19s | %-19s |%n", "NRIC:", nric);
                    System.out.printf("| %-19s | %-19s |%n", "First Name:", firstName);
                    System.out.printf("| %-19s | %-19s |%n", "Last Name:", lastName);
                    System.out.printf("| %-19s | %-19s |%n", "Gender:", gender);
                    System.out.printf("| %-19s | %-19s |%n", "Phone:", phoneNumber);

                    if (email == null) {
                        System.out.printf("| %-19s | %-19s |%n", "Email:", "");
                    } else {
                        System.out.printf("| %-19s | %-19s |%n", "Email:", email);
                    }

                    System.out.printf("| %-19s | %-19s |%n", "Nationality:", nationality);
                    System.out.println("+---------------------+---------------------+");

                    boolean isConfirmed = MenuChoices.yesnoConfirmation(scanner, "Key Y to confirm creation, N to cancel creation: ");
                    
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
                            System.out.println("Customer created!");
                        } else {
                            System.out.println("Failed to insert data. Please try again");
                        }
                    } 
                }
            } else {
                System.out.println("Unable to Create Customer. He/she must be atleast 18 years old.");
            }
            
        } catch (SQLException se) {
            // TODO: handle exception
            System.out.println(se.getMessage());
        } finally{
            GetConn.closeConn();
        }
    }
}
