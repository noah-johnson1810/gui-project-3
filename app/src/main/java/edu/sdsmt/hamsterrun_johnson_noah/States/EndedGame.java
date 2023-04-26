package edu.sdsmt.hamsterrun_johnson_noah.States;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: EndedGame state which implements State abstract class
 */

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class EndedGame extends State {

    // private variables
    private Game game;
    private StateMachine stateMachine;

    /* Description: constructor for EndedGame class
     * Params: game - the currently active game
     *         stateMachine - the stateMachine which is utilizing this base hamster state
     */
    public EndedGame(Game game, StateMachine stateMachine) {
        // set the game and state machine
        this.game = game;
        this.stateMachine = stateMachine;
    }


    /* Description: implements endTask from the abstract class State
     */
    public void endTask() {
        // nothing needs to be done when this task is ended
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

    }


    /* Description: the actions which should be taken when this state is mounted
     */
    public void startTask() {
        // check if game is won or lost, then call endGame with appropriate closing dialog message
        if(game.isWon())
            stateMachine.endGame("You won!");
        else {
            // check if lost by energy level or picked up by human
            if(game.getEnergy() < 0)
                stateMachine.endGame("You lost: energy level < 0");
            if(game.getPickedUpByHumanFlag())
                stateMachine.endGame("You lost: picked up by human");
        }
    }
}
