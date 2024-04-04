package org.example.salesman;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import static javafx.scene.paint.Color.*;
import static org.example.salesman.Loot.*;
import static org.example.salesman.SalesmanGame.visitedHouses;

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
    public static List <Point> cellsVisted;
    private int stepsPlayer1 = 0;
    private int stepsPlayer2 = 0;
    private List<Trap> traps;
    private List<ValuableTreasure> valuableTreasures;

    private Dice dice;
    public Movement(Player player1, Player player2, GridPane gridPane , Dice dice, List<Trap> traps,List<ValuableTreasure> valuableTreasures) {
        this.player1 = player1;
        this.player2 = player2; // Initialize player 2
        this.gridPane = gridPane;
        LocationList();
        this.dice = dice;
        this.traps = traps ;
        this.valuableTreasures = valuableTreasures;

            // Move Right for Player 1
            moveRightPlayer1 = new Button("Right");
            moveRightPlayer1.setOnAction(event -> {
                if(dice.isDiceRolled()) {
                    if (stepsPlayer1 < dice.getDieResult()) {
                        player1.moveRight();
                        updatePlayerPosition(player1);
                        stepsPlayer1++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer1--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels1(player1.getXCoordinate(),player1.getYCoordinate());
                    } else {
                        disableButtonsP1();
                        enableButtonsP2();
                        dice.setDiceRolled(false);
                        System.out.println("player2's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Move Left for Player 1
            moveLeftPlayer1 = new Button("Left");
            moveLeftPlayer1.setOnAction(event -> {
                if(dice.isDiceRolled()) {
                    if (stepsPlayer1 < dice.getDieResult()) {
                        player1.moveLeft();
                        updatePlayerPosition(player1);
                        stepsPlayer1++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer1--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels1(player1.getXCoordinate(),player1.getYCoordinate());
                    } else {
                        disableButtonsP1();
                        enableButtonsP2();
                        dice.setDiceRolled(false);
                        System.out.println("player2's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Move Up for Player 1
            moveUpPlayer1 = new Button("Up");
            moveUpPlayer1.setOnAction(event -> {
                if(dice.isDiceRolled()) {

                    if (stepsPlayer1 < dice.getDieResult()) {
                        player1.moveUp();
                        updatePlayerPosition(player1);
                        stepsPlayer1++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer1--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels1(player1.getXCoordinate(),player1.getYCoordinate());
                    } else {
                        disableButtonsP1();
                        enableButtonsP2();
                        dice.setDiceRolled(false);
                        System.out.println("player2's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });


            // Move Down for Player 1
            moveDownPlayer1 = new Button("Down");
            moveDownPlayer1.setOnAction(event -> {
                if(dice.isDiceRolled()) {
                    if (stepsPlayer1 < dice.getDieResult()) {
                        player1.moveDown();
                        updatePlayerPosition(player1);
                        stepsPlayer1++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer1--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels1( player1.getXCoordinate(),player1.getYCoordinate());
                    } else {
                        disableButtonsP1();
                        enableButtonsP2();
                        dice.setDiceRolled(false);
                        System.out.println("player2's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Initialize buttons for player 2
            // Move Right for Player 2
            moveRightPlayer2 = new Button("Right");
            moveRightPlayer2.setOnAction(event -> {
                if(dice.isDiceRolled()) {
                    if (stepsPlayer2 < dice.getDieResult()) {
                        player2.moveRight(); // Call moveRight method for player 2
                        updatePlayerPosition(player2);
                        stepsPlayer2++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer2--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels2( player2.getXCoordinate(),player2.getYCoordinate());
                    } else {
                        disableButtonsP2();
                        enableButtonsP1();
                        dice.setDiceRolled(false);
                        System.out.println("player1's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Move Left for Player 2
            moveLeftPlayer2 = new Button("Left");
            moveLeftPlayer2.setOnAction(event -> {
                if(dice.isDiceRolled()) {
                    if (stepsPlayer2 < dice.getDieResult()) {
                        player2.moveLeft(); // Call moveLeft method for player 2
                        updatePlayerPosition(player2);
                        stepsPlayer2++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer2--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels2(player2.getXCoordinate(),player2.getYCoordinate());
                    } else {
                        disableButtonsP2();
                        enableButtonsP1();
                        dice.setDiceRolled(false);
                        System.out.println("player1's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Move Up for Player 2
            moveUpPlayer2 = new Button("Up");
            moveUpPlayer2.setOnAction(event -> {
                if (dice.isDiceRolled()) {
                    if (stepsPlayer2 < dice.getDieResult()) {
                        player2.moveUp(); // Call moveUp method for player 2
                        updatePlayerPosition(player2);
                        stepsPlayer2++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer2--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels2(player2.getXCoordinate(),player2.getYCoordinate());
                    } else {
                        disableButtonsP2();
                        enableButtonsP1();
                        dice.setDiceRolled(false);
                        System.out.println("player1's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });

            // Move Down for Player 2
            moveDownPlayer2 = new Button("Down");
            moveDownPlayer2.setOnAction(event -> {
                if (dice.isDiceRolled()) {
                    if (stepsPlayer2 < dice.getDieResult()) {
                        player2.moveDown(); // Call moveDown method for player 2
                        updatePlayerPosition(player2);
                        stepsPlayer2++;

                        // die roll unaffected by accidental move out of bounds
                        if (Player.failToMove == true){
                            stepsPlayer2--;
                            Player.failToMove = false;
                        }

                        //create list of everywhere player has been
                        PlayerMoves.addTravels2( player2.getXCoordinate(),player2.getYCoordinate());
                    } else {
                        disableButtonsP2();
                        enableButtonsP1();
                        dice.setDiceRolled(false);
                        System.out.println("player1's turn");
                    }
                }
                else {
                    System.out.println("Roll the dice!");
                }
            });
        disableButtonsP2();
        }

    private void disableButtonsP1() {
        // Disable player 1 buttons
        moveLeftPlayer1.setDisable(true);
        moveRightPlayer1.setDisable(true);
        moveUpPlayer1.setDisable(true);
        moveDownPlayer1.setDisable(true);

        // Reset steps count for both players
        stepsPlayer1 = 0;
    }

    private void enableButtonsP1() {
        // Enable player 1 buttons
        moveLeftPlayer1.setDisable(false);
        moveRightPlayer1.setDisable(false);
        moveUpPlayer1.setDisable(false);
        moveDownPlayer1.setDisable(false);
    }
    private void disableButtonsP2(){
        // Disable player 2 buttons
        moveLeftPlayer2.setDisable(true);
        moveRightPlayer2.setDisable(true);
        moveUpPlayer2.setDisable(true);
        moveDownPlayer2.setDisable(true);

        stepsPlayer2 = 0;
    }
    private void enableButtonsP2(){
        // Enable player 2 buttons
        moveLeftPlayer2.setDisable(false);
        moveRightPlayer2.setDisable(false);
        moveUpPlayer2.setDisable(false);
        moveDownPlayer2.setDisable(false);
    }

    private void updatePlayerPosition(Player player) {

        if (isWallHouse(player.getXCoordinate(), player.getYCoordinate())) {
            errorMessage("You cannot enter a wall house!");
            // return to previous location
            gridPane.getChildren().remove(player.getShape(CELL_SIZE));
            Point previousLocation = cellsVisted.get(cellsVisted.size() - 1);
            int lastX = previousLocation.x;
            player.setXCoordinate(lastX);
            int lastY = previousLocation.y;
            player.setYCoordinate(lastY);
            gridPane.add(player.getShape(CELL_SIZE), lastX, lastY);
            if(player == player1){
                stepsPlayer1--;
            }
            if(player == player2){
                stepsPlayer2--;
            }
        }

        //Display treasure cells
        for (ValuableTreasure treasure : valuableTreasures){
            if (player.getXCoordinate() == treasure.getXCoordinate() && player.getYCoordinate() == treasure.getYCoordinate()){
                treasure.displayTreasureCell();
            }
        }

        // check if player is on marked object / loot
        Loot.atMarkedObject(player.getXCoordinate(), player.getYCoordinate(), player);

        // if player fall into traps
            for (Trap trap : traps) {
                if (player.getXCoordinate() == trap.getLocation().x && player.getYCoordinate() == trap.getLocation().y) {
                    if (player.equals(player1)) {
                        handleTrapActivation(player1, trap);
                    } else if (player.equals(player2)) {
                        handleTrapActivation(player2, trap);
                    }
                    break;
                }
            }

            // if player has collected loot turn square gray
        if (lootCollected = false){
            gridPane.getChildren().remove(player.getShape(CELL_SIZE));
            gridPane.add(player.getShape(CELL_SIZE), player.getXCoordinate(), player.getYCoordinate());
        }
        if (lootCollected = true){
            gridPane.getChildren().remove(player.getShape(CELL_SIZE));

            Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
            rect.setFill(LIGHTGRAY);
            rect.setStroke(BLACK);
            gridPane.add(rect, xLootCollected, yLootCollected);

            gridPane.add(player.getShape(CELL_SIZE), player.getXCoordinate(), player.getYCoordinate());

            // makes sure players don't disappear when left on loot
            if (player == player1 ){
                if (xLootCollected == player2.getXCoordinate() && yLootCollected == player2.getYCoordinate()){
                    gridPane.getChildren().remove(player2.getShape(CELL_SIZE));
                    gridPane.add(player2.getShape(CELL_SIZE), player2.getXCoordinate(), player2.getYCoordinate());
                }
            }
            if (player == player2 ){
                if (xLootCollected == player1.getXCoordinate() && yLootCollected == player1.getYCoordinate()){
                    gridPane.getChildren().remove(player1.getShape(CELL_SIZE));
                    gridPane.add(player1.getShape(CELL_SIZE), player1.getXCoordinate(), player1.getYCoordinate());
                }
            }
        }

        // Check if player1 and player2 are in the same position
        if (player1.getXCoordinate() == player2.getXCoordinate() && player1.getYCoordinate() == player2.getYCoordinate()) {
            // Players are in the same space, trigger the battle
            Battle battle = new Battle(gridPane);
            battle.engage(player1, player2);
            // Update battle indicators
            updateBattleIndicators(true, player1, player2);

        } else {
            // Players are not in the same space, remove battle indicators
            updateBattleIndicators(false, null, null);
        }

        // check if player is at castle
        if (player == player1){
            if (player1.getXCoordinate() == 5 && player1.getYCoordinate() == 4) {
                SalesmanGame.castleQuestion(player1);
            }
        }
        if (player == player2){
            if (player2.getXCoordinate() == 5 && player2.getYCoordinate() == 4) {
                SalesmanGame.castleQuestion(player2);
            }
        }


        // creating list of cells visted by player within turn
        // if player is trying to visit house twice within turn return error
        if (!Repitition.visitingStatus(player.getXCoordinate(), player.getYCoordinate(), cellsVisted)) {
            //list of visited cells within current turn already contains this point
            if (!Repitition.visitingStatus(player.getXCoordinate(), player.getYCoordinate(), visitedHouses)) {
                // visting same house twice within one turn
                errorMessage();

                // return to previous location
                gridPane.getChildren().remove(player.getShape(CELL_SIZE));
                Point previousLocation = cellsVisted.get(cellsVisted.size() - 1);
                int lastX = previousLocation.x;
                player.setXCoordinate(lastX);
                int lastY = previousLocation.y;
                player.setYCoordinate(lastY);
                gridPane.add(player.getShape(CELL_SIZE), lastX, lastY);

            } else {
                addLocationsVisted(player.getXCoordinate(), player.getYCoordinate());
            }
        } else {
            //player has never been on this square
            addLocationsVisted(player.getXCoordinate(), player.getYCoordinate());
        }
    }
    public void LocationList() {
        cellsVisted = new ArrayList<>();
    }
    public void addLocationsVisted (int x, int y) {
        cellsVisted.add(new Point(x, y));
    }
    public void errorMessage(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Illegal Play");
        alert.setHeaderText(null);
        alert.setContentText("ERROR: You have already visited this house during your turn.");
        alert.showAndWait();
    }
    private void updateBattleIndicators(boolean isInBattle, Player player1, Player player2) {
        // Implement logic to update UI elements or indicators based on whether a battle is happening or not
        // For example, you can change the color of the players' shapes or display a message indicating a battle
    }
    void displayWinner() {
        Stage winnerDisplay = new Stage();
        VBox winner = new VBox();
        winner.setAlignment(Pos.CENTER);
        winner.setSpacing(10);

        // Determine the winner
        String winnerText;
        if (player1.getPoint() > player2.getPoint()) {
            winnerText = "Winner is player 1!";
        }
         else {
            winnerText = "Winner is player 2!";
        }

        Label label = new Label(winnerText);
        winner.getChildren().add(label);

        // Create scene and set it to the stage
        Scene winnerScene = new Scene(winner, 200, 100);
        winnerDisplay.setScene(winnerScene);

        // Show the stage
        winnerDisplay.show();
    }

    public void addButtonsToGrid( GridPane gridPane, int rowIndex, int colIndex) {
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

    // Method for trap activation
    private void handleTrapActivation(Player player, Trap trap) {
        if (player.getPlayerWallet().getBalance() >= trap.getPenalty()) {
            trap.trigger(player.getPlayerWallet());
            System.out.println("You fell into the trap!\nYou lost " + trap.getPenalty() + " of your balance");
            System.out.println(player.getPlayerWallet().getBalance());
        } else {
            System.out.println("You don't have enough money!\nTrap not activated");
        }
    }
    // Method when both players collect 4 points and the game is drawn
    void displayDraw() {
        Stage drawDisplay = new Stage();
        VBox draw = new VBox();
        draw.setAlignment(Pos.CENTER);
        draw.setSpacing(10);

        String drawText;
        if ((player1.getPlayerStrength() + player1.getPlayerWallet().getBalance()) == (player2.getPlayerStrength() + player2.getPlayerWallet().getBalance())){
            drawText = "No winner. Game is drawn.";
        } else if ((player1.getPlayerStrength() + player1.getPlayerWallet().getBalance()) > (player2.getPlayerStrength() + player2.getPlayerWallet().getBalance())){
            drawText = "Winner is player 1!";
        } else {
            drawText = "Winner is player 2!";
        }

        Label label = new Label(drawText);
        draw.getChildren().add(label);

        // Create scene and set it to the stage
        Scene drawScene = new Scene(draw, 200, 100);
        drawDisplay.setScene(drawScene);

        // Show the stage
        drawDisplay.show();
    }
    private boolean isWallHouse(int x, int y) {
        // Define the coordinates of the wall houses
        int[][] wallHouses = {{1, 0}, {0, 8}, {0, 4}, {9, 6}, {8, 0}};

        // Check if the given coordinates match any of the wall houses
        for (int[] coordinates : wallHouses) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true; // This cell is a wall house
            }
        }

        return false; // Not a wall house
    }

    // Method to display an error message
    private void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Illegal Move");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}