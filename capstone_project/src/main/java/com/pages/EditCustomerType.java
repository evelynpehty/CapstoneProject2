package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.validations.chkText;
import com.validations.chkDigits;
import com.validations.chkEmail;
import com.essentials.GetConn;
import com.styles.FontStyle;
import com.validations.MenuChoices;

public class EditCustomerType {
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
        ArrayList<String> editType = new ArrayList<>(2);
        // add elements to the ArrayList
        // String[] editType = arrayList.toArray(new String[arrayList.size()]);
        // ArrayList editType = new ArrayList<>(2);
        // array editType = [colname, displayname]
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

            System.out.printf("%1s %-8s %n", "",
                    FontStyle.bold + FontStyle.UNDERLINE + "Customer Details: " + FontStyle.reset);
            System.out.println("+---+-------------------+--------------------+");
            System.out.printf("| %1s | %-30s | %-31s |%n", "",
                    FontStyle.bold + FontStyle.green + "NRIC" + FontStyle.reset,
                    FontStyle.bold + FontStyle.green + nric + FontStyle.reset);
            System.out.printf("| %1s | %-26s | %-27s |%n", "1", FontStyle.blue + "First Name" + FontStyle.reset,
                    FontStyle.blue + currentFirstName + FontStyle.reset);
            System.out.printf("| %1s | %-26s | %-27s |%n", "2", FontStyle.blue + "Last Name" + FontStyle.reset,
                    FontStyle.blue + currentLastName + FontStyle.reset);
            System.out.printf("| %1s | %-26s | %-27s |%n", "3", FontStyle.blue + "Phone" + FontStyle.reset,
                    FontStyle.blue + currentPhoneNumber + FontStyle.reset);

            if (currentEmail == null) {
                System.out.printf("| %1s | %-26s | %-27s |%n", "4", FontStyle.blue + "Email" + FontStyle.reset,
                        "" + FontStyle.reset);
            } else {
                System.out.printf("| %1s | %-26s | %-27s |%n", "4", FontStyle.blue + "Email" + FontStyle.reset,
                        FontStyle.blue + currentEmail + FontStyle.reset);
            }

            System.out.printf("| %1s | %-26s | %-27s |%n", "5", FontStyle.blue + "Nationality" + FontStyle.reset,
                    FontStyle.blue + currentNationality + FontStyle.reset);
            System.out.printf("| %1s | %-26s | %-18s |%n", "6", FontStyle.red + "Back" + FontStyle.reset, "");
            System.out.println(FontStyle.reset + "+---+-------------------+--------------------+");

            choice = MenuChoices.getUserChoice(scanner, 6);

            switch (choice) {
                case 1:
                    editType.add(0, "FIRST_NAME");
                    editType.add(1, "First Name");
                    currentInformation = currentFirstName;

                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;
                case 2:
                    editType.add(0, "LAST_NAME");
                    editType.add(1, "Last Name");
                    currentInformation = currentLastName;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 3:
                    editType.add(0, "PHONE_NUMBER");
                    editType.add(1, "Phone Number");
                    currentInformation = currentPhoneNumber;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 4:
                    editType.add(0, "EMAIL");
                    editType.add(1, "Email");
                    currentInformation = currentEmail;
                    isEmail = true;
                    ModifyInfor(nric, scanner, currentInformation, editType, isEmail);
                    break;

                case 5:
                    editType.add(0, "NATIONALITY");
                    editType.add(1, "Nationality");
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

    private static void ModifyInfor(String nric, Scanner scanner, String currentInformation, ArrayList<String> editType,
            boolean isEmail) {
        PreparedStatement pstmt;
        do {
            System.out.println(FontStyle.blue + "Current Information: " + currentInformation + FontStyle.reset);
            System.out.println("Enter new Information: ");

            while (true) {
                String NewInfor = scanner.nextLine();
                boolean isConfirmed = promptConfirmation(scanner,
                        FontStyle.green + FontStyle.BOLD + "Confirm changes (Y/N)? " + FontStyle.reset);
                if (isConfirmed) {
                    boolean isValid = true;
                    if (editType.get(0) == "EMAIL") {
                        isValid = chkEmail.checkEmailFormat(NewInfor);
                    }
                    if (editType.get(0) == "PHONE_NUMBER") {
                        isValid = chkDigits.checkDigits(NewInfor);
                    }

                    if (!isValid) {
                        System.out.println(FontStyle.BOLD + FontStyle.red + "Invalid " + editType.get(1)
                                + " format. Please enter a valid input." + FontStyle.reset);
                        continue;
                    }

                    String sql = "UPDATE CUSTOMER SET " + editType.get(0) + " = ? WHERE NRIC = UPPER(?)";
                    try {
                        pstmt = GetConn.getPreparedStatement(sql);
                        pstmt.setString(1, NewInfor);
                        pstmt.setString(2, nric);
                        pstmt.execute();
                        System.out.println(FontStyle.BOLD + FontStyle.green + editType.get(1) + " updated to: "
                                + NewInfor + FontStyle.reset);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                } else {
                    boolean retry = promptConfirmation(scanner, "Continue to edit " + editType.get(1) + " ? (Y). "
                            + FontStyle.red + "To exit (N)." + FontStyle.reset);
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
