package org.example.salesman;


// Weapon class
class Weapon {
    private int cost;
    private int strength;
    private String name;
    private String emoji;

    Weapon(int strength, String name, int cost , String emoji) {
        this.strength = strength;
        this.name = name;
        this.cost=cost;
        this.emoji = emoji;
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
    public String getEmoji(){
        return emoji;
    }
    @Override
    public String toString() {
        return emoji;
    }
}

