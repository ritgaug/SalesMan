package org.example.salesman;

public class Player {
    private int playerStrength;
    private int playerNumber;
    private Wallet playerWallet = new Wallet();

    public void setPlayerStrength(int playerStrength) {
        this.playerStrength = playerStrength;
    }

    public int getPlayerStrength() {
        return playerStrength;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Wallet getPlayerWallet() {
        return playerWallet;
    }

    public void setPlayerWallet(Wallet playerWallet) {
        this.playerWallet = playerWallet;
    }

}
