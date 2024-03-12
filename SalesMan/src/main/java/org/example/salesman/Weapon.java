package org.example.salesman;


// Weapon class
class Weapon {
    private int cost;
    private int strength;
    private String name;

    Weapon(int strength, String name, int cost) {
        this.strength = strength;
        this.name = name;
        this.cost=cost;
    }

    int getStrength() {
        return strength;
    }

    String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}

