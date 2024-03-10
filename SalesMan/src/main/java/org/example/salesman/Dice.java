package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Dice {
    private Button dieRollButton;

    public Dice() {
        dieRollButton = new Button("Roll Die");
        dieRollButton.setOnAction(event -> rollDie());
    }

    private int rollDie() {
        int dieResult = (int) (Math.random() * 6) + 1;
        System.out.println("Die result: " + dieResult);
        return dieResult;
    }

    public Button getDieRollButton() {
        return dieRollButton;
    }

    public void addToGrid(GridPane gridPane, int rowIndex, int colIndex) {
        gridPane.add(dieRollButton, rowIndex, colIndex);
    }
}
