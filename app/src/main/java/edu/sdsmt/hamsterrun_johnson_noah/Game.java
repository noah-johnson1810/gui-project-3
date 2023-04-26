package edu.sdsmt.hamsterrun_johnson_noah;

import java.util.Objects;

public class Game {

    // public constant variables
    public static final int GRID_SIZE = 5;
    public static final int BARS_LIMIT = 5;
    public static final int WIN_AMOUNT = 15;
    public static final int FOOD_PICK_AMOUNT = 5;

    // private variables accessed by getters/setters
    private int moveCost = 1;
    private Location location = new Location(0, 0);
    private int energyLevel = 10;
    private int foodInPouches = 0;
    private int storedFood = 0;
    private int zoomsLeft = 0;
    private int moveCount = 0;
    private boolean isLost = false;
    private boolean isZooming = false;
    private boolean pickupFlag = true;
    private boolean resetFlag = false;
    private boolean pickedUpByHuman = false;
    private Board board = new Board();
    public Location getLocation() {
        return this.location;
    }

    void move(int x, int y) {
        if (this.location.x + x < 0 || this.location.x + x > 4 || this.location.y + y < 0 || this.location.y + y > 4)
            return;
        this.location.x += x;
        this.location.y += y;
        this.moveCount++;
        this.energyLevel -= this.moveCost;
        if (this.energyLevel < 0)
            setIsLost(true);
        if (Objects.equals(this.board.board.get(this.location.y * 5 + this.location.x).type, "bars")) {
            passThroughBars();
        }
        if (this.location.x == 4 && this.location.y == 4)
            depositFoodStores();
        if (this.pickupFlag)
            this.pickup();
    }

    public void depositFoodStores() {
        this.storedFood += foodInPouches;
        foodInPouches = 0;
    }

    public void pickup() {
        Square currentSquare = this.board.board.get(this.location.y * 5 + this.location.x);
        currentSquare.pickup(this);
    }

    void eat() {
        if (foodInPouches > 0) {
            this.foodInPouches--;
            setEnergy(this.energyLevel + 5);
        }
    }

    public int getFood() {
        return this.foodInPouches;
    }

    int getHomeStores() {
        return this.storedFood;
    }

    int getEnergy() {
        return this.energyLevel;
    }

    void setEnergy(int newEnergyLevel) {
        if (newEnergyLevel > 15)
            newEnergyLevel = 15;
        this.energyLevel = newEnergyLevel;
    }

    int getMoves() {
        return this.moveCount;
    }

    int getZoomsLeft() {
        return this.zoomsLeft;
    }

    public boolean isLost() {
        return isLost;
    }

    public boolean isWon() {
        return (this.storedFood >= WIN_AMOUNT);
    }

    public boolean getIsZooming() {
        return isZooming;
    }

    public void setIsZooming(boolean isZooming) {
        this.isZooming = isZooming;
    }

    public void reset() {
        this.moveCount = 0;
        this.energyLevel = 10;
        this.foodInPouches = 0;
        this.location.x = 0;
        this.location.y = 0;
        this.zoomsLeft = 0;
        this.storedFood = 0;
        this.moveCost = 1;
        this.pickedUpByHuman = false;
        isLost = false;
        isZooming = false;
        pickupFlag = true;
        resetFlag = false;
    }

    Location getPlayerLocation() {
        return this.location;
    }

    public void addToFoodInPouches(int amount) {
        this.foodInPouches += amount;
        if (this.foodInPouches > 20) {
            this.foodInPouches = 20;
        }
    }

    public void addToZoomsLeft(int amount) {
        this.zoomsLeft += amount;
    }

    public void setIsLost(boolean value) {
        this.isLost = value;
    }

    public void passThroughBars() {
        if (this.foodInPouches > BARS_LIMIT)
            this.foodInPouches = BARS_LIMIT;
    }

    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }

    public void setPickupFlag(boolean newValue) {
        this.pickupFlag = newValue;
    }

    public boolean getResetFlag() {
        return this.resetFlag;
    }

    public void setResetFlag(boolean newValue) {
        this.resetFlag = newValue;
    }

    public int getEnergyLevel() {
        return this.energyLevel;
    }
    
    public boolean getPickedUpByHumanFlag() {
        return this.pickedUpByHuman;
    }

    public void setPickedUpByHumanFlag(boolean newValue) {
        this.pickedUpByHuman = newValue;
    }

    public void setZoomsLeft(int newZooms) {
        this.zoomsLeft = newZooms;
    }

    public void setFood(int newFood) {
        this.foodInPouches = newFood;
    }

    public void setStoredFood(int newStoredFood) {
        this.storedFood = newStoredFood;
    }

    public void setMoves(int newMoves) {
        this.moveCount = newMoves;
    }

    public void setLocationX(int newLocationX) {
        this.location.x = newLocationX;
    }

    public void setLocationY(int newLocationY) {
        this.location.y = newLocationY;
    }
}
