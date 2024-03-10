package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

    public class Movement {
        private Button moveRight;
        private Button moveLeft;
        private Button moveUp;
        private Button moveDown;

        public Movement() {
            moveRight = new Button("up");
            //moveRight.setOnAction(event -> movePlayerUp());

            moveLeft = new Button("down");
            //moveLeft.setOnAction(event -> movePlayerDown());

            moveUp = new Button("right");
            //moveUp.setOnAction(event -> movePlayerRight());

            moveDown = new Button("left");
            //moveDown.setOnAction(event -> movePlayerLeft());
        }
        public void addUpToGrid(GridPane gridPane, int rowIndex, int colIndex) {
            gridPane.add(moveUp, rowIndex, colIndex);
        }
        public void addDownToGrid(GridPane gridPane, int rowIndex, int colIndex) {

            gridPane.add(moveDown, rowIndex, colIndex);
        }
        public void addRightToGrid(GridPane gridPane, int rowIndex, int colIndex) {
            gridPane.add(moveRight, rowIndex, colIndex);
        }
        public void addLeftToGrid(GridPane gridPane, int rowIndex, int colIndex) {
            gridPane.add(moveLeft, rowIndex, colIndex);
        }
    }
