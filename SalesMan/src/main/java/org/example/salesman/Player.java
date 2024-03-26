package org.example.salesman;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Player {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private int playerNumber;
    private ArrayList<ValuableTreasure> treasuresList = new ArrayList<ValuableTreasure>(8);
    private ArrayList weaponList = new ArrayList<>();
    private int playerStrength;
    private Wallet playerWallet;
    private Circle shape;
    private Color color;
    private int xCoordinate;
    private int yCoordinate;
    private final int radius;
    private int health;
    private int treasureDiscoverScore=0;
    public static boolean failToMove = false;

    public Player(int playerNumber, Color color, int playerStrength, Wallet playerWallet, ArrayList treasuresList, ArrayList weaponList, int initialX, int initialY, int radius) {
        this.playerNumber = playerNumber;
        this.shape = new Circle(radius);
        this.color = color;
        this.shape.setFill(color);
        this.playerStrength = playerStrength;
        this.playerWallet = playerWallet;
        this.treasuresList = treasuresList;
        this.weaponList = weaponList;
        this.xCoordinate = initialX;
        this.yCoordinate = initialY;
        this.radius = radius;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Circle getShape(double cellSize) {
        shape.setRadius(cellSize / 2);
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        shape.setFill(color);
    }

    public int getPlayerStrength() {
        return playerStrength;
    }

    public Wallet getPlayerWallet() {
        return playerWallet;
    }

    public ArrayList<ValuableTreasure> getTreasuresList() {
        return treasuresList;
    }

    public ArrayList<Weapon> getWeaponList() {
        return weaponList;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    public void moveRight() {
        if (xCoordinate < GRID_SIZE - 1) {
            xCoordinate++;
        } else {
            failToMove = true;
            System.out.println("Can't move right!");
        }
    }

    public void moveLeft() {
        if (xCoordinate > 0) {
            xCoordinate--;
        } else {
            failToMove = true;
            System.out.println("Can't move left!");
        }
    }

    public void moveUp() {
        if (yCoordinate > 0) {
            yCoordinate--;
        } else {
            failToMove = true;
            System.out.println("Can't move up!");
        }
    }

    public void moveDown() {
        if (yCoordinate < GRID_SIZE - 1) {
            yCoordinate++;
        } else {
            failToMove = true;
            System.out.println("Can't move down!");
        }
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

    public void addStrength(Weapon weapon) {
        playerStrength += weapon.getStrength();
    }
    public void reduceStrength(int strengthToReduce) {
        playerStrength -= strengthToReduce;
        if (playerStrength < 0) {
            playerStrength = 0; // Ensure strength doesn't go negative
        }
    }
    //lets users add money to the player wallet
    public void addMoney(int value){
        playerWallet.addMoney(value);
    }
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setPlayerStrength(int playerStrength) {
        this.playerStrength = playerStrength;
    }

    public void addTreasure(ValuableTreasure a){
        treasuresList.add(a);
    }
    public void addTreasureDiscoveryScore(){
        treasureDiscoverScore=treasureDiscoverScore+1;
    }
    public void setTreasureDiscoveryScore(int treasureDiscoverScore){
        this.treasureDiscoverScore=treasureDiscoverScore;
    }
    public int getTreasureDiscoverScore(){
        return treasureDiscoverScore;
    }

}
