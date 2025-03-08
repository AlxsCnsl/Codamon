package com.example.codamon.core.battle.effect.move_effect;

import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class DamageApplicatorEffect implements MoveEffect {

    public DamageApplicatorEffect()
    {
    }

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle){
        if(!targets.isEmpty()){
            for(Pokemon target: targets){
                move.applyDamage(move.getOwner(), target, battle);
            }
        }
    }

    public static DamageApplicatorEffect newMoveEffect(JsonNode effectsNode){
        return new DamageApplicatorEffect();
    }
}