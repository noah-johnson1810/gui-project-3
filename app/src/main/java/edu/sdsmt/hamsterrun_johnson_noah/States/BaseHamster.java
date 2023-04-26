package edu.sdsmt.hamsterrun_johnson_noah.States;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Base Hamster State - implements State abstract class
 */

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class BaseHamster extends State {

    // private variables
    private final int BASE_MOVE_COST = 1;
    private Game game;
    private StateMachine stateMachine;


    /* Description: constructor for BaseHamster class
     * Params: game - the currently active game
     *         stateMachine - the stateMachine which is utilizing this base hamster state
     */
    public BaseHamster(Game game, StateMachine stateMachine) {
        // set the game and state machine
        this.game = game;
        this.stateMachine = stateMachine;
    }

    /* Description: implements endTask from the abstract class State
     */
    public void endTask() {
        // nothing to do here
    }

    /* Description: the actions which should be taken on each update that this state is active
     */
    public void maintenanceTask() {
        // we're going to be picking up in this state
        game.setPickupFlag(true);

        // check for game over
        if(game.isLost() || game.isWon()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }

        // check for reset flag from the game
        else if (game.getResetFlag()) {
            stateMachine.setState(StateMachine.StateEnum.Base);
            game.reset();
        }

        // check if we need to move to the zooming state
        else if(game.getIsZooming())
            stateMachine.setState(StateMachine.StateEnum.Zooming);

        // check if we need to move to heavy hamster state
        else if(game.getFood() >= 15)
            stateMachine.setState(StateMachine.StateEnum.Heavy);
    }

    /* Description: gets this state name
     * Returns: this state name as a string
     */
    public String getStateName() {
        return this.getClass().getName();
    }

    /* Description: the actions which should be taken when this state is mounted
     */
    public void startTask() {
        // set the move cost to the base move cost constant
        game.setMoveCost(BASE_MOVE_COST);
    }
}
