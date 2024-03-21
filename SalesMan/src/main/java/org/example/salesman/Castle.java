package org.example.salesman;
import javafx.scene.shape.Rectangle;
import static javafx.scene.paint.Color.*;

class Castle extends Rectangle {
    Castle(double size) {
        super(size, size);
        setFill(YELLOW);
        setStroke(BLACK);
    }
    // Castle emoji
    private String getCastleEmoji() {
        return "\uD83C\uDFF0"; // Castle emoji
    }
}

