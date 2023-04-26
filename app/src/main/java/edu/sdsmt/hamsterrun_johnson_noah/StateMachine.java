package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Primary class for State Machine
 */

// import statements
import edu.sdsmt.hamsterrun_johnson_noah.States.BaseHamster;
import edu.sdsmt.hamsterrun_johnson_noah.States.EndedGame;
import edu.sdsmt.hamsterrun_johnson_noah.States.HeavyHamster;
import edu.sdsmt.hamsterrun_johnson_noah.States.ZoomingHamster;

public class StateMachine {

    // enum for the states
    public enum StateEnum {Base, Heavy, Zooming, Ended}

    // private variables
    private StateEnum state = StateEnum.Base;
    private State[] stateArray = null;
    private MainActivity mainActivity;

    /* Description: constructor for StateMachine class
     * Params: game - the main game class that is being run
     *         mainActivity - the corresponding mainActivity for this run
     */
    StateMachine(Game game, MainActivity mainActivity) {
        // set the mainActivity
        this.mainActivity = mainActivity;

        // set the stateArray
        stateArray = new State[]{
                new BaseHamster(game, this),
                new HeavyHamster(game, this),
                new ZoomingHamster(game, this),
                new EndedGame(game, this)
        };
    }


    /* Description: function that passes along the call to do the maintenance task for the current state
     */
    public void doMaintenanceTask( ) {
        stateArray[state.ordinal()].maintenanceTask();
    }

    /* Description: function to handle the end of the game. Called from the EndedGame state
     * Params: endingDialogMessage - the message to be displayed in the ending dialog
     */
    public void endGame(String endingDialogMessage) {
        mainActivity.showEndingDialog(endingDialogMessage);
    }

    /* Description: gets the name of the current state
     * Returns: the name of the current state as a string
     */
    public String getCurrentStateName() {
        return stateArray[state.ordinal()].getStateName();
    }

    /* Description: sets the state that should be active
     * Params: state - the state which should be currently active
     */
    public void setState(StateEnum state) {
        stateArray[this.state.ordinal()].endTask();
        this.state = state;
        stateArray[this.state.ordinal()].startTask();
    }
}
