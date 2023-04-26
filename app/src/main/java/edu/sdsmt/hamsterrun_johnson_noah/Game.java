package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468
 * Description: Game class for Hamster Run project
 */

import java.util.Objects;

public class Game {

    // public constant variables
    public static final int BARS_LIMIT = 5;
    public static final int FOOD_PICK_AMOUNT = 5;
    public static final int GRID_SIZE = 5;
    public static final int WIN_AMOUNT = 15;

    // private variables accessed by getters/setters
    private Board board = new Board();
    private int energyLevel = 10;
    private int foodInPouches = 0;
    private boolean isLost = false;
    private boolean isZooming = false;
    private Location location = new Location(0, 0);
    private int moveCost = 1;
    private int moveCount = 0;
    private boolean pickedUpByHuman = false;
    private boolean pickupFlag = true;
    private boolean resetFlag = false;
    private int storedFood = 0;
    private int zoomsLeft = 0;


    /* Description: adds to food in pouches
     * Params: amount - the amount to be added to the food in pouches
     */
    public void addToFoodInPouches(int amount) {
        this.foodInPouches += amount;
        if (this.foodInPouches > 20) {
            this.foodInPouches = 20;
        }
    }


    /* Description: adds to the number of zooms left
     * Params: amount - the amount of zooms to add
     */
    public void addToZoomsLeft(int amount) {
        this.zoomsLeft += amount;
    }


    /* Description: deposits the current food in pouches into the home stores
     */
    public void depositFoodStores() {
        this.storedFood += foodInPouches;
        foodInPouches = 0;
    }


    /* Description: performs one eat action
     */
    void eat() {
        if (foodInPouches > 0) {
            this.foodInPouches--;
            setEnergy(this.energyLevel + 5);
        }
    }


    /* Description: gets the current energy level
     * Returns: the current energy level
     */
    public int getEnergy() {
        return this.energyLevel;
    }


    /* Description: gets the current amount of food in pouches
     * Returns: the current amount of food in pouches
     */
    public int getFood() {
        return this.foodInPouches;
    }


    /* Description: gets the current home stores
     * Returns: the current home stores amount
     */
    int getHomeStores() {
        return this.storedFood;
    }


    /* Description: gets whether the hamster is currently zooming
     * Returns: false if the hamster is not zooming, true if the hamster is zooming
     */
    public boolean getIsZooming() {
        return isZooming;
    }


    /* Description: gets the current number of moves that has been made this game
     * Returns: the current number of moves made this game
     */
    int getMoves() {
        return this.moveCount;
    }


    /* Description: gets the current location of the hamster
     * Returns: the current location of the hamster
     */
    Location getPlayerLocation() {
        return this.location;
    }


    /* Description: gets the flag that indicates whether the hamster has been caught by human
     * Returns: true if the hamster has been caught, false if the hamster has not been caught
     */
    public boolean getPickedUpByHumanFlag() {
        return this.pickedUpByHuman;
    }


    /* Description: gets the flag that indicates whether the game should be reset
     * Returns: true if the game should be reset, false if it should not
     */
    public boolean getResetFlag() {
        return this.resetFlag;
    }


    /* Description: gets the number of zooms left
     * Returns: the number of zooms left
     */
    int getZoomsLeft() {
        return this.zoomsLeft;
    }


    /* Description: gets the current location of the hamster
     * Returns: the current location of the hamster
     */
    public Location getLocation() {
        return this.location;
    }


    /* Description: gets whether the game is lost
     * Returns: true if the game is lost, false if the game is not lost
     */
    public boolean isLost() {
        return isLost;
    }


    /* Description: gets whether the game is won
     * Returns: true if the game is won, false if the game is not won
     */
    public boolean isWon() {
        return (this.storedFood >= WIN_AMOUNT);
    }


