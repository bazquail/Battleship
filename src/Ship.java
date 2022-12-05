import java.util.ArrayList;

public class Ship {
    private ArrayList<String> blocks;
    private String name;
    private int length = 0;

    public boolean isHit(String userGuess) {
        if (blocks.contains(userGuess)) { //checks if the ship is on a guessed block
            blocks.remove(userGuess);
            return true;
        } else {
            return false;
        }
    }
    public boolean isSunk() { //a ship is sunk when all blocks are removed
        return blocks.isEmpty();
    }
    public void setBlocks(ArrayList<String> location) {
        blocks = location;
        //System.out.print(blocks); //tells you where the boats are before the game begins
    }
    public void setName(String inputName) {
        name = inputName;
    }
    public void setLength(int shipLength) {
        if (shipLength <= 5) { //keeps ships from being too large
            length = shipLength;
        } else {
            length = 3;
        }
    }
    public int getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
}
