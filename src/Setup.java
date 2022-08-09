public class Setup {
    private int shipCount = 0;

    public void setShipCount(int numRequestedShips) { //sets sent number as ship count
        shipCount = numRequestedShips;
    }
    public int getShipCount() {
        return shipCount;
    }
    public boolean isValidNumber() { //checks to see if sent number is 3, 4, or 5
        return (shipCount > 2) && (shipCount < 6);
    }
}
