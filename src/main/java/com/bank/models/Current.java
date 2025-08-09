package com.bank.models;

public class Current extends Account{

    private double overdraftLimit = 500;

    public Current(String accountNumber, String holderName, double balance, String type) {
        super(accountNumber, holderName, balance, type);
    }

    @Override
    public void withdraw(double amount) {
        if(amount <= balance + overdraftLimit) {
            balance = balance - amount;
        }else {
            System.out.println("Insufficient balance");
        }
    }
}
