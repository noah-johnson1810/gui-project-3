package edu.sdsmt.hamsterrun_johnson_noah.States;

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class EndedGame extends State {

    private Game game;
    private StateMachine stateMachine;
    public EndedGame(Game game, StateMachine stateMachine) {
        this.game = game;
        this.stateMachine = stateMachine;
    }

    public void endTask() {

    }

    public void startTask() {
        if(game.isWon())
            stateMachine.endGame("You won!");
        else {
            if(game.getEnergyLevel() < 0)
                stateMachine.endGame("You lost: energy level < 0");
            if(game.getPickedUpByHumanFlag())
                stateMachine.endGame("You lost: picked up by human");
        }
    }

    public void maintenanceTask() {

    }

    public String getStateName() {
        return this.getClass().getName();
    }
}
