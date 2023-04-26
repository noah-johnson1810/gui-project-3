package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Location struct for use by Game
 */

public class Location {
    // since this is just the equivalent of a c++ struct, x and y are public
    public int x;
    public int y;

    /* Description: constructor for Location
     * Params: x - the x coordinate
     *         y - the y coordinate
     */
    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
