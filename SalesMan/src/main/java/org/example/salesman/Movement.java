package org.example.salesman;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;

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

        // Set keyboard shortcuts
        setKeyboardShortcuts();
    }
    // Constructor for matching keyboard buttons to grid buttons
    private void setKeyboardShortcuts() {
        moveRight.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                movePlayerRight();
            }
        });

        moveLeft.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                movePlayerLeft();
            }
        });

        moveUp.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                movePlayerUp();
            }
        });

        moveDown.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                movePlayerDown();
            }
        });
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

    // Define methods to handle player movement
    private void movePlayerRight() {
        // Implement movement logic for moving the player right
    }

    private void movePlayerLeft() {
        // Implement movement logic for moving the player left
    }

    private void movePlayerUp() {
        // Implement movement logic for moving the player up
    }

    private void movePlayerDown() {
        // Implement movement logic for moving the player down
    }
}
