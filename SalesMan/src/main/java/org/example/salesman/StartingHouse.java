package org.example.salesman;

import javafx.scene.shape.Rectangle;
import static javafx.scene.paint.Color.*;


class StartingHouse extends Rectangle {
    StartingHouse(double size) {
        super(size, size);
        setFill(AQUA);
        setStroke(BLACK);
    }
}
