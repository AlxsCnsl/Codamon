package com.example.codamon.core.batlle.effect.batlle_effect.status;

import com.example.codamon.core.pokemon.Pokemon;

public abstract class Status {
    protected String name;
    protected Pokemon pokemon = null;
    protected int durationTurns = -1;
    protected int activeTurn = 0;
    public Status(){}


    //APPLIER__________________________________________________________________
    public boolean getIfNexMoveAccept(){return true;}
    public boolean getIfApplyIsPossible(Pokemon taget){return true;}
    public double selfStatModifier(String stat){return 1;}
    public void endPhaseDamage(){}

    //TEST_____________________________________________________________________
    public boolean testApplyMoveAccept(){
        return true;
    }


    //GETTER___________________________________________________________________

    public String getName() {
        return this.name;
    }
    public Pokemon getOwner(){
        return this.pokemon;
    }

    //SETTER___________________________________________________________________
    public void setPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
    }
}
