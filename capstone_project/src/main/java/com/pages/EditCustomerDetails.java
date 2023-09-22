package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.validations.MenuChoices;
import com.essentials.GetConn;

public class EditCustomerDetails {
    static String currentFirstName;
    static String currentLastName;
    static String currentPhoneNumber;
    static String currentEmail;
    static String currentNationality;

    public static void show(Scanner scanner, String nric) {
        String sql = "SELECT * FROM CUSTOMERS WHERE NRIC = ?";
        PreparedStatement stmt = GetConn.getPreparedStatement(sql);

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

        int choice;
        boolean isExit = false;
        while (!isExit) {
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
                    editFirstName(nric, scanner);
                    break;
                case 2:
                    editLastName(nric, scanner);
                    break;

                case 3:
                    editGender(nric, scanner);
                    break;

                case 4:
                    editPhoneNumber(nric, scanner);
                    break;

                case 5:
                    editNewEmail(nric, scanner);
                    break;

                case 6:
                    editNewNationality(nric, scanner);
                    break;

                case 7:
                    isExit = MenuChoices.yesnoConfirmation(scanner,
                            "Are you sure you want to exit without saving changes? Y/N: ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    };

    private static void editFirstName(String nric, Scanner scanner) {

        do {
            System.out.println("Current First Name: " + currentFirstName);

            System.out.println("Enter new first name: ");
            currentFirstName = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                System.out.println("First Name Updated to: " + currentFirstName);
                break;
            } else {
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
        } while (true);
    }

    private static void editLastName(String nric, Scanner scanner) {
        String newLastName = null;
        do {
            System.out.println("Current Last Name: " + newLastName);

            System.out.println("Enter New Last name: ");
            newLastName = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                System.out.println("Last Name Updated To: " + newLastName);
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

    private static void editPhoneNumber(String nric, Scanner scanner) {
        String newPhoneNumber = null;
        do {
            System.out.println("Current Phone Number: " + newPhoneNumber);

            System.out.println("Enter New Phone Number: ");
            newPhoneNumber = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                System.out.println("Phone Number Updated to: " + newPhoneNumber);
                break;
            } else {
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
        } while (true);
    }

    private static void editNewEmail(String nric, Scanner scanner) {
        String newEmail = null;
        do {
            System.out.println("Current Email: " + newEmail);

            System.out.println("Enter new Email: ");
            newEmail = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                System.out.println("Email Updated to: " + newEmail);
                break;
            } else {
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
        } while (true);
    }

    private static void editNewNationality(String nric, Scanner scanner) {
        String newNationality = null;
        do {
            System.out.println("Current Nationaility: " + newNationality);

            System.out.println("Enter new Nationaility: ");
            newNationality = scanner.nextLine();

            boolean isConfirmed = promptConfirmation(scanner, "Confirm changes (Y/N)? ");
            if (isConfirmed) {
                System.out.println("Nationaility Updated to: " + newNationality);
                break;
            } else {
                boolean retry = promptConfirmation(scanner, "Continue to edit? (Y/N)");
                if (!retry) {
                    break;
                }
            }
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
