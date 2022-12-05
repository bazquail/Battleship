public class Setup {
    private int shipCount = 0;

    public void setShipCount(int numRequestedShips) {
        shipCount = numRequestedShips;
    }
    public int getShipCount() {
        return shipCount;
    }
    public boolean isValidNumber() { //checks to see if user input is 3, 4, or 5; if not, new input needed
        return (shipCount > 2) && (shipCount < 6);
    }
}
