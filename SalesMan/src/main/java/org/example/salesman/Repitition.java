package org.example.salesman;

import java.awt.*;
import java.util.List;

public class Repitition {
    public static boolean visitingStatus(int x, int y, List<Point> cells) {
        for (Point point : cells) {
            if (point.x == x && point.y == y) {
                return false; // already visted this cell
            }
        }
        return true; // never been on this cell
    }
}
