package edu.sdsmt.hamsterrun_johnson_noah;

import java.util.Objects;

public class Game {
    public static final int GRID_SIZE = 5;
    static final int BARS_LIMIT = 5;
    static final int WIN_AMOUNT = 15;
    public static final int FOOD_PICK_AMOUNT = 5;

    private int moveCost;

    private Location location = new Location(0, 0);
    private int energyLevel = 10;
    private int foodInPouches = 0;
    private int storedFood = 0;
    private int zoomsLeft = 0;
    private int moveCount = 0;
    private boolean isLost = false;
    private boolean isZooming = false;
    private boolean pickupFlag = true;

    private StateMachine stateMachine;

    Game() {
        this.stateMachine = new StateMachine(this);
        this.moveCost = 1;
    }
    private Board board = new Board();

    public Location getLocation() {
        return this.location;
    }

    void move(int x, int y) {
        if(this.location.x + x < 0 ||  this.location.x + x > 4 || this.location.y + y < 0 || this.location.y + y > 4)
                return;
        this.location.x += x;
        this.location.y += y;
        this.moveCount++;
        this.energyLevel -= this.moveCost;
        if(this.energyLevel < 0)
            setIsLost(true);
        if(Objects.equals(this.board.board.get(this.location.y * 5 + this.location.x).type, "bars")) {
            passThroughBars();
        }
        if(this.location.x == 4 && this.location.y == 4)
            depositFoodStores();
        if(this.pickupFlag)
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
        if(foodInPouches > 0) {
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
        if(newEnergyLevel > 15)
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
        return ( this.storedFood >= WIN_AMOUNT);
    }

    public boolean getIsZooming() {
        return isZooming;
    }

    public void setIsZooming(boolean isZooming) {
        this.isZooming = isZooming;
    }

    void reset() {
        this.moveCount = 0;
        this.energyLevel = 10;
        this.foodInPouches = 0;
        this.location.x = 0;
        this.location.y = 0;
        this.zoomsLeft = 0;
        this.storedFood = 0;
        this.moveCost = 1;
    }

    Location getPlayerLocation() {
        return this.location;
    }

    public void addToFoodInPouches(int amount) {
        this.foodInPouches += amount;
        if(this.foodInPouches > 20) {
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
        if(this.foodInPouches > BARS_LIMIT)
            this.foodInPouches = BARS_LIMIT;
    }

    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }

    public StateMachine getStateMachine() {
        return this.stateMachine;
    }

    public void setPickupFlag(boolean newValue) {
        this.pickupFlag = newValue;
    }
}
