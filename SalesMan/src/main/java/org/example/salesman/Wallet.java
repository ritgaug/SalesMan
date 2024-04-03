package org.example.salesman;

// Wallet class
public class Wallet {
    private int balance;

    public Wallet() {
        this.balance =1000;
    }

    public void addMoney(int amount) {
        balance += amount;
    }

    public int deductMoney(int amount) {
            balance -= amount;
        return balance;
    }

    public int getBalance() {
        return balance;
    }
}
