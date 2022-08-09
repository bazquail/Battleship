import java.util.ArrayList;
import java.util.Scanner;

public class BattleshipGame {
    static String[] names = {"The Destroyer", "The Submarine", "The Aircraft Carrier", "The Cruiser", "The Frigate"};
    static int numShips;
    static int gridSize;
    static ArrayList<Ship> ships = new ArrayList<>();
    static ArrayList<String> guessedBlocks = new ArrayList<>();
    static ArrayList<String> grid = new ArrayList<>();
    static Setup setup = new Setup();
    static ShipCreator shipCreator = new ShipCreator();
    static BlockSetter blockSetter = new BlockSetter();

    public static void main(String[] args) {
        setupGame();
        createShips();
        setShipLocations();
        printGrid();
        playGame();
    }
    public static void setupGame() {
        System.out.print("Would you like to play with 3, 4, or 5 ships? ");
        Scanner input = new Scanner(System.in);
        int numRequestedShips;
        if (input.hasNextInt()) { //checks to make sure the input is just an integer
            numRequestedShips = input.nextInt();
            setup.setShipCount(numRequestedShips); //sends the entered value to see if it is valid
        }
        while (!setup.isValidNumber()) { //if it isn't, repeat until a valid integer is entered
            System.out.print("Please enter either 3, 4, or 5! ");
            Scanner newInput = new Scanner(System.in);
            if (newInput.hasNextInt()) {
                numRequestedShips = newInput.nextInt();
                setup.setShipCount(numRequestedShips);
            }
        }
        numShips = setup.getShipCount(); //begins to get and eventually print out relevant game info
        gridSize = numShips + 4;
        String aOrAn = " a ";
        if (gridSize % 2 == 0) {
            aOrAn = " an ";
        }
        String boardDimensions = gridSize + " by " + gridSize + " board.";
        System.out.println();
        System.out.println("There will be " + numShips + " ships on" + aOrAn + boardDimensions);
        System.out.println("Your job is to sink them all. Good luck!");
    }
    public static void createShips() {
        ships = shipCreator.createShips(numShips, names);
        System.out.println();
        for (Ship ship : ships) { //lets user know how big each ship is
            System.out.println(ship.getName() + " is " + ship.getLength() + " blocks long.");
        }
    }
    public static void setShipLocations() {
        blockSetter.setShipBlocks(ships, numShips);
        blockSetter.setPossibleBlocks(); //sets a list that outlines all possible guesses
        grid = blockSetter.getPossibleBlocks(); //creates a grid initially equal to all possible guesses
    }
    public static void playGame() {
        boolean isGameGoing = true;
        boolean badGuess;
        int sunkShips = 0;
        String hitOrMiss;
        String replacementBlock;
        String errorMessage;
        blockSetter.setPossibleBlocks(); //resets so that changes to "grid" don't alter the possible blocks
        while (isGameGoing) {
            hitOrMiss = "Miss.";
            replacementBlock = "  ";
            badGuess = true;
            errorMessage = "";
            System.out.println();
            System.out.print("Guess a block! ");
            Scanner input = new Scanner(System.in);
            String userGuess = input.nextLine();
            userGuess = userGuess.toUpperCase();
            while (badGuess) {
                if (blockSetter.getPossibleBlocks().contains(userGuess)) { //first checks if it is even a valid block
                    if (guessedBlocks.contains(userGuess)) { //then checks if the block has been guessed
                        errorMessage = "You already guessed that!";
                    } else {
                        badGuess = false;
                        guessedBlocks.add(userGuess);
                    }
                } else {
                    errorMessage = "Not a valid block! ";
                }
                if (badGuess) { //if either condition is met, repeat until both are not met
                    System.out.print(errorMessage + " Guess again. ");
                    Scanner newInput = new Scanner(System.in);
                    userGuess = newInput.nextLine();
                }
            }
            for (Ship currentShip : ships) {
                if (currentShip.isHit(userGuess)) { //checks all ships for hits
                    hitOrMiss = "Hit!";
                    replacementBlock = "--";
                    if (currentShip.isSunk()) { //if it is a hit, check to see if sunk
                        hitOrMiss = currentShip.getName() + " is sunk!";
                        sunkShips++;
                    }
                }
            }
            int index = grid.indexOf(userGuess); //finds index where user guess is in grid
            grid.remove(userGuess);
            grid.add(index, replacementBlock); //adds the hit or miss block to visually update the grid for next print
            printGrid();
            System.out.println(hitOrMiss);
            if (sunkShips == setup.getShipCount()) { //end game if all ships are sunk
                isGameGoing = false;
            }
        }
        System.out.println("Game over! Number of guesses: " + guessedBlocks.size());
    }
    public static void printGrid() {
        System.out.println();
        for (int i = 0; i < gridSize*gridSize; i++) {
            if ((i % gridSize == 0) && (i > 0)) {
                System.out.println();
            }
            System.out.print(grid.get(i) + " ");
        }
        System.out.println();
    }
}