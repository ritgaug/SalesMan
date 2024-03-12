package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Movement {
    private GridPane gridPane;
    private static final int CELL_SIZE = 50;
    private Player player;
    private Button moveRight;
    private Button moveLeft;
    private Button moveUp;
    private Button moveDown;


    public Movement(Player player, GridPane gridPane) {
        this.player = player;
        this.gridPane = gridPane;



        //Move Right
        moveRight = new Button("right");
        moveRight.setOnAction(event -> {
            player.moveRight();
            updatePlayerPosition();

        });

        //Move Left
        moveLeft = new Button("Left");
        moveLeft.setOnAction(event -> {
            player.moveLeft();
            updatePlayerPosition();
        });

        //Move Up
        moveUp = new Button("up");
        moveUp.setOnAction(event -> {
            player.moveUp();
            updatePlayerPosition();
        });

        //Move Down
        moveDown = new Button("down");
        moveDown.setOnAction(event -> {
            player.moveDown();
            updatePlayerPosition();
        });
    }


        private void updatePlayerPosition () {
            gridPane.getChildren().remove(player.getShape(CELL_SIZE));
            gridPane.add(player.getShape(CELL_SIZE), player.getxCoordinate(), player.getyCoordinate());
        }


        public void addUpToGrid (GridPane gridPane,int rowIndex, int colIndex){
            gridPane.add(moveUp, rowIndex, colIndex);
        }
        public void addDownToGrid (GridPane gridPane,int rowIndex, int colIndex){

            gridPane.add(moveDown, rowIndex, colIndex);
        }
        public void addRightToGrid (GridPane gridPane,int rowIndex, int colIndex){
            gridPane.add(moveRight, rowIndex, colIndex);
        }
        public void addLeftToGrid (GridPane gridPane,int rowIndex, int colIndex){
            gridPane.add(moveLeft, rowIndex, colIndex);
        }
    }
