package org.example.salesman;

import java.util.Random;

// House class
public class House {
    private boolean isWall;
    private boolean isTrap;

    public House() {
        this.isWall = false; // Default value, not a wall
        this.isTrap = new Random().nextBoolean(); // Randomly determine if the house has a trap
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public void setTrap(boolean isTrap) {
        this.isTrap = isTrap;
    }
}


