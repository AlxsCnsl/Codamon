package com.example.codamon.core.effect.move_effect;

import com.example.codamon.core.action.category.Category;
import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.effect.batlle_effect.status.StatusTools;
import com.example.codamon.core.type.TypeTools;
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