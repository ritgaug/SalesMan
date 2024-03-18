package org.example.salesman;

public class Battle {
    public void engage(Player player1, Player player2) {
        // Check if players are adjacent
        if (areAdjacent(player1, player2)) {
            // Determine the winner
            Player winner = determineWinner(player1, player2);
            Player loser = (winner == player1) ? player2 : player1;

            // Effects of winning
            handleWinningEffects(winner, loser);

            // Effects of losing
            handleLosingEffects(winner, loser);
        }
    }

    private boolean areAdjacent(Player player1, Player player2) {
        // Implement logic to check if players are adjacent
        return Math.abs(player1.getXCoordinate() - player2.getXCoordinate()) +
                Math.abs(player1.getYCoordinate() - player2.getYCoordinate()) == 1;
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

    private void handleWinningEffects(Player winner, Player loser) {
        // Take a fraction of money from the losing player
        int fraction = calculateMoneyFraction(loser);
        winner.getPlayerWallet().addMoney(fraction);
        loser.getPlayerWallet().deductMoney(fraction);

        // Throw the losing player back to the starting house
        throwToStartingHouse(loser);
    }

    private int calculateMoneyFraction(Player loser) {
        // Implement logic to calculate money fraction
        // For now, we assume a fixed fraction of 50% of the loser's money
        return loser.getPlayerWallet().getBalance() / 2;
    }

    private void throwToStartingHouse(Player loser) {
        // Implement logic to throw the losing player back to the starting house
        // For now, we assume the player's coordinates are set to the starting house coordinates
        loser.setXCoordinate(0);
        loser.setYCoordinate(0);
    }

    private void handleLosingEffects(Player winner, Player loser) {
        // Reduce the losing player's strength to zero
        loser.setPlayerStrength(0);

        // Decrease the winning player's strength by the amount of the losing player's strength
        winner.setPlayerStrength(winner.getPlayerStrength() - loser.getPlayerStrength());
    }
}
