package com.example.codamon.core.battle.effect.battle_effect;

import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;

public abstract class BattleEffectApplicator {
    protected String name;
    protected Pokemon pokemon = null;

    public BattleEffectApplicator(){}

    public String getName() {
        return this.name;
    }

    public void applyExecuteMovePhaseEffect(){}
    public void applyEndPhaseEffect(){
    }
    public void applyEffectAfterReceiveAttack(Move move){}

    public boolean testIfTargetAccept(){
        return true;
    }

    public boolean testApplyMoveAccept(){
        return true;
    }

    public double selfStatModifier(String stat){
        return 1;
    }

    public Pokemon getPokemon(){
        return this.pokemon;
    }

    public void setPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
    }
}
