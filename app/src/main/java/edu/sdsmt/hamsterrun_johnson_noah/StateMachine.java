package edu.sdsmt.hamsterrun_johnson_noah;

import edu.sdsmt.hamsterrun_johnson_noah.States.BaseHamster;
import edu.sdsmt.hamsterrun_johnson_noah.States.EndedGame;
import edu.sdsmt.hamsterrun_johnson_noah.States.HeavyHamster;
import edu.sdsmt.hamsterrun_johnson_noah.States.ZoomingHamster;

public class StateMachine {
    public enum StateEnum {Base, Heavy, Zooming, Ended}

    private StateEnum state = StateEnum.Base;
    private State[] stateArray = null;

    StateMachine(Game game) {
        stateArray = new State[]{
                new BaseHamster(game, this),
                new HeavyHamster(game, this),
                new ZoomingHamster(game, this),
                new EndedGame(game, this)
        };
    }

    public void setState(StateEnum state) {
        stateArray[this.state.ordinal()].endTask();
        this.state = state;
        stateArray[this.state.ordinal()].startTask();
    }

    public String getCurrentStateName() {
        return stateArray[state.ordinal()].getStateName(); // TODO: not sure about this
    }

    public void doMaintenanceTask( ) {
        stateArray[state.ordinal()].maintenanceTask();
    }
}
