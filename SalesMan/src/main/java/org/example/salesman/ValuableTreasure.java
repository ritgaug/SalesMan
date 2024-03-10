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
    private static int PaladinsShield=0;
    private static int GoldenKey=0;
    private static int DragonsScroll=0;

    //Constructor that prevents the occurance of more than 1 of each unique treasure
    //Each treasure has a unique number assigned to it from 1-8. Any other value
    //will result in the treasure being assigned a value of zero and the name "Empty"
    public ValuableTreasure(int treasureId) {

        this.treasureId = treasureId;
        if(treasureId==1&&DiamondRing==0){
            treasureName="Diamond Ring";
            valueOfTreasure=1000;
            DiamondRing++;
        } else if (treasureId==2&&JewelEncrustedSword==0) {
            treasureName="Jewel Encrusted Sword";
            valueOfTreasure=2000;
            JewelEncrustedSword++;
        }
        else if(treasureId==3&&CrystalGoblets==0){
            treasureName="Crystal Goblets";
            valueOfTreasure=5000;
            CrystalGoblets++;
        }
        else if(treasureId==4&&WoodenBow==0){
            treasureName="Wooden Bow";
            valueOfTreasure=100;
            WoodenBow++;
        }
        else if(treasureId==5&&GoldenGoblet==0){
            treasureName="Golden Goblet";
            valueOfTreasure=500;
            GoldenGoblet++;
        }
        else if(treasureId==6&&PaladinsShield==0){
            treasureName="Paladin's Shield";
            valueOfTreasure=700;
            PaladinsShield++;
        }
        else if(treasureId==7&&GoldenKey==0){
            treasureName="Golden Key";
            valueOfTreasure=300;
            GoldenKey++;
        }
        else if(treasureId==8&&DragonsScroll==0){
            treasureName="Dragon's Scroll";
            valueOfTreasure=7500;
            DragonsScroll++;

        }
        else{
            treasureName="Empty";
            valueOfTreasure=0;;

    }


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
    public void setValueOfTreasure(int value){valueOfTreasure=value;}

    public void setTreasureName(String treasureName){
        this.treasureName=treasureName;
    }



    }


