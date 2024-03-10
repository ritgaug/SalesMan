package org.example.salesman;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;

import static javafx.scene.paint.Color.*;
//hi
public class SalesmanGame extends Application {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private Circle p1Piece; // player 1
    private Circle p2Piece; // player 2
    private static final int MARKED_OBJECTS = 13;
    private static final int MIN_TRAPS = 1;
    private static final int MAX_TRAPS = 5;
    private List<Point> markedObjects;

    // Create instances of Wallet, Trap, and House
    private Wallet wallet = new Wallet();
    private List<Trap> traps = new ArrayList<>();
    private House house = new House();
    private List<Point> walls;
    private List<ValuableTreasure> valuableTreasures = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        // Initialize marked objects
        markedObjects = createLostItems();

        // Initialize Traps
        traps = createTraps();

        walls = createWalls();


        // Create instances of valuable treasures based on the provided data
        ValuableTreasure diamondRing = new ValuableTreasure(1,200);
        ValuableTreasure jewelEncrustedSword = new ValuableTreasure(2,300);
        ValuableTreasure crystalGoblets = new ValuableTreasure(3,400);
        ValuableTreasure woodenBow = new ValuableTreasure(4,500);
        ValuableTreasure goldenGoblet = new ValuableTreasure(5,600);
        ValuableTreasure paladinsShield = new ValuableTreasure(6,700);
        ValuableTreasure goldenKey = new ValuableTreasure(7,800);
        ValuableTreasure dragonsScroll = new ValuableTreasure(8,900);


        // Add valuable treasures to the list
        valuableTreasures.add(diamondRing);
        valuableTreasures.add(jewelEncrustedSword);
        valuableTreasures.add(crystalGoblets);
        valuableTreasures.add(woodenBow);
        valuableTreasures.add(goldenGoblet);
        valuableTreasures.add(paladinsShield);
        valuableTreasures.add(goldenKey);
        valuableTreasures.add(dragonsScroll);

        // Initialize valuable treasures
        initializeValuableTreasures();

        // Initialize markets and weapons
        Market[] markets = {
                new Market("Market 1", 0, 3),
                new Market("Market 2", 2, 5),
                new Market("Market 3", 6, 0),
                new Market("Market 4", 7, 7),
                new Market("Market 5", 9, 3)
        };

        Weapon[] weapons = {
                new Weapon(20, "Axe"),
                new Weapon(15, "Sword"),
                new Weapon(10, "Bow"),
                new Weapon(5, "Knife")
        };

        // Add weapons to the markets
        for (Market market : markets) {
            for (Weapon weapon : weapons) {
                market.addWeapon(weapon);
            }
        }

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Button trapButton = new Button("Trigger Trap");
        trapButton.setOnAction(event -> {
            // Trigger the trap
            traps.get(new Random().nextInt(traps.size())).trigger(wallet);
        });

        // Add trap button to the grid
        gridPane.add(trapButton, 1, GRID_SIZE);

        // Add wall and trap to the house
        house.setWall(true); // Example: Set the house as a wall
        house.setTrap(true); // Example: Set the house as a trap

        // Nested loop to create the map
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setStroke(Color.BLACK);

                // Check if the cell contains a castle
                if (row == 4 && col == 5) {
                    cell.setFill(Color.YELLOW); // Castle color
                }

                // Check if the cell contains a market
                boolean isMarketCell = false;
                for (Market market : markets) {
                    if (row == market.getRow() && col == market.getCol()) {
                        cell.setFill(Color.ORANGE); // Market color
                        isMarketCell = true;
                        break;
                    }
                }

                // Check if the cell is a wall
                boolean isWallCell = false;
                if (row == 0 && col == 1 || row == 0 && col == 8 || row == 4 && col == 0 || row == 6 && col == 9 || row == 8 && col == 0) {
                    cell.setFill(Color.BLACK); // Wall color
                    isWallCell = true;
                }

                // Check if the cell contains a trap
                boolean isTrapCell = false;
                for (Trap trap : traps) {
                    if (row == trap.getLocation().y && col == trap.getLocation().x) {
                        cell.setFill(Color.RED); // Trap color
                        isTrapCell = true;
                        break;
                    }
                }

                // Check if the cell is not a market, castle, wall, or trap
                if (!isMarketCell && !(row == 4 && col == 5) && !isWallCell && !isTrapCell) {
                    boolean isMarkedObject = false;
                    for (Point loot : markedObjects) {
                        if (loot.equals(new Point(col, row))) {
                            cell.setFill(Color.BLUE); // Marked object color
                            isMarkedObject = true;
                            break;
                        }
                    }
                    // Set the cell color to green if it contains valuable treasure
                    if (!isMarkedObject) {
                        boolean isTreasure = false;
                        for (ValuableTreasure treasure : valuableTreasures) {
                            if (treasure.getXCoordinate() == col && treasure.getYCoordinate() == row) {
                                cell.setFill(Color.GREEN);
                                isTreasure = true;
                                break;
                            }
                        }
                        if (!isTreasure) {
                            cell.setFill(Color.LIGHTGRAY); // Default cell color
                        }
                    }
                }

                gridPane.add(cell, col, row);
            }
        }


        // Add player pieces and die roll button
        p1Piece = createPlayerPiece(PURPLE);
        p2Piece = createPlayerPiece(TAN);

        gridPane.add(p1Piece, 0, 0);
        gridPane.add(p2Piece, GRID_SIZE - 1, GRID_SIZE - 1);

