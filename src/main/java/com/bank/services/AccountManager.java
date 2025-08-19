package com.bank.services;

import com.bank.models.Account;
import com.bank.models.Current;
import com.bank.models.Saving;
import com.bank.models.Transfer;
import com.bank.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AccountManager {
    private static Connection con = Conn.getConfig();

    public Account getAccountByUsername(String username){
        String accSql = "SELECT * FROM accounts a INNER JOIN users u ON a.account_number = u.account_number WHERE username = ?";
        try {
            PreparedStatement pst = con.prepareStatement(accSql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String type = rs.getString("account_type");
                String name = rs.getString("holder_name");
                String accountNumber = rs.getString("account_number");
                double balance = rs.getDouble("balance");

                if(type.equalsIgnoreCase("Savings")){
                   return new Saving(accountNumber,name,balance,type);
                }else if(type.equalsIgnoreCase("Current")){
                    return new Current(accountNumber,name,balance,type);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching account : "  + e.getMessage());
        }
        return null;
    }

    public Account checkTrasferAccount(String accountNumber){
        String accSql = "SELECT * FROM accounts WHERE account_number = ?";
        try {
            PreparedStatement pst = con.prepareStatement(accSql);
            pst.setString(1, accountNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String type = rs.getString("account_type");
                String name = rs.getString("holder_name");
                double balance = rs.getDouble("balance");

                if(type.equalsIgnoreCase("Savings")){
                    return new Saving(accountNumber,name,balance,type);
                }else if(type.equalsIgnoreCase("Current")){
                    return new Current(accountNumber,name,balance,type);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching account : "  + e.getMessage());
        }
        return null;
    }

    public void updateAccountBalance(Account account) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, account.getBalance());
            ps.setString(2, account.getAccountNumber());
            ps.executeUpdate();
        }
    }

    public void saveTransaction(Transfer transfer) throws SQLException {
        String insertSql = "INSERT INTO history (fromAcc, toAcc, amount, remark, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertSql)) {
            ps.setString(1, transfer.get_fromAcc());
            ps.setString(2, transfer.get_toAcc());
            ps.setDouble(3, transfer.get_amount());
            ps.setString(4, transfer.get_remarker());

            String dateStr = transfer.getDate();

            LocalDateTime dateTime;
            try {
                DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("HH.mm, dd MMMM yyyy");
                dateTime = LocalDateTime.parse(dateStr, oldFormatter);
            } catch (Exception e) {
                dateTime = LocalDateTime.now();
            }

            String mysqlDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            ps.setString(5, mysqlDate);

            ps.executeUpdate();
        }
    }


}
