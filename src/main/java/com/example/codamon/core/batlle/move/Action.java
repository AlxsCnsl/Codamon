package com.example.codamon.core.batlle.move;

public abstract class Action {
    protected Boolean allowed = true;
    protected int priority;
    public Action(int priority){
        this.priority = priority;
    }

    public Boolean getAllowed(){return this.allowed;}

    public abstract void execute();

    public int getPriority() {
        return this.priority;
    }

    public Boolean setAllowed(){
        return this.allowed;
    }
}
