package objects;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

import com.essentials.GetConn;
import com.styles.Console;
import com.styles.FontStyle;
import com.validations.MenuChoices;
import com.validations.Validation;

public class Account {
    private int id;
    private String nric;
    private double balance;
    private AccountType type;
    private boolean active;
    private LocalDate createDate;

    public Account(int id) {
        PreparedStatement statement = GetConn.getPreparedStatement("SELECT * FROM account WHERE account_id = ?");
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            this.id = resultSet.getInt("account_id");
            this.nric = resultSet.getString("nric");
            this.balance = resultSet.getDouble("account_balance");

            switch (resultSet.getString("account_type")) {
                case "Savings":
                    this.type = AccountType.SAVINGS;
                    break;
                case "Checking":
                    this.type = AccountType.CHECKING;
                    break;
                case "Fixed Deposit":
                    this.type = AccountType.FIXED_DEPOSIT;
                    break;
                
                default:
                    System.out.println("Invalid account type. Setting to default savings account type.");
                    this.type = AccountType.SAVINGS;
                    break;
            }

            this.active = resultSet.getInt("isaccountactive") == 1 ? true : false;
            this.createDate = resultSet.getDate("account_created_date").toLocalDate();
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Error occurred while reading the account.");
        }
        GetConn.closeConn();
    }

    public int getId() {
        return id;
    }

    public String getNric() {
        return nric;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    private void setActive(boolean active) {
        this.active = active;
    }

    public void viewBalance(Scanner scanner){
        Console.clear();
        System.out.println(FontStyle.green + "Your " + this.getType() + " account " + this.getId() + " balance is: " + this.getBalance() + FontStyle.reset);
        Console.pause(scanner);
    }

    public void deposit(Scanner scanner){
        Console.clear();
        if (!this.isActive()) {
            System.out.println("This account is inactive. Please activate it before making a deposit.");
        } else {
            System.out.println("Deposit fund into this account...");
            System.out.println("Please enter the following details.");
            System.out.print(FontStyle.blue + "Enter the amount to deposit: " + FontStyle.reset);
            double amount = Validation.validateDouble(scanner);
            this.setBalance(this.getBalance() + amount);
    
            try {
                String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
                PreparedStatement stmt = GetConn.getPreparedStatement(sql);
                stmt.setDouble(1, this.getBalance());
                stmt.setInt(2, this.getId());
                stmt.executeUpdate();
                GetConn.closeConn();
    
                sql = "INSERT INTO transaction VALUES (transaction_id_seq.nextval, ?, ?, ?, ?, null)";
                stmt = GetConn.getPreparedStatement(sql);
                stmt.setInt(1, this.getId());
                stmt.setString(2, "Deposit");
                stmt.setDouble(3, amount);
                stmt.setDate(4, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
                GetConn.closeConn();
    
                System.out.println(FontStyle.green + "Successfully deposited $" + amount + FontStyle.reset);
            } catch (SQLException e) {
                System.err.println("Error occurred while depositing.");
            }
        }
        Console.pause(scanner);
    }

    public void withdraw(Scanner scanner){
        Console.clear();
        if (!this.isActive()) {
            System.out.println(FontStyle.red + "This account is inactive. Please activate it before making a withdrawal." + FontStyle.reset);
        } else {
            System.out.println("Withdraw fund from this account...");
            System.out.println("Please enter the following details.");
            System.out.print(FontStyle.blue + "Enter the amount to withdraw: " + FontStyle.reset);
            double amount = Validation.validateDouble(scanner);
    
            if (amount > this.getBalance()){
                System.out.println(FontStyle.red + "Insufficient fund. Returning to account..." + FontStyle.reset);
            } else {
                this.setBalance(this.getBalance() - amount);
    
                try {
                    String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
                    PreparedStatement stmt = GetConn.getPreparedStatement(sql);
                    stmt.setDouble(1, this.getBalance());
                    stmt.setInt(2, this.getId());
                    stmt.executeUpdate();
                    GetConn.closeConn();
                    
                    sql = "INSERT INTO transaction VALUES (transaction_id_seq.nextval, ?, ?, ?, ?, null)";
                    stmt = GetConn.getPreparedStatement(sql);
                    stmt.setInt(1, this.getId());
                    stmt.setString(2, "Withdraw");
                    stmt.setDouble(3, amount);
                    stmt.setDate(4, Date.valueOf(LocalDate.now()));
                    stmt.executeUpdate();
                    GetConn.closeConn();
    
                    System.out.println(FontStyle.green + "Successfully withdrew $" + amount + FontStyle.reset);
                } catch (SQLException e) {
                    System.err.println(FontStyle.red + "Error occurred while withdrawing." + FontStyle.reset);
                }
            }
        }
        Console.pause(scanner);
    }

    public void transfer(Scanner scanner) {
        Console.clear();
        if (!this.isActive()) {
            System.out.println(FontStyle.red + "This account is inactive. Please activate it before making a transfer" + FontStyle.reset);
        } else {
            System.out.println("Transfer fund to another account...");
            System.out.println("Please enter the following details.");
            System.out.print(FontStyle.blue + "Enter the payee's account ID: " + FontStyle.reset); 
            Account payee = getAccount(Validation.validateInt(scanner));
    
            if (Objects.isNull(payee)) {
                System.out.println(FontStyle.red + "Account ID could not be found." + FontStyle.reset);
            } else if (!payee.isActive()) {
                System.out.println(FontStyle.red + "The payee's account is inactive. Fund cannot be transferred to the account." + FontStyle.reset);
            } else {
                System.out.print(FontStyle.blue + "Enter the amount to transfer: " + FontStyle.reset);
                double amount = Validation.validateDouble(scanner);
    
                if (amount > this.getBalance()) {
                    System.out.println(FontStyle.red + "Insufficient fund. Returning to account..." + FontStyle.reset);
                } else {
                    this.setBalance(this.getBalance() - amount);
                    payee.setBalance(payee.getBalance() + amount);
    
                    try {
                        PreparedStatement statement = GetConn.getPreparedStatement("UPDATE account SET account_balance = ? WHERE account_id = ?");
                        statement.setDouble(1, this.getBalance());
                        statement.setInt(2, this.getId());
                        statement.executeUpdate();
                        statement.setDouble(1, payee.getBalance());
                        statement.setInt(2, payee.getId());
                        statement.executeUpdate();
                        GetConn.closeConn();
    
                        statement = GetConn.getPreparedStatement("INSERT INTO transaction VALUES(transaction_id_seq.NEXTVAL, ?, ?, ?, ?, ?)");
                        statement.setInt(1, this.getId());
                        statement.setString(2, "Transfer");
                        statement.setDouble(3, amount);
                        statement.setDate(4, Date.valueOf(LocalDate.now()));
                        statement.setInt(5, payee.getId());
                        statement.executeUpdate();
                        GetConn.closeConn();
    
                        System.out.println(FontStyle.green + "Fund has been transferred successfully." + FontStyle.reset);
                    } catch (SQLException e) {
                        System.err.println(FontStyle.red + "Error occurred while transferring." + FontStyle.reset);
                    }
                }
            }
        }
        Console.pause(scanner);
    }

    private Account getAccount(int id) {
        PreparedStatement statement = GetConn.getPreparedStatement("SELECT account_id FROM account WHERE account_id = ?");
        Account account = null;
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new Account(resultSet.getInt("account_id"));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Error occurred while reading the account.");
        }
        GetConn.closeConn();
        return account;
    }

    public void toggleActive(Scanner scanner) {
        Console.clear();
        System.out.println("This account is " + (this.isActive() ? "active" : "inactive") + " now.");
        System.out.print(FontStyle.blue + "Do you want to " + (this.isActive() ? "deactivate" : "activate") + " it (Y/N)? " + FontStyle.reset);
        boolean toggle = MenuChoices.yesnoConfirmation(scanner, "");

        if (toggle) {
            this.setActive(!this.isActive());

            PreparedStatement statement = GetConn.getPreparedStatement("UPDATE account SET isaccountactive = ? WHERE account_id = ?");
            try {
                statement.setInt(1, this.isActive() ? 1 : 0);
                statement.setInt(2, this.getId());
                statement.executeUpdate();

                System.out.println(FontStyle.green + "This account has been " + (this.isActive() ? "activated" : "deactivated") + " successfully." + FontStyle.reset);
            } catch (SQLException e) {
                // TODO: handle exception
                System.err.println(FontStyle.red + "Error occurred while changing the status of account." + FontStyle.reset);
            }
            GetConn.closeConn();
        }
        Console.pause(scanner);
    }

    public void viewTransaction(Scanner scanner) {
        Console.clear();
        PreparedStatement statement = GetConn.getPreparedStatement("SELECT * FROM transaction WHERE account_id = ? OR transaction_party_accountid = ? ORDER BY transaction_datetime DESC");
        try {
            statement.setInt(1, this.getId());
            statement.setInt(2, this.getId());
            ResultSet resultSet = statement.executeQuery();

            System.out.printf("%15s | %-6s | %10s | %15s | %15s | %15s|%n", "Transaction ID", "ID", "Type", "Value", "Date", "Third Party ID");
            while (resultSet.next()) {
                System.out.printf(
                    "%15d | %-6d | %10S | %15.2f | %15s | %15d|%n", resultSet.getInt("transaction_id"), resultSet.getInt("account_id"),
                    resultSet.getString("transaction_type"), resultSet.getDouble("transaction_value"),
                    resultSet.getDate("transaction_datetime").toLocalDate(), resultSet.getInt("transaction_party_accountid")
                );
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(FontStyle.red + "Error occurred while retrieving the transaction history." + FontStyle.reset);
        }
        GetConn.closeConn();
        Console.pause(scanner);
    }
}
