package org.example.salesman;


// Weapon class
class Weapon {
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
}

