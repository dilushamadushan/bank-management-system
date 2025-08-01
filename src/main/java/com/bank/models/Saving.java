package com.bank.models;

public class Saving extends Account{

    public Saving(String accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if(amount <= balance) {
            balance = balance - amount;
        }else  {
            System.out.println("Insufficient balance");
        }
    }
}
