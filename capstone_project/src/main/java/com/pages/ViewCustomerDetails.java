package com.pages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.essentials.GetConn;
import com.styles.Console;
import com.styles.FontStyle;

public class ViewCustomerDetails {
    public static void show(Scanner scanner, String nric) {
        try {
            Console.clear();
            String sqlQuery = "select * from customer where upper(nric) = upper(?)";
            PreparedStatement pstmt = GetConn.getPreparedStatement(sqlQuery);
            pstmt.setString(1, nric);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dobString = dob.format(formatter);

                System.out.println();
                System.out.println(FontStyle.bold + FontStyle.green + "Customer Details" + FontStyle.reset);
                System.out.println("+---+---------------------+---------------------+");
                System.out.printf("| %1s | %-19s | %-19s |%n", "1", "Date of Birth", dobString);
                System.out.printf("| %1s | %-19s | %-19s |%n", "2", "NRIC", resultSet.getString("nric"));
                System.out.printf("| %1s | %-19s | %-19s |%n", "3", "First Name", resultSet.getString("first_name"));
                System.out.printf("| %1s | %-19s | %-19s |%n", "4", "Last Name", resultSet.getString("last_name"));
                System.out.printf("| %1s | %-19s | %-19s |%n", "5", "Gender", resultSet.getString("gender"));
                System.out.printf("| %1s | %-19s | %-19s |%n", "6", "Phone", resultSet.getString("phone_number"));

                String email = resultSet.getString("email");
                if (email == null) {
                    System.out.printf("| %1s | %-19s | %-19s |%n", "7", "Email", "");
                } else {
                    System.out.printf("| %1s | %-19s | %-19s |%n", "7", "Email", email);
                }

                System.out.printf("| %1s | %-19s | %-19s |%n", "8", "Nationality", resultSet.getString("nationality"));
                System.out.println("+---+---------------------+---------------------+");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            GetConn.closeConn();
        }
        Console.pause(scanner);
    }
}
