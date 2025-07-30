package com.bank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection con = null;

    public static Connection getConfig() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected to database successfully");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return con;
    }
}
