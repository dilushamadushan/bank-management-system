package com.bank.models;

public abstract class Account {
    protected String accountNumber;
    protected String holderName;
    protected double balance;
    protected String type;

    public Account(String accountNumber, String holderName, double balance,  String type) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.type = type;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double showBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public abstract void withdraw(double amount);
}