    /* Description: moves the hamster the number of squares indicated by the parameters
     * Params: x - the number of squares to move in the x direction
     *         y - the number of squares to move in the y direction
     */
    void move(int x, int y) {
        // check if the location is out of range
        if (this.location.x + x < 0 || this.location.x + x > 4 || this.location.y + y < 0 || this.location.y + y > 4)
            return;

        // add to the current location
        this.location.x += x;
        this.location.y += y;

        // increment the move count
        this.moveCount++;

        // perform the cost on the energy level
        this.energyLevel -= this.moveCost;

        // check whether the energy has gone below the minimum
        if (this.energyLevel < 0)
            setIsLost(true);

        // check whether to pass through bars and take the bars toll
        if (Objects.equals(this.board.getBoard().get(this.location.y * 5 + this.location.x).getType(), "bars")) {
            passThroughBars();
        }

        // check whether the hamster is home and should deposit food stores
        if (this.location.x == 4 && this.location.y == 4)
            depositFoodStores();

        // if the hamster is in a state that picks up, then pick up what's on the current square
        if (this.pickupFlag)
            this.pickup();
    }


    /* Description: performs the actions required when the hamster must pass through bars
     */
    public void passThroughBars() {
        // if the food is over the bars limit, set it to the bars limit
        if (this.foodInPouches > BARS_LIMIT)
            this.foodInPouches = BARS_LIMIT;
    }


    /* Description: performs a pickup on the current square
     */
    public void pickup() {
        // get the current square
        Square currentSquare = this.board.getBoard().get(this.location.y * 5 + this.location.x);
        // perform a pickup on this square, the Square will know what behavior to do
        currentSquare.pickup(this);
    }


    /* Description: resets the current game to the initial, fresh state
     */
    public void reset() {
        // reset everything that can be effected in the game
        this.moveCount = 0;
        this.energyLevel = 10;
        this.foodInPouches = 0;
        this.location.x = 0;
        this.location.y = 0;
        this.zoomsLeft = 0;
        this.storedFood = 0;
        this.moveCost = 1;
        this.pickedUpByHuman = false;
        this.isLost = false;
        this.isZooming = false;
        this.pickupFlag = true;
        this.resetFlag = false;
    }


    /* Description: sets the current energy level
     * Params: newEnergyLevel - the new energy level
     */
    void setEnergy(int newEnergyLevel) {
        // make sure the hamster doesn't have more than 15 energy no matter what
        if (newEnergyLevel > 15)
            newEnergyLevel = 15;

        // set the new energy level
        this.energyLevel = newEnergyLevel;
    }


    /* Description: sets the current food level
     * Params: newFood - the new amount of food in pouches
     */
    public void setFood(int newFood) {
        this.foodInPouches = newFood;
    }


    /* Description: sets the isLost flag which indicates whether the game is lost
     * Params: value - the value to set the isLost flag to
     */
    public void setIsLost(boolean value) {
        this.isLost = value;
    }


    /* Description: sets whether the hamster is zooming currently
     * Params: isZooming - the new value for the isZooming flag
     */
    public void setIsZooming(boolean isZooming) {
        this.isZooming = isZooming;
    }


    /* Description: sets the X-coordinate of the current location
     * Params: newLocationX - the new X location
     */
    public void setLocationX(int newLocationX) {
        this.location.x = newLocationX;
    }


    /* Description: sets the Y-coordinate of the current location
     * Params: newLocationY - the new Y location
     */
    public void setLocationY(int newLocationY) {
        this.location.y = newLocationY;
    }


    /* Description: sets the move cost
     * Params: moveCost - the new cost of each move
     */
    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }


    /* Description: sets the current number of moves made this game
     * Params: newMoves - the number of moves made this game
     */
    public void setMoves(int newMoves) {
        this.moveCount = newMoves;
    }


    /* Description: sets the flag that indicates whether the hamster has been picked up by human
     * Params: newValue - the new value for the pickedUpByHuman flag
     */
    public void setPickedUpByHumanFlag(boolean newValue) {
        this.pickedUpByHuman = newValue;
    }


    /* Description: sets whether or not the hamster should perform pickups on the squares
     * Params: newValue - the new value for this pickup flag
     */
    public void setPickupFlag(boolean newValue) {
        this.pickupFlag = newValue;
    }


    /* Description: sets the reset flag
     * Params: newValue - the new value for the reset flag
     */
    public void setResetFlag(boolean newValue) {
        this.resetFlag = newValue;
    }


    /* Description: sets the amount of food in the home stores
     * Params: newStoredFood - the new amount of food in the home stores
     */
    public void setStoredFood(int newStoredFood) {
        this.storedFood = newStoredFood;
    }


    /* Description: sets the number of zooms left
     * Params: newZooms - the new number of zooms left
     */
    public void setZoomsLeft(int newZooms) {
        this.zoomsLeft = newZooms;
    }
}
