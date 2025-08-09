package com.bank.services;

import com.bank.models.Account;
import com.bank.models.Current;
import com.bank.models.Saving;
import com.bank.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private static Connection con = Conn.getConfig();

    public static Account getAccount(String username){
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
}
