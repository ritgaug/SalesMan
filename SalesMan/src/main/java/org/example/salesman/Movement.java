package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Movement {
    private GridPane gridPane;
    private static final int CELL_SIZE = 50;
    private Player player1;
    private Player player2;
    private Button moveRightPlayer1;
    private Button moveLeftPlayer1;
    private Button moveUpPlayer1;
    private Button moveDownPlayer1;
    private Button moveRightPlayer2;
    private Button moveLeftPlayer2;
    private Button moveUpPlayer2;
    private Button moveDownPlayer2;

    public Movement(Player player1, Player player2, GridPane gridPane) {
        this.player1 = player1;
        this.player2 = player2; // Initialize player 2
        this.gridPane = gridPane;

        // Move Right for Player 1
        moveRightPlayer1 = new Button("Right");
        moveRightPlayer1.setOnAction(event -> {
            player1.moveRight();
            updatePlayerPosition(player1);
        });

        // Move Left for Player 1
        moveLeftPlayer1 = new Button("Left");
        moveLeftPlayer1.setOnAction(event -> {
            player1.moveLeft();
            updatePlayerPosition(player1);
        });

        // Move Up for Player 1
        moveUpPlayer1 = new Button("Up");
        moveUpPlayer1.setOnAction(event -> {
            player1.moveUp();
            updatePlayerPosition(player1);
        });

        // Move Down for Player 1
        moveDownPlayer1 = new Button("Down");
        moveDownPlayer1.setOnAction(event -> {
            player1.moveDown();
            updatePlayerPosition(player1);
        });

        // Initialize buttons for player 2
        // Move Right for Player 2
        moveRightPlayer2 = new Button("Right");
        moveRightPlayer2.setOnAction(event -> {
            player2.moveRight(); // Call moveRight method for player 2
            updatePlayerPosition(player2);
        });

        // Move Left for Player 2
        moveLeftPlayer2 = new Button("Left");
        moveLeftPlayer2.setOnAction(event -> {
            player2.moveLeft(); // Call moveLeft method for player 2
            updatePlayerPosition(player2);
        });

        // Move Up for Player 2
        moveUpPlayer2 = new Button("Up");
        moveUpPlayer2.setOnAction(event -> {
            player2.moveUp(); // Call moveUp method for player 2
            updatePlayerPosition(player2);
        });

        // Move Down for Player 2
        moveDownPlayer2 = new Button("Down");
        moveDownPlayer2.setOnAction(event -> {
            player2.moveDown(); // Call moveDown method for player 2
            updatePlayerPosition(player2);
        });
    }

    private void updatePlayerPosition(Player player) {
        gridPane.getChildren().remove(player.getShape(CELL_SIZE));
        gridPane.add(player.getShape(CELL_SIZE), player.getXCoordinate(), player.getYCoordinate());

        // Check if player1 and player2 are in the same position
        if (player1.getXCoordinate() == player2.getXCoordinate() && player1.getYCoordinate() == player2.getYCoordinate()) {
            // Players are in the same space, trigger the battle
            Battle battle = new Battle();
            battle.engage(player1, player2);
            // Update battle indicators
            updateBattleIndicators(true, player1, player2);
            // Display the result of the battle (you can implement this part as needed)
            // For now, we print the winner to the console
            System.out.println("Battle between Player 1 and Player 2!");
        } else {
            // Players are not in the same space, remove battle indicators
            updateBattleIndicators(false, null, null);
        }
    }
    private void updateBattleIndicators(boolean isInBattle, Player player1, Player player2) {
        // Implement logic to update UI elements or indicators based on whether a battle is happening or not
        // For example, you can change the color of the players' shapes or display a message indicating a battle
    }

    public void addButtonsToGrid(GridPane gridPane, int rowIndex, int colIndex) {
        // Player 1 buttons
        gridPane.add(moveLeftPlayer1, rowIndex - 3, colIndex);
        gridPane.add(moveRightPlayer1, rowIndex - 1, colIndex);
        gridPane.add(moveUpPlayer1, rowIndex - 2, colIndex);
        gridPane.add(moveDownPlayer1, rowIndex - 2, colIndex + 1);

        // Player 2 buttons
        gridPane.add(moveLeftPlayer2, rowIndex + 1, colIndex);
        gridPane.add(moveRightPlayer2, rowIndex + 3, colIndex);
        gridPane.add(moveUpPlayer2, rowIndex + 2, colIndex);
        gridPane.add(moveDownPlayer2, rowIndex + 2, colIndex + 1);
    }
}
