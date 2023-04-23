package edu.sdsmt.hamsterrun_johnson_noah;

public abstract class State {
    public abstract void endTask();
    public abstract void startTask();
    public abstract void maintenanceTask();
    public abstract String getStateName();
}
