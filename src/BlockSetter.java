import java.util.ArrayList;
import java.util.Random;

public class BlockSetter {
    static ArrayList<String> usedBlocks = new ArrayList<>();
    static ArrayList<String> possibleBlocks = new ArrayList<>();
    static int gridSize = 0;
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public void setShipBlocks(ArrayList<Ship> ships, int shipCount) {
        gridSize = shipCount + 4;
        int count = 1;
        for (Ship ship : ships) { //repeats for every ship
            int bound = gridSize - ship.getLength();
            boolean isCheckingBlocks = true;

            while (isCheckingBlocks) { //scraps new ship and tries again if there is a collision with an existing ship
                ArrayList<String> shipBlocks = new ArrayList<>();
                Random randLet = new Random();
                int randomLetter;
                Random randNum = new Random();
                int randomNumber;
                boolean vertical = count % 2 == 0; //ensures equal arrangement distribution

                if (vertical) { //aligns the ship's blocks vertically in the grid
                    randomNumber = randNum.nextInt(gridSize);
                    randomLetter = randLet.nextInt(bound);
                    for (int i = 0; i < ship.getLength(); i++) {
                        shipBlocks.add(letters[randomLetter + i] + numbers[randomNumber]);
                    }
                } else { //aligns the ship's blocks horizontally in the grid
                    randomNumber = randNum.nextInt(bound);
                    randomLetter = randLet.nextInt(gridSize);
                    for (int i = 0; i < ship.getLength(); i++) {
                        shipBlocks.add(letters[randomLetter] + numbers[randomNumber + i]);
                    }
                }
                isCheckingBlocks = false;
                for (String shipBlock : shipBlocks) { //checks every block in the new ship against used blocks
                    if (usedBlocks.contains(shipBlock)) {
                        isCheckingBlocks = true; //makes it continue looping to try for another set of blocks
                        break;
                    }
                }
                if (!isCheckingBlocks) { //if no conflict, set the ship's blocks and add them to the used list
                    ship.setBlocks(shipBlocks);
                    usedBlocks.addAll(shipBlocks);
                }
            }
            count++;
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
