package org.example.salesman;

import com.almasb.fxgl.core.collection.Array;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.HBox;
import static javafx.scene.paint.Color.*;
import static javax.swing.text.html.HTML.Attribute.COLS;
import javafx.scene.image.Image;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SalesmanGame extends Application {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private static final int MARKED_OBJECTS = 13;
    private static final int MIN_TRAPS = 1;
    private static final int MAX_TRAPS = 5;
    public static List<Point> markedObjects = new ArrayList<>();

    // Create instances of Wallet, Trap, and House
    private Wallet wallet = new Wallet();
    private List<Trap> traps = new ArrayList<>();
    private House house = new House();
    private List<ValuableTreasure> valuableTreasures = new ArrayList<>();
    private Player player1;
    private Player player2;
    public static List<Point> visitedHouses;
    private VBox battleIndicators; // VBox to hold battle indicators
    private Market marketPlayer1IsOn;
    private Market marketPlayer2IsOn;
    private boolean darkMode = false;

    @Override
    public void start(Stage primaryStage) {
        // Initialize marked objects
        markedObjects = createLostItems();

        // Initialize Traps
        traps = createTraps();

        // Create instances of valuable treasures based on the provided data
        ValuableTreasure diamondRing = new ValuableTreasure(1, 200,"\uD83D\uDCCD");
        ValuableTreasure jewelEncrustedSword = new ValuableTreasure(2, 300, "\uD83D\uDDE1️");
        ValuableTreasure crystalGoblets = new ValuableTreasure(3, 400,"\uD83C\uDF77");
        ValuableTreasure woodenBow = new ValuableTreasure(4, 500,"\uD83C\uDFF9");
        ValuableTreasure goldenGoblet = new ValuableTreasure(5, 600,"\uD83C\uDFC6");
        ValuableTreasure paladinsShield = new ValuableTreasure(6, 700,"\uD83D\uDEE1");
        ValuableTreasure goldenKey = new ValuableTreasure(7, 800,"\uD83D\uDDDD️");
        ValuableTreasure dragonsScroll = new ValuableTreasure(8, 900,"🐉");

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
                new Weapon(25,"Pistol",1000,"\uD83D\uDD2B"),
                new Weapon(20, "Axe", 800,"\u2692"),
                new Weapon(15, "Sword", 400,"\uD83D\uDDE1"),
                new Weapon(10, "Bow", 200,"\uD83C\uDFF9"),
                new Weapon(5, "Knife", 100,"\uD83D\uDD2A")
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
        house.setWall(true);
        house.setTrap(true);

        battleIndicators = new VBox();
        battleIndicators.setSpacing(10); // Set spacing between indicators

        // Add battle indicators to the right side of the grid pane
        gridPane.add(battleIndicators, GRID_SIZE + 1, 0);

        // Nested loop to create the map
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setStroke(BLACK);

                // Check if the cell contains a market
                boolean isMarketCell = false;
                for (Market market : markets) {
                    addEmojiToGrid(gridPane,market.getCol() , market.getRow(), "\uD83D\uDED2", CELL_SIZE);
                    if (row == market.getRow() && col == market.getCol()) {
                        cell.setFill(ORANGE); // Market color
                        isMarketCell = true;
                        break;
                    }
                }

                // Check if the cell is a wall
                boolean isWallCell = false;
                if (row == 0 && col == 1 || row == 0 && col == 8 || row == 4 && col == 0 || row == 6 && col == 9 || row == 8 && col == 0) {
                    cell.setFill(BLACK); // Wall color
                    isWallCell = true;
                }

                // Check if the cell contains a trap
                boolean isTrapCell = false;
                if(!isWallCell && !isMarketCell) {
                    for (Trap trap : traps) {
                        addEmojiToGrid(gridPane,trap.getLocation().x,trap.getLocation().y,"⚠",CELL_SIZE);
                        if (row == trap.getLocation().y && col == trap.getLocation().x) {
                            cell.setFill(RED); // Trap color
                            isTrapCell = true;
                            break;
                        }
                    }
                }

                // Check if the cell is not a market, castle, wall, trap or Starting House
                if (!isMarketCell && !(row == 4 && col == 5) && !isWallCell && !isTrapCell && !(row == 9 && col == 9)) {
                    boolean isMarkedObject = false;
                    for (Point loot : markedObjects) {
                        if (loot.equals(new Point(col, row))) {
                            cell.setFill(BLUE); // Marked object color
                            isMarkedObject = true;
                            break;
                        }
                    }
                    // Set the cell color to green if it contains valuable treasure
                    if (!isMarkedObject) {
                        boolean isTreasure = false;
                        for (ValuableTreasure treasure : valuableTreasures) {
                            addEmojiToGrid(gridPane, treasure.getXCoordinate(), treasure.getYCoordinate(), treasure.getEmoji(), CELL_SIZE - 5);
                            if (treasure.getXCoordinate() == col && treasure.getYCoordinate() == row) {
                                cell.setFill(GREEN);
                                isTreasure = true;
                                break;
                            }
                        }
                        if (!isTreasure) {
                            cell.setFill(LIGHTGRAY); // Default cell color
                        }
                    }

                }
                gridPane.add(cell, col, row);
            }
        }

        // list of all house types to be visted
        visitedHouses = createVisitedHouses();

        // Create starting house and add to the grid
        StartingHouse startingHouse = new StartingHouse(CELL_SIZE);
        gridPane.add(startingHouse, 9, 9);

        // Create and add Player1
        player1 = new Player(1, PURPLE, 100, new Wallet(), new ArrayList<ValuableTreasure>(), new ArrayList<Weapon>(), GRID_SIZE, GRID_SIZE-1, CELL_SIZE / 2);
        gridPane.add(player1.getShape(CELL_SIZE), player1.getXCoordinate(), player1.getYCoordinate());

        // Create and add Player 2
        player2 = new Player(2, DEEPPINK, 50, new Wallet(), new ArrayList<>(), new ArrayList<>(), GRID_SIZE, GRID_SIZE -1, CELL_SIZE / 2);
        gridPane.add(player2.getShape(CELL_SIZE), player2.getXCoordinate(), player2.getYCoordinate());

        // Creating instance of Dice
        Dice dice = new Dice();
        dice.addToGrid(gridPane, 0, 10);

        // Inside the SalesmanGame class, after creating player1 and player2
        Movement movementPlayer1 = new Movement(player1, player2, gridPane,dice,traps);
        movementPlayer1.addButtonsToGrid(gridPane, 6, 10); // Add movement buttons for player 1

        // Modify trapButton event handler to trigger traps for both players
        trapButton.setOnAction(event -> {
            // Trigger the trap for player 1
            traps.get(new Random().nextInt(traps.size())).trigger(player1.getPlayerWallet());
            // Trigger the trap for player 2
            traps.get(new Random().nextInt(traps.size())).trigger(player2.getPlayerWallet());
        });

        // Modify buyWeaponsButton event handler to handle buying weapons for both players
        // Add buy weapons button
        Button buyWeaponsButton = new Button("Buy Weapons");
        gridPane.add(buyWeaponsButton, 1, GRID_SIZE);
        buyWeaponsButton.setOnAction(event -> {
            // Find the market the player 1 is on
            Market marketPlayer1IsOn = null;
            for (Market market : markets) {
                if (market.getRow() == player1.getYCoordinate() && market.getCol() == player1.getXCoordinate()) {
                    marketPlayer1IsOn = market;
                    break;
                }
            }

            // Find the market the player 2 is on
            Market marketPlayer2IsOn = null;
            for (Market market : markets) {
                if (market.getRow() == player2.getYCoordinate() && market.getCol() == player2.getXCoordinate()) {
                    marketPlayer2IsOn = market;
                    break;
                }
            }
            // If the market is found, display the weapons available in that market for player 1
            if (marketPlayer1IsOn != null) {
                buyWeapons(marketPlayer1IsOn.getWeapons(), marketPlayer1IsOn.getName(), player1);
            }
            // If the market is found, display the weapons available in that market for player 2
            if (marketPlayer2IsOn != null) {
                buyWeapons(marketPlayer2IsOn.getWeapons(), marketPlayer2IsOn.getName(), player2);
            }
        });

       //--

        Button statusBoardButton = new Button("Status Board player 1");