// Inside the SalesmanGame class
        Dice dice = new Dice();
        dice.addToGrid(gridPane, 0, 10);


        // Add buy weapons button
        Button buyWeaponsButton = new Button("Buy Weapons");
        gridPane.add(buyWeaponsButton, 1, GRID_SIZE);

        // Attach event handler to the buy weapons button
        buyWeaponsButton.setOnAction(event -> {
            // Get the market where the button was clicked
            Button clickedButton = (Button) event.getSource();
            int row = GridPane.getRowIndex(clickedButton);
            int col = GridPane.getColumnIndex(clickedButton);

            // Find the market associated with the clicked button
            Market clickedMarket = null;
            for (Market market : markets) {
                if (market.getRow() == row && market.getCol() == col) {
                    clickedMarket = market;
                    break;
                }
            }

            // If the market is found, display the weapons available in that market
            if (clickedMarket != null) {
                buyWeapons(clickedMarket.getWeapons(), clickedMarket.getName());
            }
        });

        // Set up the scene
        Scene scene = new Scene(gridPane, GRID_SIZE * CELL_SIZE, (GRID_SIZE + 1) * CELL_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traveling Salesman Game");
        primaryStage.show();
    }

    // Method to create traps at random locations
    private List<Trap> createTraps() {
        List<Trap> traps = new ArrayList<>();
        Random random = new Random();
        int numberOfTraps = random.nextInt(MAX_TRAPS - MIN_TRAPS + 1) + MIN_TRAPS; // Generate a random number of traps within the specified range
        for (int i = 0; i < numberOfTraps; i++) {
            int penalty = random.nextInt(10) + 1; // Random penalty between 1 and 10
            Point location = new Point(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE));
            traps.add(new Trap(penalty, location));
        }
        return traps;
    }

    // Method to create walls at custom locations
    private List<Point> createWalls() {
        List<Point> walls = new ArrayList<>();
        // Define custom wall positions here
        walls.add(new Point(0, 1));
        walls.add(new Point(0, 8));
        walls.add(new Point(4, 0));
        walls.add(new Point(6, 9));
        walls.add(new Point(8, 0));
        return walls;
    }

    private void initializeValuableTreasures() {
        Random random = new Random();
        Set<Point> occupiedCells = new HashSet<>(); // Keep track of occupied cells
        for (ValuableTreasure treasure : valuableTreasures) {
            int x, y;
            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (occupiedCells.contains(new Point(x, y))); // Check if the cell is already occupied by another valuable treasure

            treasure.setXCoordinate(x);
            treasure.setYCoordinate(y);
            occupiedCells.add(new Point(x, y));
        }
    }


    // Method to display weapons in a market
    private void displayWeapons(List<Weapon> weapons, String marketName) {
        GridPane weaponsPane = new GridPane();
        weaponsPane.setAlignment(Pos.CENTER);
        weaponsPane.setHgap(10);
        weaponsPane.setVgap(5);

        Label marketLabel = new Label("Available weapons in " + marketName + ":");
        weaponsPane.add(marketLabel, 0, 0);

        for (int i = 0; i < weapons.size(); i++) {
            Weapon weapon = weapons.get(i);
            Label weaponLabel = new Label(weapon.getName() + " - Strength: " + weapon.getDamage());
            weaponsPane.add(weaponLabel, 0, i + 1);
        }

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(weaponsPane);

        Scene secondScene = new Scene(secondaryLayout, 250, 200);

        Stage newWindow = new Stage();
        newWindow.setTitle("Weapons in " + marketName);
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    // method for rolling die
    private int dieRoll() {
        return (int) (Math.random() * 6) + 1;
    }

    // method for player pieces
    private Circle createPlayerPiece(Color color) {
        Circle piece = new Circle(CELL_SIZE / 2);
        piece.setFill(color);
        return piece;
    }

    // method for creating random location of marked objects/ lost items (loot)
    private List<Point> createLostItems() {
        List<Point> cells = new ArrayList<>();
        Set<Point> occupiedCells = new HashSet<>();

        for (int i = 0; i < MARKED_OBJECTS; i++) {

            int x, y;
            do {
                x = (int) (Math.random() * GRID_SIZE);
                y = (int) (Math.random() * GRID_SIZE);
                Point loot = new Point(x, y);
                cells.add(loot);
            }
            while (occupiedCells.contains(new Point(x, y)));
            occupiedCells.add(new Point(x, y));
            System.out.println("Treasure initialized at coordinates: (" + x + ", " + y + ")");

        }

        // evenly distributing the marked objects between left and right side
        Collections.sort(cells, Comparator.comparing(Point::getX).thenComparing(Point::getY));
        return cells;
    }

    // Method to check if a cell is occupied by a valuable treasure
    private boolean isCellOccupied(int row, int col) {
        for (ValuableTreasure treasure : valuableTreasures) {
            if (treasure.getXCoordinate() == col && treasure.getYCoordinate() == row) {
                return true;
            }
        }
        return false;
    }

    private void buyWeapons(List<Weapon> weapons, String marketName) {
        // Display the weapons available in the market
        displayWeapons(weapons, marketName);
    }
}