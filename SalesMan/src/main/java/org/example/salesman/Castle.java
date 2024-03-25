package org.example.salesman;
import javafx.scene.shape.Rectangle;
import static javafx.scene.paint.Color.*;
import java.util.ArrayList;
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
    public void verification(ArrayList<ValuableTreasure> a,Player p){
        for(int i=0;i<a.size();i++){
            for(int j=0;j<p.getTreasuresList().size();j++){
                
            }
        }

    }
}

