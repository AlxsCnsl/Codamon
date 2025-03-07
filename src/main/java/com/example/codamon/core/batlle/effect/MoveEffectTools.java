package com.example.codamon.core.batlle.effect;

import com.example.codamon.core.batlle.effect.move_effect.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class MoveEffectTools {

    public MoveEffectTools(){}

    public static void effectSetter(JsonNode effectNode,
                                    ArrayList<MoveEffect> effects){
        String typeEffect = effectNode.get("type_effect").asText();
        switch (typeEffect){
            case "DamageApplicatorEffect" ->
                effects.add(DamageApplicatorEffect.
                        newMoveEffect(effectNode));

            case "SwitchActivePokemonEffect" ->
                effects.add(SwitchActivePokemonEffect.
                        newMoveEffect(effectNode));

            case "TypesFilterEffect" ->
                    effects.add(TypesFilterEffect.
                            newMoveEffect(effectNode));

            case "StatusGiverEffect" ->
                    effects.add(StatusGiverEffect.
                            newMoveEffect(effectNode));

            case "ProbabilityEffect" ->
                    effects.add(ProbabilityEffect.
                            newMoveEffect(effectNode));

            case "ModifierStatisticEffect" ->
                    effects.add(ModifierStatisticEffect.
                            newMoveEffect(effectNode));

            default ->
                    System.err.println("Type_effect is not in switch : "
                            +typeEffect);
        }
        System.out.println("#MOVE#INIT# add efect : "+ typeEffect);
    }
}
