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
    public void verification(ArrayList<ValuableTreasure> a,Player p){
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
    public static void castleQuestion() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        Label treasuresFound = new Label("Enter the x and y coordinates of the treasure you have found.");
        vbox.getChildren().add(treasuresFound);

        GridPane questionBox = new GridPane();
        questionBox.setHgap(5);
        questionBox.setVgap(5);

        // Input values
        Label xTreasure = new Label("Enter x-coordinate:");
        TextField xTreasureEntered = new TextField();
        Label yTreasure = new Label("Enter y-coordinate:");
        TextField yTreasureEntered = new TextField();
        Button enter = new Button("enter");
        enter.setOnAction(event->{
             xEnter = Integer.parseInt(xTreasureEntered.getText());
             yEnter = Integer.parseInt(yTreasureEntered.getText());
             SalesmanGame salesmanGame = new SalesmanGame();
            if (salesmanGame.collectingTreasures()){
                System.out.println("You got the point ");
            }
            else{
                System.out.println("NOPE");
            }

        });

        // Add labels and text to the grid pane
        questionBox.add(xTreasure, 0, 0);
        questionBox.add(xTreasureEntered, 1, 0);
        questionBox.add(yTreasure, 0, 1);
        questionBox.add(yTreasureEntered, 1, 1);
        questionBox.add(enter,2,0);

        // Add the grid pane to  VBox
        vbox.getChildren().add(questionBox);

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vbox, 400, 100);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Treasure Collection Verification");
        dialogStage.show();
    }
    public int getXEnter(){
        return xEnter;
    }
    public int getYEnter(){
        return yEnter;
    }
}

