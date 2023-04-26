package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSc 468 - GUI Programming
 * Description: Square class for use in hamster run game
 */

import java.util.Objects;

public class Square {

    // private variables
    private String type;
    private int quantity = 1;


    /* Description: constructor for a square which doesn't have a quantity
     * Params: newType - the type of this square
     */
    Square(String newType) {
        // forward to complete constructor with quantity of 1
        this(newType, 1);
    }


    /* Description: constructor for a square which has both type and quantity
     * Params: newType - the type of this square
     *         newQuantity - the quantity of items in this square
     */
    Square(String newType, int newQuantity) {
        this.type = newType;
        this.quantity = newQuantity;
    }


    /* Description: gets the type of this square
     * Returns: the type of this square as a string
     */
    public String getType() {
        return this.type;
    }


    /* Description: performs a pickup based on the hamster passed in, kind of like the visitor pattern
     * Params: hamster - the hamster game which will be affected by this square pickup event
     */
    public void pickup(Game hamster) {
        // check if this is a food square
        if(Objects.equals(this.type, "food")) {
            // if there's still food left in the square, add food to the hamster's pouches
            if(quantity > 0) {
                hamster.addToFoodInPouches(Game.FOOD_PICK_AMOUNT);
                // decrease the square's food quantity
                this.quantity--;
            }
        }
        // if this is a zoom square, add a zoom to the hamster and remove it from this square
        else if (Objects.equals(this.type, "zoom")) {
            hamster.addToZoomsLeft(this.quantity);
            this.quantity = 0;
        }
        // if this is a person, set isLost to true in the hamster as they've been caught
        else if (Objects.equals(this.type, "person")) {
            hamster.setIsLost(true);
            hamster.setPickedUpByHumanFlag(true);
        }
    }
}
