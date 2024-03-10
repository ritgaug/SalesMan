package org.example.salesman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.YELLOW;

// Castle class
class Castle extends Rectangle {
    Castle(double size) {
        super(size, size);
        setFill(YELLOW);
        setStroke(BLACK);
    }
}