// Set an action event for the button (optional)
        statusBoardButton.setOnAction(event -> {

            // Create a new stage for displaying the status board
            Stage statusWindow = new Stage();

            // Create a vertical box layout for organizing content
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);

            // Create a label to display "Status Board"
            Label statusLabel = new Label("Status Board");

            // Add the label to the layout
            layout.getChildren().add(statusLabel);

            // Create a scene with the layout and set its size
            Scene statusScene = new Scene(layout, 300, 200);

            // Set the scene to the stage
            statusWindow.setScene(statusScene);

            // Set the title of the new window
            statusWindow.setTitle("Status Board");

            // Show the new window
            statusWindow.show();
        });

// Add the button to the grid pane
        gridPane.add(statusBoardButton, 0, GRID_SIZE+1);


        Button statusBoardButton2 = new Button("Status Board player 2");

// Set an action event for the button (optional)
        statusBoardButton2.setOnAction(event -> {

            // Create a new stage for displaying the status board
            Stage statusWindow = new Stage();

            // Create a vertical box layout for organizing content
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);

            // Create a label to display "Status Board"
            Label statusLabel = new Label("Status Board");
            // Additional content can be added here as needed...

            // Add the label to the layout
            layout.getChildren().add(statusLabel);

            // Create a scene with the layout and set its size
            Scene statusScene = new Scene(layout, 300, 200);

            // Set the scene to the stage
            statusWindow.setScene(statusScene);

            // Set the title of the new window
            statusWindow.setTitle("Status Board");

            // Show the new window
            statusWindow.show();
        });

