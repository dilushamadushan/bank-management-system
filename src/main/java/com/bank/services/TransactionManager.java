package com.bank.services;

import com.bank.models.Transfer;
import com.bank.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    public List<Transfer> getTransactions(String fromAcc) {
        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT * FROM history WHERE fromAcc = ?";

        try (Connection conn = Conn.getConfig();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fromAcc);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transfer tr = new Transfer(
                            rs.getString("fromAcc"),
                            rs.getString("toAcc"),
                            rs.getDouble("amount"),
                            rs.getString("remark"),
                            rs.getString("date")
                    );
                    transfers.add(tr);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }

        return transfers;
    }

}
