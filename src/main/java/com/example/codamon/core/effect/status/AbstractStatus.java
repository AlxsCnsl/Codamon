package com.example.codamon.core.effect.status;

import com.example.codamon.core.action.move.Move;

public abstract class AbstractStatus {
    protected   String name;
    protected int durationTurns = -1;
    protected int activeTurn = 0;
    public AbstractStatus(){}

    public void applyExecuteMovePhaseEffect(){}
    public void applyEndPhaseEffect(){
        this.activeTurn += 1;
    }
    public boolean testIfNexMoveAccept(){return true;}
    public void applyEffectAfterReceiveAttack(Move move){}

    public double selfStatModifier(String stat){
        return 1;
    }

    public String getName(){
        return this.name;
    }

}
