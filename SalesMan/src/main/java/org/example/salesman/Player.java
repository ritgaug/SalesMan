package org.example.salesman;

import javafx.scene.control.Alert;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Player {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private int playerNumber;
    private ArrayList<String> treasuresList = new ArrayList<String>(8);
    private ArrayList weaponList = new ArrayList<>();
    private int playerStrength;
    private Wallet playerWallet;
    private Circle shape;
    private Color color;
    private int xCoordinate;
    private int yCoordinate;
    private final int radius;
    private int  point;
    private int treasureDiscoverScore=0;
    public static boolean failToMove = false; // if player tries to moves out of grid

    public Player(int playerNumber, Color color, int playerStrength, Wallet playerWallet, ArrayList treasuresList, ArrayList weaponList, int initialX, int initialY, int radius , int point ) {
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
        this.point = point;
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

    public ArrayList<String> getTreasuresList() {
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
    public void updatePoint(int x){
        point += x;
    }
    public int getPoint(){
        return point;
    }

    public void moveRight() {
        if (yCoordinate >= 10) {
            incorrectMove();
            failToMove = true;
        }else if (xCoordinate < GRID_SIZE - 1) {
            xCoordinate++;
        } else {
            failToMove = true;
            incorrectMove();
        }
    }

    public void moveLeft() {
        if (yCoordinate >= 10) {
            incorrectMove();
            failToMove = true;
        }else if (xCoordinate > 0) {
            xCoordinate--;
        } else {
            failToMove = true;
            incorrectMove();
        }
    }

    public void moveUp() {
        if (xCoordinate >= 10){
            incorrectMove();
            failToMove = true;
        } else if (yCoordinate > 0) {
            yCoordinate--;
        } else {
            failToMove = true;
            incorrectMove();
        }
    }

    public void moveDown() {
        if (xCoordinate >= 10) {
            incorrectMove();
            failToMove = true;
        } else if (yCoordinate < GRID_SIZE - 1) {
            yCoordinate++;
        } else {
            failToMove = true;
            incorrectMove();
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

    public void addTreasure(String a){
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
    public void incorrectMove(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Illegal Play");
        alert.setHeaderText(null);
        alert.setContentText("ERROR: Move outside of map. Try again.");
        alert.showAndWait();
    }

}
