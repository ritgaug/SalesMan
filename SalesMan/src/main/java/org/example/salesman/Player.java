package org.example.salesman;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.paint.Color;




public class Player{
    private static final int GRID_SIZE = 10;
    private ArrayList<ValuableTreasure> treasuresList =new ArrayList<ValuableTreasure>(8);
    private int playerStrength;
    private int playerNumber;
    private Wallet playerWallet = new Wallet();
    private Circle shape;
    private Color color;
    private int xCoordinate;
    private int yCoordinate;
    private final int radius;

    // Constructor for each player including wallet , shape , color ,strength and treasures
    public Player(int playerNumber, Color color, int playerStrength, Wallet playerWallet , ArrayList treasuresList , int initialX , int initialY , int radius) {
        this.playerNumber = playerNumber;
        this.shape = new Circle(radius);
        this.color = color;
        this.shape.setFill(color);
        this.playerStrength = playerStrength;
        this.playerWallet = playerWallet ;
        this.treasuresList = treasuresList ;
        this.xCoordinate = initialX;
        this.yCoordinate = initialY;
        this.radius = radius;


    }
    public int getPlayerNumber() {
        return playerNumber;
    }
    public Circle getShape(double cellSize) {
        // Set the radius of the circle based on the cell size
        shape.setRadius(cellSize / 2);
        return shape;
    }

    public Color getColor(){
        return color;
    }
    public void setColor(Color color){
        this.color = color;
        shape.setFill(color);
    }

    public int getPlayerStrength() {
        return playerStrength;
    }

    public Wallet getPlayerWallet() {
        return playerWallet;
    }

    public ArrayList<ValuableTreasure> treasuresList(){
        return treasuresList;
    }
    public int getxCoordinate(){
        return xCoordinate;
    }
    public int getyCoordinate(){
        return yCoordinate;
    }
    public int getRadius(){
        return radius;
    }

    // Method for move player

    public void moveRight() {
        if (xCoordinate < GRID_SIZE - 1) {
            xCoordinate++;
        }
        else {
            System.out.println("Where the fuck are you going!");
        }
    }

    public void moveLeft() {
        if (xCoordinate > 0) {
            xCoordinate--;
        }
        else {
            System.out.println("Where the fuck are you going!");
        }
    }

    public void moveUp() {
        if (yCoordinate > 0) {
            yCoordinate--;
        }
        else {
            System.out.println("Where the fuck are you going!");
        }
    }

    public void moveDown() {
        if (yCoordinate < GRID_SIZE - 1) {
            yCoordinate++;
        }
        else {
            System.out.println("Where the fuck are you going!");
        }
    }
}
