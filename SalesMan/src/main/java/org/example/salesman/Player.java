package org.example.salesman;
//This class will hold all the important information
//about the player's status during the game.
public class Player {
    private int playerNumber;
    private int playerStrength;
    private Wallet playerWallet=new Wallet();

    public int getPlayerNumber(){return playerNumber;}
    public void setPlayerNumber(int playerNumber){this.playerNumber=playerNumber;}
    public int getPlayerStrength(){return playerStrength;}

    public void setPlayerStrength(int playerStrength) {
        this.playerStrength = playerStrength;
    }

    public Wallet getPlayerWallet(){return playerWallet;}
    public void setPlayerWallet(Wallet playerWallet){this.playerWallet=playerWallet;}

}
