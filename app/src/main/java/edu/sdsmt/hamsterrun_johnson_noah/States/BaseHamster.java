package edu.sdsmt.hamsterrun_johnson_noah.States;

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class BaseHamster extends State {

    private Game game;
    private StateMachine stateMachine;
    private final int BASE_MOVE_COST = 1;

    public BaseHamster(Game game, StateMachine stateMachine) {
        this.game = game;
        this.stateMachine = stateMachine;
    }

    public void endTask() {

    }

    public void startTask() {
        game.setMoveCost(BASE_MOVE_COST);
    }

    public void maintenanceTask() {
        game.setPickupFlag(true);
        if(game.isLost() || game.isWon()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
        else if (game.getResetFlag()) {
            stateMachine.setState(StateMachine.StateEnum.Base);
            game.reset();
        }
        else if(game.getIsZooming())
            stateMachine.setState(StateMachine.StateEnum.Zooming);
        else if(game.getFood() >= 15)
            stateMachine.setState(StateMachine.StateEnum.Heavy);
    }

    public String getStateName() {
        return this.getClass().getName();
    }
}