// Add the button to the grid pane
        gridPane.add(statusBoardButton2, 1, GRID_SIZE+1);



        // Create castles and add them to the grid
        Castle castle = new Castle(CELL_SIZE);
        gridPane.add(castle, 5, 4);
        addEmojiToGrid(gridPane,5 , 4, "\uD83C\uDFF0", CELL_SIZE );

        // Add buy weapons button
        buyWeaponsButton = new Button("Buy Weapons");
        gridPane.add(buyWeaponsButton, 1, GRID_SIZE);

        // Attach event handler to the buy weapons button
        buyWeaponsButton.setOnAction(event -> {
            // Find the market the player 1 is on
            marketPlayer1IsOn = null;
            for (Market market : markets) {
                if (market.getRow() == player1.getYCoordinate() && market.getCol() == player1.getXCoordinate()) {
                    marketPlayer1IsOn = market;
                    break;
                }
            }
            // Find the market the player 2 is on
            marketPlayer2IsOn = null;
            for (Market market : markets) {
                if (market.getRow() == player2.getYCoordinate() && market.getCol() == player2.getXCoordinate()) {
                    marketPlayer2IsOn = market;
                    break;
                }
            }

            // If the market is found, display the weapons available in that market for player 1
            if (marketPlayer1IsOn != null) {
                buyWeapons(marketPlayer1IsOn.getWeapons(), marketPlayer1IsOn.getName(), player1);
            }
            // If the market is found, display the weapons available in that market for player 2
            if (marketPlayer2IsOn != null) {
                buyWeapons(marketPlayer2IsOn.getWeapons(), marketPlayer2IsOn.getName(), player2);
            }
        });

        // Add dark mode button
        ToggleButton DarkMode = new ToggleButton("Dark Mode");
        DarkMode.setOnAction(event -> toggleDarkMode(gridPane));
        gridPane.add(DarkMode, GRID_SIZE,  0);

        // Set up the scene
        Scene scene = new Scene(gridPane, 580, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traveling Salesman Game");
        // Set initial background color
        setGridPaneBackgroundColor(gridPane);
        Image image = new Image(getClass().getResourceAsStream("/org/example/salesman/Game Icon.png"));
        primaryStage.getIcons().add(image);
        primaryStage.show();

    }

    // Method to add emoji to the grid with a specific size
    private void addEmojiToGrid(GridPane gridPane, int colEmoji, int rowEmoji, String emoji, double fontSize) {
        StackPane stackPane = new StackPane();
        Text text = new Text(emoji);
        text.setFont(Font.font(fontSize)); // Set the font size
        stackPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);
        gridPane.add(stackPane, colEmoji, rowEmoji);
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
            Label weaponLabel = new Label(weapon.getEmoji() + " " + weapon.getName() + "\nStrength: " + weapon.getStrength() + "\nCost: " + weapon.getCost());
            weaponsPane.add(weaponLabel, 0, i + 1);

            // Create a "Buy" button for each weapon
            Button Buy = new Button("Buy");
            weaponsPane.add(Buy, 1, i + 1);
            Buy.setOnAction(event -> {
                if (marketPlayer1IsOn != null) {
                    if (weapon.getCost() <= player1.getPlayerWallet().getBalance()) {
                        player1.getPlayerWallet().deductMoney(weapon.getCost());
                        player1.addWeapon(weapon);
                        player1.addStrength(weapon);
                        System.out.println("Purchase complete and weapon " + weapon.getName() + " add to your weapons:)");
                        System.out.println("Your balance is :" + player1.getPlayerWallet().getBalance());
                        System.out.println("Player strength: "+player1.getPlayerStrength());
                        System.out.println("Player weapons: "+player1.getWeaponList().toString());
                    } else {
                        System.out.println("Not enough balance");
                    }
                }
                else if (marketPlayer2IsOn != null){
                    if (weapon.getCost() <= player2.getPlayerWallet().getBalance()) {
                        player2.getPlayerWallet().deductMoney(weapon.getCost());
                        player2.addWeapon(weapon);
                        player2.addStrength(weapon);
                        System.out.println("Purchase complete and weapon " + weapon.getName() + " add to your weapons:)");
                        System.out.println("Your balance is :" + player2.getPlayerWallet().getBalance());
                        System.out.println("Player strength: "+player2.getPlayerStrength());
                        System.out.println("Player weapons: "+player2.getWeaponList().toString());
                    } else {
                        System.out.println("Not enough balance");
                    }
                }

            });
        }

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(weaponsPane);

        Scene secondScene = new Scene(secondaryLayout, 300, 350);

        Stage newWindow = new Stage();
        newWindow.setTitle("Weapons in " + marketName);
        newWindow.setScene(secondScene);
        Image image = new Image(getClass().getResourceAsStream("/org/example/salesman/market.png"));
        newWindow.getIcons().add(image);
        newWindow.show();
    }


    // method for creating random location of marked objects/ lost items (loot)
    public static List<Point> createLostItems() {
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

    private void buyWeapons(List<Weapon> weapons, String marketName, Player player) {
        // Display the weapons available in the market
        displayWeapons(weapons, marketName);
    }

    private void updateBattleIndicators(boolean isBattle, Player player1, Player player2) {
        battleIndicators.getChildren().clear(); // Clear existing indicators

        if (isBattle) {
            // Show battle indicator
            Label battleLabel = new Label("Battle!");
            battleIndicators.getChildren().add(battleLabel);

            // Show players involved in the battle
            Rectangle player1Indicator = new Rectangle(20, 20, player1.getColor());
            Rectangle player2Indicator = new Rectangle(20, 20, player2.getColor());
            HBox playersBox = new HBox(10, player1Indicator, player2Indicator);
            battleIndicators.getChildren().add(playersBox);
        }
    }

    public List<Point> createVisitedHouses() {
        List<Point> visitedHouses = new ArrayList<>();

        visitedHouses.add(new Point(3, 0));
        visitedHouses.add(new Point(5, 2));
        visitedHouses.add(new Point(0, 6));
        visitedHouses.add(new Point(7, 7));
        visitedHouses.add(new Point(3, 9));

        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                for (ValuableTreasure treasure : valuableTreasures) {
                    if (treasure.getXCoordinate() == x && treasure.getYCoordinate() == y) {
                        visitedHouses.add(new Point(x, y));
                    }
                }
            }
        }

        return visitedHouses;
    }
    //method for players to pick up treasures
    public void pickUpTreasures(ArrayList<ValuableTreasure> a,Player p1, Player p2){
        for(int i=0;i<a.size();i++) {
            if ((p1.getXCoordinate() == a.get(i).getXCoordinate()) && (p1.getYCoordinate() == a.get(i).getYCoordinate())) {
                p1.addTreasure(a.get(i));
            }
            if ((p2.getXCoordinate() == a.get(i).getXCoordinate()) && (p2.getYCoordinate() == a.get(i).getYCoordinate())) {
                p2.addTreasure(a.get(i));
            }
        }
    }

    // Dark Mode
    private void toggleDarkMode(GridPane gridPane) {
        darkMode = !darkMode;
        setGridPaneBackgroundColor(gridPane);
    }

    private void setGridPaneBackgroundColor(GridPane gridPane) {
        if (darkMode) {
            gridPane.setStyle("-fx-background-color: #333333;"); // Dark background color
        } else {
            gridPane.setStyle("-fx-background-color: #FFFFFF;"); // Light background color
        }
    }
}