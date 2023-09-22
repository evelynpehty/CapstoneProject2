package objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.essentials.GetConn;
import com.validations.Validations;

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

    public void viewBalance(){
        System.out.println("Your " + getType() + " account " + getId() + " balance is: " + getBalance());
    }

    public void deposit(Scanner scanner){
        System.out.println("Please enter the amount to deposit: ");
        double amount = Validations.validateDouble(scanner);
        setBalance(getBalance() + amount);
        String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
        PreparedStatement stmt = GetConn.getPreparedStatement(sql);
        try {
            stmt.setDouble(1, getBalance());
            stmt.setInt(2, getId());
            stmt.execute();
            System.out.println(amount + " deposited!");
            System.out.println("New balance: " + getBalance());
            GetConn.closeConn();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error depositing");
        }
    }

    public void withdraw(Scanner scanner){
        System.out.println("Please enter the amount to withdraw: ");
        double amount = Validations.validateDouble(scanner);
        if (amount > getBalance()){
            System.out.println("Insufficient funds to withdraw. Returning to account menu...");
        } else{
            setBalance(getBalance() - amount);
            String sql = "UPDATE account SET account_balance = ? WHERE account_id = ?";
            PreparedStatement stmt = GetConn.getPreparedStatement(sql);
            try {
                stmt.setDouble(1, getBalance());
                stmt.setInt(1, getId());
                stmt.execute();
                System.out.println(amount + " withdrawn!");
                System.out.println("New balance: " + getBalance());
                GetConn.closeConn();
            } catch (SQLException e) {
                System.out.println("Error withdrawing");
            }
        }

    }
}
