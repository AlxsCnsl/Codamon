package com.example.codamon.core.batlle.effect.move_effect;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.move.MoveTools;
import com.example.codamon.core.pokemon.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class TargetSelfEffect implements MoveEffect{
    private ArrayList<MoveEffect> effects;

    public TargetSelfEffect(ArrayList<MoveEffect> effects){
        this.effects = effects;
    }

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle) {
        targets.clear();targets.add(move.getOwner());
        if(!targets.isEmpty()){
            for(Pokemon target: targets){
                for (MoveEffect effect: effects){

                    effect.applyEffect(move, targets, battle);
                }
            }
        }
    }

    public static TargetSelfEffect newMoveEffect(JsonNode effectsNode){
        ArrayList<MoveEffect> effects =new ArrayList<>();
        TargetSelfEffect targetSelfEffect =
                new TargetSelfEffect(effects);
        JsonNode nextEffectsNode = effectsNode.get("effects");
        MoveTools.effectsBuilder(nextEffectsNode, effects);
        return targetSelfEffect;
    }
}
