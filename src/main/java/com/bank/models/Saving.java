package com.bank.models;

public class Saving extends Account{

    public Saving(String accountNumber, String holderName, double balance, String type) {
        super(accountNumber, holderName, balance, type);
    }

    @Override
    public void withdraw(double amount) {
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        balance -= amount;
    }
}
