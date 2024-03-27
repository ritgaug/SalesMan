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
        wallet.deductMoney(penalty);
    }

    public Point getLocation() {
        return location;
    }
    public int getPenalty(){
        return penalty;
    }
}


