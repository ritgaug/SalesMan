package org.example.salesman;

import java.awt.*;
import java.util.Iterator;

public class Loot {
    public static boolean lootCollected = false;
    public static int xLootCollected;
    public static int yLootCollected;

    public static void atMarkedObject(int x, int y, Player player){
        SalesmanGame salesmanGame = new SalesmanGame();
        Iterator<Point> iterator = salesmanGame.markedObjects.iterator();

        while (iterator.hasNext()) {
            Point point = iterator.next();
            int xLoot = (int) point.getX();
            int yLoot = (int) point.getY();

            if (xLoot == x && yLoot == y) {
                player.getPlayerWallet().addMoney(50); // player collects money
                iterator.remove(); // Remove the current point from the list
                lootCollected = true;
                xLootCollected = x;
                yLootCollected = y;
            }
        }
    }

}
