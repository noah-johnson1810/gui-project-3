package edu.sdsmt.hamsterrun_johnson_noah.States;

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class ZoomingHamster extends State {

    private Game game;
    private StateMachine stateMachine;
    private final int ZOOMING_MOVE_COST = 2;

    private int zoomMovesLeft;

    public ZoomingHamster(Game game, StateMachine stateMachine) {
        this.game = game;
        this.stateMachine = stateMachine;
    }

    public void endTask() {
        game.setIsZooming(false);
    }

    public void startTask() {
        game.setMoveCost(ZOOMING_MOVE_COST);
        zoomMovesLeft = 2;
    }

    public void maintenanceTask() {
        game.setPickupFlag(false);
        if(game.isLost() || game.isWon())
            stateMachine.setState(StateMachine.StateEnum.Ended);
        else if (game.getResetFlag()) {
            stateMachine.setState(StateMachine.StateEnum.Base);
            game.reset();
        }
        if(zoomMovesLeft == 0) {
            if (game.getFood() >= 15)
                stateMachine.setState(StateMachine.StateEnum.Heavy);
            else
                stateMachine.setState(StateMachine.StateEnum.Base);
        }
        zoomMovesLeft--;
    }

    public String getStateName() {
        return this.getClass().getName();
    }
}
