package edu.sdsmt.hamsterrun_johnson_noah;

import java.util.Objects;

public class Game {

    static final int BARS_LIMIT = 5;
    static final int WIN_AMOUNT = 30;
    public static final int FOOD_PICK_AMOUNT = 5;


    private Location location = new Location(0, 0);
    private int energyLevel = 10;
    private int foodInPouches = 0;
    private int storedFood = 0;
    private int zoomsLeft = 0;
    private int moveCount = 0;
    private boolean isLost = false;

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
        this.energyLevel -= 1;
        if(this.energyLevel < 0)
            setIsLost(true);
        if(Objects.equals(this.board.board.get(this.location.y * 5 + this.location.x).type, "bars")) {
            passThroughBars();
        }
        if(this.location.x == 4 && this.location.y == 4)
            depositFoodStores();
    }

    public void depositFoodStores() {
        this.storedFood += foodInPouches;
        foodInPouches = 0;
    }

    void pickup() {
        Square currentSquare = this.board.board.get(this.location.y * 5 + this.location.x);
        currentSquare.pickup(this);
    }

    void eat() {
        if(foodInPouches > 0) {
            this.foodInPouches--;
            setEnergy(this.energyLevel + 5);
        }
    }

    int getFood() {
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

    boolean isLost() {
        return isLost;
    }

    boolean isWon() {
        return ( this.storedFood >= WIN_AMOUNT);
    }

    void reset() {
        this.moveCount = 0;
        this.energyLevel = 10;
        this.foodInPouches = 20;
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
        this.foodInPouches = BARS_LIMIT;
    }
}
