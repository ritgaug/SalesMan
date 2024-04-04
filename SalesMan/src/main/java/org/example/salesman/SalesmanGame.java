package org.example.salesman;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.HBox;
import static javafx.scene.paint.Color.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

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
    private static List<ValuableTreasure> valuableTreasures = new ArrayList<>();
    private static Player player1;
    private static Player player2;
    public static List<Point> visitedHouses;
    private VBox battleIndicators; // VBox to hold battle indicators
    private Market marketPlayer1IsOn;
    private Market marketPlayer2IsOn;
    private boolean darkMode = false;
    private Castle castle;
    private PlayerMoves playermove;
    private static int xEnter;
    private static int yEnter;
    private static Movement movementPlayer1;
    private static List<Point> occupiedPoints = new ArrayList<>();
    private long startTime;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        addOccupiedPoint(5, 4);
        addOccupiedPoint(9, 9);
        addOccupiedPoint(1,0);
        addOccupiedPoint(8,0);
        addOccupiedPoint(0,4);
        addOccupiedPoint(9,6);
        addOccupiedPoint(0,8);


        // Create instances of valuable treasures based on the provided data
        ValuableTreasure diamondRing = new ValuableTreasure(1, 200,"\uD83D\uDCCD",1);
        ValuableTreasure jewelEncrustedSword = new ValuableTreasure(2, 300, "\uD83D\uDDE1️",1);
        ValuableTreasure crystalGoblets = new ValuableTreasure(3, 400,"\uD83C\uDF77",1);
        ValuableTreasure woodenBow = new ValuableTreasure(4, 500,"\uD83C\uDFF9",1);
        ValuableTreasure goldenGoblet = new ValuableTreasure(5, 600,"\uD83C\uDFC6",1);
        ValuableTreasure paladinsShield = new ValuableTreasure(6, 700,"\uD83D\uDEE1",1);
        ValuableTreasure goldenKey = new ValuableTreasure(7, 800,"\uD83D\uDDDD️",1);
        ValuableTreasure dragonsScroll = new ValuableTreasure(8, 900,"🐉",1);

        // Add valuable treasures to the list
        valuableTreasures.add(diamondRing);
        valuableTreasures.add(jewelEncrustedSword);
        valuableTreasures.add(crystalGoblets);
        valuableTreasures.add(woodenBow);
        valuableTreasures.add(goldenGoblet);
        valuableTreasures.add(paladinsShield);
        valuableTreasures.add(goldenKey);
        valuableTreasures.add(dragonsScroll);



        // Initialize markets and weapons
        Market[] markets = {
                new Market("Market 1", 0, 3),
                new Market("Market 2", 2, 5),
                new Market("Market 3", 6, 0),
                new Market("Market 4", 7, 7),
                new Market("Market 5", 9, 3)
        };
        // add to occupiedPoint
        for(Market market:markets){
            addOccupiedPoint(market.getCol(), market.getRow());
        }

        Weapon[] weapons = {
                new Weapon(45,"Pistol",360,"\uD83D\uDD2B"),
                new Weapon(30, "Axe", 240,"\u2692"),
                new Weapon(20, "Sword", 180,"\uD83D\uDDE1"),
                new Weapon(10, "Bow", 90,"\uD83C\uDFF9"),
                new Weapon(5, "Knife", 50,"\uD83D\uDD2A")
        };

        // Initialize valuable treasures
        initializeValuableTreasures();

        // Initialize marked objects
        markedObjects = createLostItems(new HashSet<>(occupiedPoints));

        // Initialize Traps
        traps = createTraps();



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
                            //addEmojiToGrid(gridPane, treasure.getXCoordinate(), treasure.getYCoordinate(), treasure.getEmoji(), CELL_SIZE - 5);
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
                    for (Point loot : markedObjects) {
                        for (Market market : markets) {
                            for (Trap trap : traps) {
                                for (ValuableTreasure treasure : valuableTreasures) {
                                    if (loot.getY() == 0 && loot.getX() == 1 || loot.getY() == 0 && loot.getX() == 8 || loot.getY() == 4 && loot.getX() == 0 || loot.getY() == 6 && loot.getX() == 9 || loot.getY() == 8 && loot.getX() == 0 || loot.getY() == market.getRow() && loot.getX() == market.getCol() || loot.getY() == 4 && loot.getX() == 5 || loot.getY() == trap.getLocation().y && loot.getX() == trap.getLocation().x || treasure.getYCoordinate()==loot.getY() && treasure.getXCoordinate() == loot.getX()){
                                        loot.setLocation(loot.getX()+1,loot.getY()+1);
                                        if (loot.equals(new Point(col, row))) {
                                            cell.setFill(BLUE); // Marked object color
                                            break;
                                        }
                                    }

                                }
                            }

                        }
                    }
                    for (Point loot : markedObjects) {
                        for (Market market : markets) {
                            for (Trap trap : traps) {
                                for (ValuableTreasure treasure : valuableTreasures) {
                                    if (loot.getY() == 0 && loot.getX() == 1 || loot.getY() == 0 && loot.getX() == 8 || loot.getY() == 4 && loot.getX() == 0 || loot.getY() == 6 && loot.getX() == 9 || loot.getY() == 8 && loot.getX() == 0 || loot.getY() == market.getRow() && loot.getX() == market.getCol() || loot.getY() == 4 && loot.getX() == 5 || loot.getY() == trap.getLocation().y && loot.getX() == trap.getLocation().x || treasure.getYCoordinate()==loot.getY() && treasure.getXCoordinate() == loot.getX()){
                                        loot.setLocation(loot.getX()-2,loot.getY()-2);
                                        if (loot.equals(new Point(col, row))) {
                                            cell.setFill(BLUE); // Marked object color
                                            break;
                                        }
                                    }

                                }
                            }

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
        player1 = new Player(1, PURPLE, 0, new Wallet(), new ArrayList<ValuableTreasure>(), new ArrayList<Weapon>(), GRID_SIZE, GRID_SIZE-1, CELL_SIZE / 2,0);
        gridPane.add(player1.getShape(CELL_SIZE), player1.getXCoordinate(), player1.getYCoordinate());

        // Create and add Player 2
        player2 = new Player(2, DEEPPINK, 0, new Wallet(), new ArrayList<>(), new ArrayList<>(), GRID_SIZE, GRID_SIZE -1, CELL_SIZE / 2,0);
        gridPane.add(player2.getShape(CELL_SIZE), player2.getXCoordinate(), player2.getYCoordinate());

        // Creating instance of Dice
        Dice dice = new Dice();
        dice.addToGrid(gridPane, 0, 10);

        // Inside the SalesmanGame class, after creating player1 and player2
        movementPlayer1 = new Movement(player1, player2, gridPane,dice,traps,valuableTreasures);
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

        startTime = System.currentTimeMillis(); // Record the start time (in milliseconds) when the program starts

        //This line creates a new Label object named elapsedTimeLabel. The text content of this label is set to "00:00:00".
        //This label displays the elapsed time
        Label elapsedTimeLabel = new Label("00:00:00");

        //This line creates a new button with the text "Status Board player 1" and assigns it to the variable statusBoardButton
        Button statusBoardButton = new Button("Status Board player 1");

// This line sets an action event handler for when the statusBoardButton is clicked.
// It defines the action to be performed when the button is clicked
        statusBoardButton.setOnAction(event -> {

            // This line creates a new window for displaying the status board
            Stage statusWindow = new Stage();

            // This line creates a new vertical box layout and assigns it to the variable layout.
            // A VBox is a layout pane that arranges everything in it in a single vertical column from top to bottom.
            VBox layout = new VBox();
            //This line sets the alignment of the layout to center.
            layout.setAlignment(Pos.CENTER);
            //This line sets the spacing between each component within the layout to 10 pixels.
            layout.setSpacing(10);

            //calculates and shows the elapsed time of the player since the game has started
            long elapsedTime = System.currentTimeMillis() - startTime;
            long seconds = elapsedTime / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            //formats the elapsed time in a HH:MM:SS
            String elapsedTimeStr = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);

            // This line creates a new label with the text "Elapsed Time"
            // concatenated with the formatted elapsed time string (elapsedTimeStr).
            Label time = new Label("Elapsed Time: "+elapsedTimeStr );
            //Same but for player 1 strength
            Label strength2 = new Label("Player 1 Strength: "+ player1.getPlayerStrength());
            Label money2 = new Label("Player 1 Money: " + player1.getPlayerWallet().getBalance());
            Label points2 = new Label("Player 1 Points: "+player1.getPoint());
            Label treasure2 = new Label("Player 1 Treasure: "+player1.getTreasuresList().toString());
            Label weapons2 = new Label("Player 1 Weapons: "+player1.getWeaponList().toString());
            Label path2 = new Label("Player 1 Path: ");

            // Add the label to the layout
            layout.getChildren().add(time);
            layout.getChildren().add(strength2);
            layout.getChildren().add(money2);
            layout.getChildren().add(points2);
            layout.getChildren().add(treasure2);
            layout.getChildren().add(weapons2);
            layout.getChildren().add(path2);

            //The loop iterates over each point in the PlayerMoves.pathTraveledPlayer1
            // list and adds a text representation of each point to the layout.
            for (Point point : PlayerMoves.pathTraveledPlayer1) {
                Text pointText = new Text("[" + point.getX() + ", " + point.getY() + "]");
                layout.getChildren().add(pointText);
            }
            //This line creates a scroll pane, which allows for scrolling when the content exceeds the visible area.
            ScrollPane scrollPane = new ScrollPane();
            //sets the scroll pane to the layout (popup window) that was previously created
            scrollPane.setContent(layout);
            //These lines: content inside the ScrollPane will be resized to fit the available width and height without the need for horizontal or vertical scrolling
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            // All the content within the ScrollPane will be displayed within this scene.
            //sets its dimensions to be 300 pixels wide and 400 pixels tall
            Scene statusScene = new Scene(scrollPane, 300, 400);

            // This line sets the created scene (statusScene) as the scene to be displayed within the statusWindow
            statusWindow.setScene(statusScene);

            // Set the title of the new window
            statusWindow.setTitle("Status Board");

            // Show the new window on the screen
            statusWindow.show();
        });

// This line adds the statusBoardButton to a GridPane (gridPane) at column 0 and row GRID_SIZE+1
        gridPane.add(statusBoardButton, 0, GRID_SIZE+1);


        Button statusBoardButton2 = new Button("Status Board player 2");

// Set an action event for the button
        statusBoardButton2.setOnAction(event -> {

            // Create a new stage for displaying the status board
            Stage statusWindow = new Stage();

            // Create a vertical box layout for organizing content
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long seconds = elapsedTime / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            String elapsedTimeStr = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);

            // Create a label to display "Status Board"
            Label time = new Label("Elapsed Time: "+elapsedTimeStr );
            Label strength2 = new Label("Player 2 Strength: "+ player2.getPlayerStrength());
            Label money2 = new Label("Player 2 Money: " + player2.getPlayerWallet().getBalance());
            Label points2 = new Label("Player 2 Points: "+player2.getPoint());
            Label treasure2 = new Label("Player 2 Treasure: "+player2.getTreasuresList().toString());
            Label weapons2 = new Label("Player 2 Weapons: "+player2.getWeaponList().toString());
            Label path2 = new Label("Player 2 Path: ");

            // Add the label to the layout
            layout.getChildren().add(time);
            layout.getChildren().add(strength2);
            layout.getChildren().add(money2);
            layout.getChildren().add(points2);
            layout.getChildren().add(treasure2);
            layout.getChildren().add(weapons2);
            layout.getChildren().add(path2);

            for (Point point : PlayerMoves.pathTraveledPlayer2) {
                Text pointText = new Text("[" + point.getX() + ", " + point.getY() + "]");
                layout.getChildren().add(pointText);
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(layout);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            // Create a scene with the ScrollPane
            Scene statusScene = new Scene(scrollPane, 300, 400);

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
        castle = new Castle(CELL_SIZE);
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
            int x, y;
            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (isPointOccupied(x, y)); // Check if the location is already occupied
            Point location = new Point(x, y);
            traps.add(new Trap(penalty, location));
            // Add the trap location to the occupiedPoints list
            addOccupiedPoint(x, y);
        }
        return traps;
    }

    // Method to initialize treasures
    private void initializeValuableTreasures() {
        Random random = new Random(); // Initialize the Random object
        // Loop through the predefined valuable treasures
        for (ValuableTreasure treasure : valuableTreasures) {
            int x, y;
            do {
                // Generate random coordinates
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (isPointOccupied(x, y)); // Check if the coordinates are already occupied

            // Set the xCoordinate and yCoordinate directly
            treasure.setXCoordinate(x);
            treasure.setYCoordinate(y);

            // Add the newly initialized point to the list of occupied points
            addOccupiedPoint(x, y);
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
    public static List<Point> createLostItems(Set<Point> occupiedCells) {
        List<Point> loot = new ArrayList<>();
        Random random = new Random();

        while (loot.size() < MARKED_OBJECTS) {
            int x, y;

            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (occupiedCells.contains(new Point(x, y)));


            Point lootPoint = new Point(x, y);
            loot.add(lootPoint);
            occupiedCells.add(lootPoint);
        }

        return loot;
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
    /*public void pickUpTreasures(ArrayList<ValuableTreasure> a,Player p1, Player p2){
        for(int i=0;i<a.size();i++) {
            if ((p1.getXCoordinate() == a.get(i).getXCoordinate()) && (p1.getYCoordinate() == a.get(i).getYCoordinate())) {
                p1.addTreasure(a.get(i));
            }
            if ((p2.getXCoordinate() == a.get(i).getXCoordinate()) && (p2.getYCoordinate() == a.get(i).getYCoordinate())) {
                p2.addTreasure(a.get(i));
            }
        }
    }

     */

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
    public static void collectingTreasures1() {

        boolean collect2 = false;
        // Condition for collecting treasures
        for (ValuableTreasure treasure : valuableTreasures){
            for (Point point : PlayerMoves.pathTraveledPlayer1) {
                if (treasure.getXCoordinate() == xEnter && treasure.getYCoordinate() == yEnter && point.getX() == xEnter && point.getY() == yEnter) {
                    if (treasure.getPoint() > 0) {
                        player1.updatePoint(treasure.getPoint());
                        treasure.setPoint(0);
                        player1.getPlayerWallet().addMoney(treasure.getValueOfTreasure());
                        treasure.setValueOfTreasure(0);
                        player1.addTreasure(treasure.getTreasureName());
                        treasure.setTreasureName("empty");
                        collect2 = true;
                    }
                }
            }
        }

        if (collect2){
            winPoints(player1);
        }
        else{
            noPoints();
        }
    }
    public static void collectingTreasures2() {
        boolean collect2 = false;
        // Condition for collecting treasures
        for (ValuableTreasure treasure : valuableTreasures){
            for (Point point : PlayerMoves.pathTraveledPlayer2) {
                if (treasure.getXCoordinate() == xEnter && treasure.getYCoordinate() == yEnter && point.getX() == xEnter && point.getY() == yEnter) {
                    if (treasure.getPoint() > 0) {
                            player2.updatePoint(treasure.getPoint());
                            treasure.setPoint(0);
                            player2.getPlayerWallet().addMoney(treasure.getValueOfTreasure());
                            treasure.setValueOfTreasure(0);
                            player2.addTreasure(treasure.getTreasureName());
                            treasure.setTreasureName("empty");
                            collect2 = true;
                        }
                }
            }
        }

        if (collect2){
            winPoints(player2);
        }
        else{
            noPoints();
        }
    }
    public static void castleQuestion(Player player) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        Label treasuresFound = new Label("Enter the x and y coordinates of the treasure you have found.");
        vbox.getChildren().add(treasuresFound);

        GridPane questionBox = new GridPane();
        questionBox.setHgap(5);
        questionBox.setVgap(5);

        // Input values
        Label xTreasure = new Label("Enter x-coordinate:");
        javafx.scene.control.TextField xTreasureEntered = new javafx.scene.control.TextField();
        Label yTreasure = new Label("Enter y-coordinate:");
        javafx.scene.control.TextField yTreasureEntered = new TextField();
        Button enter = new Button("Enter");
        enter.setOnAction(event->{
            xEnter = Integer.parseInt(xTreasureEntered.getText());
            yEnter = Integer.parseInt(yTreasureEntered.getText());

            if (player == player1) {
                collectingTreasures1();
            }
            if (player == player2) {
                collectingTreasures2();
            }

            // Close vbox after values are entered
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

            // Display winner
                    if(player1.getPoint()>=5 || player2.getPoint()>=5){
                        movementPlayer1.displayWinner();
                    }
                    else if (player1.getPoint()==4 && player2.getPoint()==4){
                        movementPlayer1.displayDraw();
                    }

        });

        // Add labels and text to the grid pane
        questionBox.add(xTreasure, 0, 0);
        questionBox.add(xTreasureEntered, 1, 0);
        questionBox.add(yTreasure, 0, 1);
        questionBox.add(yTreasureEntered, 1, 1);
        questionBox.add(enter,2,0);

        // Add the grid pane to  VBox
        vbox.getChildren().add(questionBox);

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vbox, 400, 100);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Treasure Collection Verification");
        dialogStage.show();

    }
    private static void winPoints(Player player){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        javafx.scene.control.Label treasuresFound = new javafx.scene.control.Label("Treasure found! Collect points. Player points: " + player.getPoint());
        vbox.getChildren().add(treasuresFound);

        GridPane lootMessage = new GridPane();
        lootMessage.setHgap(5);
        lootMessage.setVgap(5);

        // Add the grid pane to  VBox
        vbox.getChildren().add(lootMessage);

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vbox, 400, 50);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Treasure Found!");
        dialogStage.show();
    }
    private static void noPoints(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        javafx.scene.control.Label treasuresFound = new javafx.scene.control.Label("No treasure at this location!");
        vbox.getChildren().add(treasuresFound);

        GridPane lootMessage = new GridPane();
        lootMessage.setHgap(5);
        lootMessage.setVgap(5);

        // Add the grid pane to  VBox
        vbox.getChildren().add(lootMessage);

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vbox, 400, 50);
        dialogStage.setScene(scene);
        dialogStage.setTitle("No Points!");
        dialogStage.show();
    }
    // Method to add occupied points
    private void addOccupiedPoint(int x, int y) {
        occupiedPoints.add(new Point(x, y));
    }
    private static boolean isPointOccupied(int x, int y) {
        for (Point occupiedPoint : occupiedPoints) {
            if (occupiedPoint.x == x && occupiedPoint.y == y) {
                return true; // The point is occupied
            }
        }
        return false; // The point is not occupied
    }


}
