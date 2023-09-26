package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.validations.chkText;
import com.validations.chkDigits;
import com.validations.chkEmail;
import com.essentials.GetConn;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class EditCustomerDetails {
    static String currentFirstName;
    static String currentLastName;
    static String currentPhoneNumber;
    static String currentEmail;
    static String currentdob;
    static String currentNationality;

    public static void show(Scanner scanner, String nric) {
        String sql = "SELECT * FROM CUSTOMER WHERE UPPER (NRIC) = UPPER (?)";
        PreparedStatement stmt = GetConn.getPreparedStatement(sql);

        String currentInformation = "";
        String editType = "";
        int choice;
        boolean isExit = false;
        while (!isExit) {
            boolean isEmail = false;
            try {
                stmt.setString(1, nric);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    currentFirstName = resultSet.getString("FIRST_NAME");
                    currentLastName = resultSet.getString("LAST_NAME");
                    currentdob = resultSet.getString("DOB");
                    currentPhoneNumber = resultSet.getString("PHONE_NUMBER");
                    currentEmail = resultSet.getString("EMAIL");
                    currentNationality = resultSet.getString("NATIONALITY");
                } else {
                    System.out.println("Customer not found.");
                    return; // Exit if customer not found
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return; // Exit on database error
            }
            System.out.println("Hello " + currentFirstName);
            System.out.println(FontStyle.bold + "Customer Details" + FontStyle.reset);
            System.out.println("+---+---------------------+---------------------+");
            System.out.printf("| %1s | %-19s | %-19s |%n", "", "NRIC", nric);
            System.out.printf("| %1s | %-19s | %-19s |%n", "1", "First Name", currentFirstName);
            System.out.printf("| %1s | %-19s | %-19s |%n", "2", "Last Name", currentLastName);
            System.out.printf("| %1s | %-19s | %-19s |%n", "3", "Phone", currentPhoneNumber);

            if (currentEmail == null) {
                System.out.printf("| %1s | %-19s | %-19s |%n", "4", "Email", "");
            } else {
                System.out.printf("| %1s | %-19s | %-19s |%n", "4", "Email", currentEmail);
            }

            System.out.printf("| %1s | %-19s | %-19s |%n", "5", "Nationality", currentNationality);
            System.out.printf("| %1s | %-19s | %-19s |%n", "6", "Back", "");
            System.out.println("+---+---------------------+---------------------+");

            choice = MenuChoices.getUserChoice(scanner, 6);

            switch (choice) {
                case 1:
                    editType = "FIRST_NAME";
                    currentInformation = currentFirstName;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;
                case 2:
                    editType = "LAST_NAME";
                    currentInformation = currentLastName;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 3:
                    editType = "PHONE_NUMBER";
                    currentInformation = currentPhoneNumber;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 4:
                    editType = "EMAIL";
                    currentInformation = currentEmail;
                    isEmail = true;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 5:
                    editType = "NATIONALITY";
                    currentInformation = currentNationality;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 6:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to return to menu? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please select (Y/N).");
            }
        }
    }

    private static void ModifyInfor(String nric, Scanner scanner, String currentInformation, String editType,
            boolean isEmail) {
        PreparedStatement pstmt;
        do {
            System.out.println("Current Information: " + currentInformation);
            System.out.println("Enter new Information: ");

            while (true) {
                String NewInfor = scanner.nextLine();
                boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
                if (isConfirmed) {
                    boolean isValid = true;
                    if (editType == "EMAIL") {
                        isValid = chkEmail.checkEmailFormat(NewInfor);
                    }
                    if (editType == "PHONE_NUMBER") {
                        isValid = chkDigits.checkDigits(NewInfor);
                    }

                    if (!isValid) {
                        System.out.println("Invalid " + editType + " format. Please enter a valid input.");
                        continue;
                    }

                    String sql = "UPDATE CUSTOMER SET " + editType + " = ? WHERE NRIC = UPPER(?)";
                    try {
                        pstmt = GetConn.getPreparedStatement(sql);
                        pstmt.setString(1, NewInfor);
                        pstmt.setString(2, nric);
                        pstmt.execute();
                        System.out.println(editType + " updated to: " + NewInfor);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                } else {
                    boolean retry = promptConfirmation(scanner, "Continue to edit " + editType + " ? (Y/N)");
                    if (!retry) {
                        break;
                    }
                }
            }
            break;
        } while (true);
    }

    private static boolean promptConfirmation(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            String res = scanner.nextLine().trim().toUpperCase();
            if (res.equals("Y")) {
                return true;
            } else if (res.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input.Please select 'Y' or 'N'.");
            }
        }
    }
}
