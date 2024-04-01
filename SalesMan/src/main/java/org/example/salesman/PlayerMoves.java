package org.example.salesman;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class PlayerMoves {
    public static List<Point> pathTraveledPlayer1 = new ArrayList<>();
    public static List<Point> pathTraveledPlayer2 = new ArrayList<>();

    public PlayerMoves() {
        player1Path();
        player2Path();
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

}
