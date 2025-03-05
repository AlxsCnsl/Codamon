package com.example.codamon.core.action;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;

public abstract class Action {
    protected Boolean allowed = true;
    protected int priority;
    public Action(int priority){
        this.priority = priority;
    }
    public Boolean getAllowed(){return this.allowed;}
    public abstract void execute(Pokemon self, Pokemon target, Battle battle);

    public int getPriority() {
        return this.priority;
    }
    public Boolean setAllowed(){
        return this.allowed;
    }
}
