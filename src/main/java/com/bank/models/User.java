package com.bank.models;

public abstract class User {
    protected String username;
    protected String nicNo;
    protected String accountNumber;
    protected String password;
    protected String contactNo;

    public User(String username, String nicNo, String accountNumber, String password, String contactNo) {
        this.username = username;
        this.nicNo = nicNo;
        this.accountNumber = accountNumber;
        this.password = password;
        this.contactNo = contactNo;
    }

    public String getUsername() {
        return username;
    }

    public String getNicNo() {
        return nicNo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNo() {
        return contactNo;
    }
}
