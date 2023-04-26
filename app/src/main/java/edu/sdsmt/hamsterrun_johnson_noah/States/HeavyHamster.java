package edu.sdsmt.hamsterrun_johnson_noah.States;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Heavy Hamster State - implements State abstract class
 */

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class HeavyHamster extends State {

    // private variables
    private final int HEAVY_MOVE_COST = 2;
    private Game game;
    private StateMachine stateMachine;

    /* Description: constructor for HeavyHamster class
     * Params: game - the currently active game
     *         stateMachine - the stateMachine which is utilizing this base hamster state
     */
    public HeavyHamster(Game game, StateMachine stateMachine) {
        // set the game and stateMachine
        this.game = game;
        this.stateMachine = stateMachine;
    }

    /* Description: implements endTask from the abstract class State
     */
    public void endTask() {
        // nothing needs to be done
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
        // set the pickup flag because we're going to be picking up while in this state
        game.setPickupFlag(true);

        // check for win/loss
        if(game.isLost() || game.isWon())
            stateMachine.setState(StateMachine.StateEnum.Ended);

        // check for reset flag from game
        else if (game.getResetFlag()) {
            stateMachine.setState(StateMachine.StateEnum.Base);
            game.reset();
        }

        // check if we need to move to the zooming state
        if(game.getIsZooming())
            stateMachine.setState(StateMachine.StateEnum.Zooming);

        // check if we need to move back to base hamster state
        else if(game.getFood() < 15)
            stateMachine.setState(StateMachine.StateEnum.Base);
    }

    /* Description: the actions which should be taken when this state is mounted
     */
    public void startTask() {
        game.setMoveCost(HEAVY_MOVE_COST);
    }
}
