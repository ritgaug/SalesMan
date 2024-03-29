package org.example.salesman;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.util.Iterator;

import static javafx.scene.paint.Color.LIGHTGRAY;

public class Loot {
    public static boolean lootCollected = false;
    public static int xLootCollected;
    public static int yLootCollected;
    private static final int CELL_SIZE = 50;


    public static void atMarkedObject(int x, int y, Player player){
        SalesmanGame salesmanGame = new SalesmanGame();
        Iterator<Point> iterator = salesmanGame.markedObjects.iterator();

        while (iterator.hasNext()) {
            Point point = iterator.next();
            int xLoot = (int) point.getX();
            int yLoot = (int) point.getY();

            if (xLoot == x && yLoot == y) {
                player.getPlayerWallet().addMoney(50); // player collects money
                notifyPlayerWinnings(player);
                iterator.remove(); // Remove the current point from the list
                lootCollected = true;
                xLootCollected = x;
                yLootCollected = y;
            }
        }
    }

    private static void notifyPlayerWinnings(Player player){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        javafx.scene.control.Label treasuresFound = new javafx.scene.control.Label("Lost loot found! Collect $50.00. New player balance: $" + player.getPlayerWallet().getBalance());
        vbox.getChildren().add(treasuresFound);

        GridPane lootMessage = new GridPane();
        lootMessage.setHgap(5);
        lootMessage.setVgap(5);

        // Add the grid pane to  VBox
        vbox.getChildren().add(lootMessage);

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vbox, 400, 50);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Loot Found!");
        dialogStage.show();
    }

}
