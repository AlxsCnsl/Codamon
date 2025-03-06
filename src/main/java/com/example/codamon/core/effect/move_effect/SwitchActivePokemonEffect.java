package com.example.codamon.core.effect.move_effect;

import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class SwitchActivePokemonEffect implements MoveEffect{

    public SwitchActivePokemonEffect(){
    }

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle){
        if(!targets.isEmpty()){
            Pokemon target = targets.getLast();
            target.getOwner().switchPokemon(move.getOwner(), target);
        }
    }


    public static SwitchActivePokemonEffect newMoveEffect(JsonNode effectsNode){
        return new SwitchActivePokemonEffect();
    }
}
