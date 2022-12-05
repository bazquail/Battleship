import java.util.ArrayList;
import java.util.Random;

public class BlockSetter {
    ArrayList<String> usedBlocks = new ArrayList<>();
    ArrayList<String> possibleBlocks = new ArrayList<>();
    int gridSize = 0;
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public void setShipBlocks(ArrayList<Ship> ships, int shipCount) {
        gridSize = shipCount + 4;
        for (Ship ship : ships) {
            int bound = gridSize - ship.getLength();
            boolean checkingBlocks = true;

            while (checkingBlocks) { //loop will scrap new ship and try again if there is a collision with an existing ship
                ArrayList<String> shipBlocks = new ArrayList<>();
                Random randLet = new Random();
                int randomLetter;
                Random randNum = new Random();
                int randomNumber;
                boolean vertical = randNum.nextInt(100) % 2 == 0; //randomizes arrangement distribution

                if (vertical) { //align current ship's blocks vertically in the grid
                    randomNumber = randNum.nextInt(gridSize);
                    randomLetter = randLet.nextInt(bound);
                    for (int i = 0; i < ship.getLength(); i++) {
                        shipBlocks.add(letters[randomLetter + i] + numbers[randomNumber]);
                    }
                } else { //align current ship's blocks horizontally in the grid
                    randomNumber = randNum.nextInt(bound);
                    randomLetter = randLet.nextInt(gridSize);
                    for (int i = 0; i < ship.getLength(); i++) {
                        shipBlocks.add(letters[randomLetter] + numbers[randomNumber + i]);
                    }
                }
                for (String shipBlock : shipBlocks) { //checks every block in the current ship against used blocks
                    if (usedBlocks.contains(shipBlock)) {
                        checkingBlocks = true; //if one matches, break out and try another random set of blocks
                        break;
                    } else {
                        checkingBlocks = false;
                    }
                }
                if (!checkingBlocks) { //if no conflict, finalize the ship and add its blocks to the used list
                    ship.setBlocks(shipBlocks);
                    usedBlocks.addAll(shipBlocks);
                }
            }
        }
    }
    public void setPossibleBlocks() { //creates a list of possible blocks for a given grid size
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                possibleBlocks.add(letters[i] + numbers[j]);
            }
        }
    }
    public ArrayList<String> getPossibleBlocks() {
        return possibleBlocks;
    }
}
