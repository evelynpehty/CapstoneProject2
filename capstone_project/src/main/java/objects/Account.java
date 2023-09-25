package objects;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.essentials.GetConn;
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
            e.printStackTrace();
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void viewBalance() {
        System.out.println("Your " + getType() + " account " + getId() + " balance is: " + getBalance());
    }

    public void deposit(Scanner scanner) {
        System.out.println("Please enter the amount to deposit: ");
        double amount = Validation.validateDouble(scanner);
        setBalance(getBalance() + amount);
        String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
        try {
            PreparedStatement stmt = GetConn.getPreparedStatement(sql);
            stmt.setDouble(1, getBalance());
            stmt.setInt(2, getId());
            stmt.execute();
            GetConn.closeConn();

            sql = "INSERT INTO transaction VALUES (transaction_id_seq.nextval, ?, ?, ?, ?, null)";
            stmt = GetConn.getPreparedStatement(sql);
            stmt.setInt(1, getId());
            stmt.setString(2, "Deposit");
            stmt.setDouble(3, amount);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.execute();
            
            System.out.println("Successfully deposited $" + amount);
            // stmt.setInt(5, );not needed
            GetConn.closeConn();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error depositing");
        }
    }

    public void withdraw(Scanner scanner) {
        System.out.println("Please enter the amount to withdraw: ");
        double amount = Validation.validateDouble(scanner);
        if (amount > getBalance()) {
            System.out.println("Insufficient funds to withdraw. Returning to account menu...");
        } else {
            setBalance(getBalance() - amount);
            String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
            try {
                PreparedStatement stmt = GetConn.getPreparedStatement(sql);
                stmt.setDouble(1, getBalance());
                stmt.setInt(2, getId());
                stmt.execute();
                GetConn.closeConn();
                
                sql = "INSERT INTO transaction VALUES (transaction_id_seq.nextval, ?, ?, ?, ?, null)";
                stmt = GetConn.getPreparedStatement(sql);
                stmt.setInt(1, getId());
                stmt.setString(2, "Withdraw");
                stmt.setDouble(3, amount);
                stmt.setDate(4, Date.valueOf(LocalDate.now()));
                stmt.execute();
                // stmt.setInt(5, );not needed
                System.out.println("Successfully withdrew $" + amount);

                GetConn.closeConn();

            } catch (SQLException e) {
                System.out.println("Error withdrawing");
            }
        }
    }

    public void transfer(Scanner scanner) {
        System.out.println("Transfer fund to another account...");
        System.out.println("Please enter the following details.");
        System.out.println("Enter the payee's account ID: ");
        Account payee = getAccount(Validation.validateInt(scanner));
    }

    private Account getAccount(int id) {
        PreparedStatement statement = GetConn
                .getPreparedStatement("SELECT account_id FROM account WHERE account_id = ?");
        Account account = null;
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new Account(resultSet.getInt("account_id"));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        GetConn.closeConn();
        return account;
    }
}
