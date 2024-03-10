package org.example.salesman;

public class Player {
    private int playerNumber;
    private Wallet playerWallet=new Wallet();

    public int getPlayerNumber(){return playerNumber;}
    public void setPlayerNumber(int playerNumber){this.playerNumber=playerNumber;}
    public Wallet getPlayerWallet(){return playerWallet;}
    public void setPlayerWallet(Wallet playerWallet){this.playerWallet=playerWallet;}

}
