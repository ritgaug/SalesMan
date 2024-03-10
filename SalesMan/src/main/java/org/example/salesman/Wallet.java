package org.example.salesman;

// Wallet class
public class Wallet {
    private int balance;

    public Wallet() {
        this.balance = 0;
    }

    public void addMoney(int amount) {
        balance += amount;
    }

    public boolean deductMoney(int amount) {
        if (amount <= balance) {
            balance -= amount;
            return true; // Deduction successful
        }
        return false; // Not enough balance
    }

    public int getBalance() {
        return balance;
    }
}
