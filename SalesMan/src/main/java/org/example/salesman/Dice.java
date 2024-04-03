package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Dice {
    private Button dieRollButton;
    private int dieResult;
    private boolean diceRolled ;

        // Constructor with Movement parameter
        public Dice() {
            dieRollButton = new Button("Roll Die");
            dieRollButton.setOnAction(event -> {
                if (diceRolled == false) {
                    rollDie();
                    diceRolled = true;
                }
                else {
                    System.out.println("Finish your move!");
                }
            });
        }
        public void setDiceRolled(boolean diceRolled){
            this.diceRolled = diceRolled;
        }
        public boolean isDiceRolled(){
            return diceRolled;
        }

    public void rollDie() {

        dieResult = (int) (Math.random() * 6) + 1;

        System.out.println("Die result: " + dieResult);

        // clear the locations visited for each roll of the die
        Movement.cellsVisted.clear();
    }

    public Button getDieRollButton() {
        return dieRollButton;
    }
    public int getDieResult(){
        return dieResult;
    }

    public void addToGrid(GridPane gridPane, int rowIndex, int colIndex) {
        gridPane.add(dieRollButton, rowIndex, colIndex);
    }
}
