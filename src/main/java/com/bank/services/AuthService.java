package com.bank.services;
import com.bank.models.User;
import com.bank.utils.BUtils;
import com.bank.utils.Conn;
import com.bank.utils.HashUtil;

import javax.swing.*;
import java.security.MessageDigest;
import java.sql.*;

public class AuthService {
    private Connection con = Conn.getConfig();

    public boolean register(User user) throws SQLException {
        String checkSql = "SELECT account_number FROM accounts  WHERE account_number = ?";
        PreparedStatement ps = con.prepareStatement(checkSql);
        ps.setString(1, user.getAccountNumber());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            BUtils bu =  new BUtils();
            bu.showMessage("Account Not Found", "No matching bank account found. Please open a bank account first.");
        }

        String sql = "INSERT INTO users (username, password, nic_no,contact_no, account_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2,HashUtil.hashPassword(user.getPassword()));
            stmt.setString(3,user.getNicNo());
            stmt.setString(4, user.getContactNo());
            stmt.setString(5,user.getAccountNumber());
        return stmt.executeUpdate() > 0;
    }

    public boolean login(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            BUtils bu =  new BUtils();
            bu.showMessage("Error", "Please fill all the fields.");
        }

        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = HashUtil.hashPassword(password);

                return MessageDigest.isEqual(storedHash.getBytes(), inputHash.getBytes());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return false;
    }
}
