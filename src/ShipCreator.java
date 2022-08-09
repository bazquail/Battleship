import java.util.ArrayList;

public class ShipCreator {
    public ArrayList<Ship> ships = new ArrayList<>();
    public ArrayList<Ship> createShips(int numShips, String[] names) { //creates and returns the requested number of ships
        for (int j = 0; j < numShips; j++) {
            Ship ship = new Ship();
            ship.setName(names[j]);
            ship.setLength(j+3);
            ships.add(ship);
        }
        return ships;
    }
}
