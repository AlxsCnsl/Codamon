package com.example.codamon.core.battle.effect.move_effect;

import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class HealPercentHPEffect implements MoveEffect {
    int percentage;
    public HealPercentHPEffect(int percentage){
        this.percentage = percentage;
    }

    private void heal(Pokemon target){
        int maxHpTarget = target.getBaseState("HP");
        int pvAdded = maxHpTarget*percentage/100;
        System.out.println("#MOVE#MODSTATE# "+
                target.getName()+" Gain max "+pvAdded+" HP");
        target.getHeal(pvAdded);
    }


    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle) {
        if (!targets.isEmpty()) {
            for (Pokemon target : targets) {
                heal(target);
            }
        }
    }

    public static HealPercentHPEffect newMoveEffect(JsonNode effectsNode){
        int percent = effectsNode.get("percent").asInt();
        return new HealPercentHPEffect(percent);
    }
}
