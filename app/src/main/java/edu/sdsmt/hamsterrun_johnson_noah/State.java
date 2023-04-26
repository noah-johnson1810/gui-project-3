package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Abstract class of State for the state machine in the hamster run game
 */

public abstract class State {
    public abstract void endTask();
    public abstract void startTask();
    public abstract void maintenanceTask();
    public abstract String getStateName();
}
