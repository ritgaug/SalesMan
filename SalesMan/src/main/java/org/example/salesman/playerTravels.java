package org.example.salesman;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class CollectingLoot {
    public static List<Point> pathTraveledPlayer1 = new ArrayList<>();
    public static List<Point> pathTraveledPlayer2 = new ArrayList<>();

    public CollectingLoot() {
        player1Path();
        player2Path();

        if (vistingMarkedObjects()){


        }
    }
    public void player1Path() {
        pathTraveledPlayer1 = new ArrayList<>();
    }
    public void player2Path() {
        pathTraveledPlayer2 = new ArrayList<>();
    }
    public static void addTravels1(int x, int y) {
        pathTraveledPlayer1.add(new Point(x, y));

    }
    public static void addTravels2(int x, int y) {
        pathTraveledPlayer2.add(new Point(x, y));
    }
    public static boolean vistingMarkedObjects() {
        SalesmanGame salesmanGame = new SalesmanGame();
        for (Point markedObject : salesmanGame.markedObjects) {
            if (markedObject == pathTraveledPlayer1) {
                // player1 is on loot
                return true;
            }
            else if ( markedObject == pathTraveledPlayer2){
                // player2 is on loot
                return true;
            }
        }
        // no players are on loot
        return false;
    }

    public static boolean alreadyCollectedLoot() {
        SalesmanGame salesmanGame = new SalesmanGame();
        for (Point markedObject : salesmanGame.markedObjects) {
            if (markedObject != pathTraveledPlayer1 || markedObject != pathTraveledPlayer1) {
                // loot is availble to be collected
                return true;
            }
        }
        // no players are on loot
        return false;
    }
}
