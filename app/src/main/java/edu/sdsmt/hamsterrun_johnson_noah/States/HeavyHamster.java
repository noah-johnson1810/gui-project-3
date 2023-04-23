package edu.sdsmt.hamsterrun_johnson_noah.States;

import edu.sdsmt.hamsterrun_johnson_noah.Game;
import edu.sdsmt.hamsterrun_johnson_noah.State;
import edu.sdsmt.hamsterrun_johnson_noah.StateMachine;

public class HeavyHamster extends State {
    private Game game;
    private StateMachine stateMachine;
    private final int HEAVY_MOVE_COST = 2;

    public HeavyHamster(Game game, StateMachine stateMachine) {
        this.game = game;
        this.stateMachine = stateMachine;
    }

    public void endTask() {

    }

    public void startTask() {
        game.setMoveCost(HEAVY_MOVE_COST);
    }

    public void maintenanceTask() {
        game.setPickupFlag(true);
        if(game.isLost() || game.isWon())
            stateMachine.setState(StateMachine.StateEnum.Ended);
        if(game.getIsZooming())
            stateMachine.setState(StateMachine.StateEnum.Zooming);
        else if(game.getFood() < 15)
            stateMachine.setState(StateMachine.StateEnum.Base);
    }

    public String getStateName() {
        return "edu.sdsmt.hamsterrun_johnson_noah.States.HeavyHamster";
    }
}
