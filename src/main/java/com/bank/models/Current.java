package com.bank.models;

public class Current extends Account{

    private double overdraftLimit = 500;

    public Current(String accountNumber, String holderName, double balance, String type) {
        super(accountNumber, holderName, balance, type);
    }

    @Override
    public void withdraw(double amount) {
        if(balance + overdraftLimit < amount) {
            throw new IllegalArgumentException("Insufficient funds including overdraft limit.");
        }
        balance -= amount;
    }
}
