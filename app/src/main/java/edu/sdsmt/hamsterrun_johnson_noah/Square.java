package edu.sdsmt.hamsterrun_johnson_noah;

import java.util.Objects;

import edu.sdsmt.hamsterrun_johnson_noah.Location;

public class Square {
    public Location location;
    public String type;
    public int quantity = 1;

    Square(Location newLocation, String newType) {
        this(newLocation, newType, 1);
    }

    Square(Location newLocation, String newType, int newQuantity) {
        this.location = newLocation;
        this.type = newType;
        this.quantity = newQuantity;
    }

    public void pickup(Game hamster) {
        if(Objects.equals(this.type, "food")) {
            if(quantity > 0) {
                hamster.addToFoodInPouches(Game.FOOD_PICK_AMOUNT);
                this.quantity--;
            }
        }
        else if (Objects.equals(this.type, "zoom")) {
            hamster.addToZoomsLeft(this.quantity);
            this.quantity = 0;
        }
        else if (Objects.equals(this.type, "person")) {
            hamster.setIsLost(true);
        }
    }
}
