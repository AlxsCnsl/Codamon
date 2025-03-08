package com.example.codamon.core.battle.effect.battle_effect.status;

import com.example.codamon.core.battle.effect.battle_effect.BattleEffectApplicator;
import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;

public abstract class Status extends BattleEffectApplicator {
    protected int durationTurns = -1;
    protected int activeTurn = 0;
    public Status(){}


    //APPLIER__________________________________________________________________
    public void applyEndPhaseEffect(){
        this.activeTurn += 1;
    }

}
