import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BattleshipGame {
    String[] names = {"The Destroyer", "The Submarine", "The Aircraft Carrier", "The Cruiser", "The Frigate"};
    int numShips;
    int gridSize;
    ArrayList<Ship> ships = new ArrayList<>();
    ArrayList<String> guessedBlocks = new ArrayList<>();
    ArrayList<String> grid = new ArrayList<>();
    Setup setup = new Setup();
    ShipCreator shipCreator = new ShipCreator();
    BlockSetter blockSetter = new BlockSetter();

    public static void main(String[] args) {
        new BattleshipGame().go();
    }
    public void go() {
        setupGame();
        createShips();
        setShipLocations();
        printGrid();
        playGame();
    }
    public void setupGame() {
        System.out.print("Would you like to play with 3, 4, or 5 ships? ");
        Scanner input = new Scanner(System.in);
        int numRequestedShips;
        if (input.hasNextInt()) {
            numRequestedShips = input.nextInt();
            setup.setShipCount(numRequestedShips); //passes the entered value to check if it is valid
        }
        while (!setup.isValidNumber()) { //if it isn't, repeat until a valid integer is entered
            System.out.print("Please enter either 3, 4, or 5! ");
            Scanner newInput = new Scanner(System.in);
            if (newInput.hasNextInt()) {
                numRequestedShips = newInput.nextInt();
                setup.setShipCount(numRequestedShips);
            }
        }
        numShips = setup.getShipCount();
        gridSize = numShips + 4;
        String aOrAn = " a ";
        if (gridSize % 2 == 0) {
            aOrAn = " an ";
        }
        String boardDimensions = gridSize + " by " + gridSize + " board.";
        System.out.println("\nThere will be " + numShips + " ships on" + aOrAn + boardDimensions);
        System.out.println("Your job is to sink them all. Good luck!");
        try {
            TimeUnit.SECONDS.sleep(2); //gives the user time to read before printing next text
        } catch (InterruptedException ie) {
            System.out.println("First sleep() failed.");
        }
    }
    public void createShips() {
        ships = shipCreator.createShips(numShips, names);
        System.out.println();
        for (Ship ship : ships) {
            System.out.println(ship.getName() + " is " + ship.getLength() + " blocks long.");
        }
        try {
            TimeUnit.SECONDS.sleep(2); //gives the user time to read before printing board
        } catch (InterruptedException ie) {
            System.out.println("First sleep() failed.");
        }
    }
    public void setShipLocations() {
        blockSetter.setShipBlocks(ships, numShips);
        blockSetter.setPossibleBlocks(); //sets a list that outlines all possible guesses
        grid = blockSetter.getPossibleBlocks(); //creates a grid initially equal to all possible guesses
    }
    public void playGame() {
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

            while (badGuess) { //checks for invalid or repeated guesses
                if (blockSetter.getPossibleBlocks().contains(userGuess)) {
                    if (guessedBlocks.contains(userGuess)) {
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

            int index = grid.indexOf(userGuess);
            grid.remove(userGuess);
            grid.add(index, replacementBlock); //adds the hit or miss indication block to visually update the grid for next print
            printGrid();
            System.out.println(hitOrMiss);
            if (sunkShips == setup.getShipCount()) { //end game if all ships are sunk
                isGameGoing = false;
            }
        }
        System.out.println("Game over! Number of guesses: " + guessedBlocks.size());
        System.out.println("Play again? Enter 'y' for yes, anything else to exit.");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine().toLowerCase();
        if (choice.equals("y")) {
            new BattleshipGame().go();
        }
    }
    public void printGrid() {
        System.out.println();
        for (int i = 0; i < gridSize*gridSize; i++) {
            if ((i % gridSize == 0) && (i > 0)) { //starts next row on new line when column limit reached
                System.out.println();
            }
            System.out.print(grid.get(i) + " ");
        }
        System.out.println();
    }
}