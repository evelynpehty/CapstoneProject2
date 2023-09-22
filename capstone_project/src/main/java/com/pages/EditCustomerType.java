package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.validations.MenuChoices;
import com.essentials.GetConn;

public class EditCustomerType {

    public static void show(Scanner scanner, String nric) {
        String currentFirstName;
        String currentLastName;
        String currentPhoneNumber;
        String currentEmail = "";
        String currentNationality;
        String sql = "SELECT * FROM CUSTOMER WHERE NRIC = ?";
        PreparedStatement stmt = GetConn.getPreparedStatement(sql);

        String currentInformation = "";
        String editType = "";
        int choice;
        boolean isExit = false;
        while (!isExit) {
            try {
                stmt.setString(1, nric);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    currentFirstName = resultSet.getString("FIRST_NAME");
                    currentLastName = resultSet.getString("LAST_NAME");
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
            System.out.println("NRIC: " + nric);
            System.out.println("-----------------------------------------");
            System.out.println("Please Choose The Option You Like To Edit: ");
            System.out.println("1. EDIT FIRST NAME: ");
            System.out.println("2. EDIT LAST NAME: ");
            System.out.println("3. GENDER: ");
            System.out.println("4. EDIT PHONE NUMBER: ");
            System.out.println("5. EDIT EMAIL: ");
            System.out.println("6. EDIT NATIONALITY: ");
            System.out.println("7. Back");

            choice = MenuChoices.getUserChoice(scanner, 7);

            switch (choice) {
                case 1:
                    editType = "FIRST_NAME";
                    currentInformation = currentFirstName;
                    // editFirstName(nric, scanner);
                    break;
                case 2:
                    editType = "LAST_NAME";
                    currentInformation = currentLastName;
                    break;

                case 3:
                    editGender(nric, scanner);
                    break;

                case 4:
                    editType = "PHONE_NUMBER";
                    currentInformation = currentPhoneNumber;
                    break;

                case 5:
                    editNewEmail(nric, scanner, currentEmail);
                    currentInformation = currentEmail;
                    break;

                case 6:
                    editType = "NATIONALITY";
                    currentInformation = currentNationality;

                    break;

                case 7:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to exit without saving changes? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            ModifyInfor(nric, scanner, currentInformation, editType);
        }
    }

    private static void ModifyInfor(String nric, Scanner scanner, String currentInformation, String editType) {
        PreparedStatement pstmt;
        do {
            System.out.println("Current Information: " + currentInformation);
            System.out.println("Enter new Information: ");
            String NewInfor = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                String sql = "UPDATE CUSTOMER SET " + editType + " = ? WHERE NRIC = ?";
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
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
        } while (true);
    }

    private static void editNewEmail(String nric, Scanner scanner, String currentEmail) {
        PreparedStatement pstmt;
        do {
            System.out.println("Current Email: " + currentEmail);
            System.out.println("Enter new Email: ");
            String newEmail = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                String sql = "UPDATE CUSTOMER SET " + currentEmail + " = ? WHERE NRIC = ? ";
                try {
                    pstmt = GetConn.getPreparedStatement(sql);
                    pstmt.setString(1, newEmail);
                    pstmt.setString(2, nric);
                    pstmt.execute();
                    System.out.println("Email Updated to: " + newEmail);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
        } while (true);
    }

    private static void editGender(String nric, Scanner scanner) {
        System.out.println("Don't need booking. Tmr appointment 9am.");
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
