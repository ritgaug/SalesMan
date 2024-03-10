package org.example.salesman;

// ValuableTreasure class
public class ValuableTreasure {
    private String treasureName;
    private int valueOfTreasure;
    //Hellohjhjhj
    private int treasureId;
    private int xCoordinate;
    private int yCoordinate;

    //class instances of each type of treasure
    private static int DiamondRing=0;
    private static int JewelEncrustedSword=0;
    private static int CrystalGoblets=0;
    private static int WoodenBow=0;
    private static int GoldenGoblet=0;
    private static int PaladinShield=0;
    private static int GoldenKey=0;
    private static int DragonsScroll=0;


    public ValuableTreasure(int treasureId) {
        this.treasureId = treasureId;
    }

    public int getTreasureId() {
        return treasureId;
    }

    public void setTreasureId(int treasureId) {
        this.treasureId = treasureId;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    public int getValueOfTreasure(){return valueOfTreasure;}
}

