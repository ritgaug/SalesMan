package org.example.salesman;


// Weapon class
class Weapon {
    private int cost;
    private int strength;
    private String name;

    Weapon(int strength, String name) {
        this.strength = strength;
        this.name = name;
    }

    int getDamage() {
        return strength;
    }

    String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}

