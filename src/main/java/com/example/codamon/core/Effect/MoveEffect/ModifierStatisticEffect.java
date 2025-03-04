package com.example.codamon.core.Effect.MoveEffect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;

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


    public void applyEffect(Pokemon user, Pokemon target, Battle battle){
        System.out.println("#MOVE#MODSTATE# before : "+this.stat+
                " : "+getTargetModifierState(target));
        target.addModifierStat(this.stat, this.modifier);
        System.out.println("#MOVE#MODSTATE# after : "+this.stat+
                " : "+getTargetModifierState(target));
    }

    public static ModifierStatisticEffect newModifierStatEffect(JsonNode effectsNode){
        String stat = effectsNode.get("statistic").asText();
        int modifier = effectsNode.get("modifier").asInt();
        return new ModifierStatisticEffect(stat, modifier);
    }


}
