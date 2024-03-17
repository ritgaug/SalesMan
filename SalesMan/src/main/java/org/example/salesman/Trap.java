package org.example.salesman;

import java.awt.Point;

// Trap class
public class Trap {
    private int penalty;
    private Point location;

    public Trap(int penalty, Point location) {
        this.penalty = penalty;
        this.location = location;
    }

    public void trigger(Wallet wallet) {
        int result = wallet.deductMoney(penalty);
        if (result >= 0) {
            System.out.println("Trap activated! Lost " + penalty + " money.");
        } else {
            System.out.println("Not enough money to deduct!");
        }
    }

    public Point getLocation() {
        return location;
    }
}


