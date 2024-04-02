package org.example.salesman;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;
import java.util.ArrayList;
class Castle extends Rectangle {
    private static int xEnter;
    private static int yEnter;

    Castle(double size) {
        super(size, size);
        setFill(YELLOW);
        setStroke(BLACK);
    }
    // Castle emoji
    private String getCastleEmoji() {
        return "\uD83C\uDFF0"; // Castle emoji
    }
    //Verifies the treasure claim. If the claim is valid it will remove the treasure from the map.
    // Add 1 to the treasure Discovery Score and adds the value of the treasure to the
    //player's wallet.
    /*public void verification(ArrayList<ValuableTreasure> a,Player p){
        for(int i=0;i<a.size();i++){
            for(int j=0;j<p.getTreasuresList().size();j++){
                if(p.getTreasuresList().get(j)==a.get(i)){
                    p.addTreasureDiscoveryScore();
                    p.addMoney(a.get(i).getValueOfTreasure());
                    a.remove(i);
                }
            }
        }

    }

     */
}

