package com.example.codamon.core.batlle.effect.move_effect;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class ModifierStatisticEffect implements MoveEffect {
    private String stat;
    private int modifier;
    public ModifierStatisticEffect(String stat, int modifier){
        this.stat = stat;
        this.modifier = modifier;
    }

    private int getTargetModifierState(Pokemon targe){
        return targe.getModifierState(this.stat);
    }


    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle) {
        if (!targets.isEmpty()) {
            for (Pokemon target : targets) {
                System.out.println("#MOVE#MODSTATE# before : " + this.stat +
                        " : " + getTargetModifierState(target));
                target.addModifierStat(this.stat, this.modifier);
                System.out.println("#MOVE#MODSTATE# after : " + this.stat +
                        " : " + getTargetModifierState(target));
            }
        }
    }

    public static ModifierStatisticEffect newMoveEffect(JsonNode effectsNode){
        String stat = effectsNode.get("statistic").asText();
        int modifier = effectsNode.get("modifier").asInt();
        return new ModifierStatisticEffect(stat, modifier);
    }


}
