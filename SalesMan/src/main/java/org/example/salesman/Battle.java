package org.example.salesman;

import javafx.scene.layout.GridPane;

public class Battle {
    private final int CELL_SIZE = 50;
    private GridPane gridPane;

    public Battle(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void engage(Player player1, Player player2) {
        // Check if players are adjacent
        if (areAdjacent(player1, player2) && (player1.getXCoordinate() >= 10)) {
            // no battles when players are outside map
            System.out.println("ERROR: Move players within map.");
        } else if (areAdjacent(player1, player2) && (player1.getYCoordinate() >= 10)){
            // no battles when players are outside map
            System.out.println("ERROR: Move players within map.");

        } else if (areAdjacent(player1, player2)) {
            System.out.println("Battle between Player 1 and Player 2!");

            // Determine the winner
            Player winner = determineWinner(player1, player2);
            Player loser = (winner == player1) ? player2 : player1;

            // Display winner and loser
            System.out.println("The winner is Player"+winner.getPlayerNumber()+ " !");
            System.out.println("The loser is Player"+loser.getPlayerNumber()+ " !");

            // Effects on money
            handleMoneyEffects(winner, loser);
            System.out.println("Winner balance: "+winner.getPlayerWallet().getBalance());
            System.out.println("Loser balance: "+loser.getPlayerWallet().getBalance());

            // Effects on strength
            handleStrengthEffects(winner, loser);
            System.out.println("Winner strength: "+winner.getPlayerStrength());
            System.out.println("Loser strength: "+loser.getPlayerStrength());
        }
    }

    private boolean areAdjacent(Player player1, Player player2) {
        return player1.getXCoordinate() == player2.getXCoordinate() && player1.getYCoordinate() == player2.getYCoordinate();

    }


    private Player determineWinner(Player player1, Player player2) {
        // Compare player strengths
        if (player1.getPlayerStrength() > player2.getPlayerStrength()) {
            return player1;
        } else if (player1.getPlayerStrength() < player2.getPlayerStrength()) {
            return player2;
        } else {
            // If strengths are equal, the player who entered the cell more recently wins
            // Implement logic to determine winner based on recent entry
            // For now, we assume player1 wins by default
            return player1;
        }
    }

    private void handleMoneyEffects(Player winner, Player loser) {
        // Take a fraction of money from the losing player
        double fraction = calculateMoneyFraction(loser , winner);
        winner.getPlayerWallet().addMoney((int) fraction);
        loser.getPlayerWallet().deductMoney((int) fraction);

        // Throw the losing player back to the starting house
        throwToStartingHouse(loser);
    }

    private double calculateMoneyFraction(Player loser , Player winner) {

        double fraction = (double) (winner.getPlayerStrength() - loser.getPlayerStrength()) /
                (winner.getPlayerStrength() + loser.getPlayerStrength())*
                loser.getPlayerWallet().getBalance();
        return fraction;
    }

    private void throwToStartingHouse(Player loser) {
        loser.setXCoordinate(9);
        loser.setYCoordinate(9);
        gridPane.getChildren().remove(loser.getShape(CELL_SIZE));
        gridPane.add(loser.getShape(CELL_SIZE), loser.getXCoordinate(), loser.getYCoordinate());
    }

    private void handleStrengthEffects(Player winner, Player loser) {
        // Decrease the winning player's strength by the amount of the losing player's strength
        winner.setPlayerStrength(winner.getPlayerStrength() - loser.getPlayerStrength());

        // Reduce the losing player's strength to zero
        loser.setPlayerStrength(0);
    }
}
