package edu.sdsmt.hamsterrun_johnson_noah.States;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Zooming Hamster State - implements State abstract class
 */

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class ZoomingHamster extends State {

    // private variables
    private final int ZOOMING_MOVE_COST = 2;
    private Game game;
    private StateMachine stateMachine;
    private int zoomMovesLeft;


    /* Description: constructor for ZoomingHamster class
     * Params: game - the currently active game
     *         stateMachine - the stateMachine which is utilizing this base hamster state
     */
    public ZoomingHamster(Game game, StateMachine stateMachine) {
        this.game = game;
        this.stateMachine = stateMachine;
    }

    /* Description: implements endTask from the abstract class State
     */
    public void endTask() {
        // update in game that this zoom is over
        game.setIsZooming(false);
    }

    /* Description: gets this state name
     * Returns: this state name as a string
     */
    public String getStateName() {
        return this.getClass().getName();
    }

    /* Description: the actions which should be taken on each update that this state is active
     */
    public void maintenanceTask() {
        // we're not picking up in this state, so set the pickup flag in game to false
        game.setPickupFlag(false);

        // check for game over
        if(game.isLost() || game.isWon())
            stateMachine.setState(StateMachine.StateEnum.Ended);

        // check for game reset
        else if (game.getResetFlag()) {
            stateMachine.setState(StateMachine.StateEnum.Base);
            game.reset();
        }

        // if zoom is over, check whether to go to heavy or base hamster state
        if(zoomMovesLeft == 0) {
            if (game.getFood() >= 15)
                stateMachine.setState(StateMachine.StateEnum.Heavy);
            else
                stateMachine.setState(StateMachine.StateEnum.Base);
        }

        // decrement zoom moves left if we have any left
        zoomMovesLeft--;
    }

    /* Description: the actions which should be taken when this state is mounted
     */
    public void startTask() {
        // set the move cost for zooming hamster
        game.setMoveCost(ZOOMING_MOVE_COST);
        // each zoom gets two moves
        zoomMovesLeft = 2;
    }
}
