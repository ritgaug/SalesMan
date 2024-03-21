package org.example.salesman;

import java.util.ArrayList;
import java.util.List;

// Market class
class Market {
    private List<Weapon> weapons;
    private String name;
    private int row;
    private int col;

    Market(String name, int row, int col) {
        this.name = name;
        this.weapons = new ArrayList<>();
        this.row = row;
        this.col = col;
    }

    void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    String getName() {
        return name;
    }

    List<Weapon> getWeapons() {
        return weapons;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }
    // Market emoji
    private String getMarketEmoji() {
        return "\uD83C\uDFEA"; // Market emoji
    }
}

