package com.example.codamon.core.Effect.MoveEffect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;
import com.example.codamon.core.action.Move.MoveTools;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Random;

public class ProbabilityEffect implements MoveEffect {

    private double probability;
    private ArrayList<MoveEffect> effects;

    public ProbabilityEffect(double probability, ArrayList<MoveEffect> effects){
        this.probability = probability;
        this.effects = effects;
    }

    public void applyEffect(Pokemon user, Pokemon target, Battle battle){
        Random rand = new Random();
        int maxProba = (int) (getProbability()*100);
        int test = rand.nextInt(100)+1;
        if(test<maxProba){
            System.out.println("#MOVE#PROBABLY# ProbablySuccess");
            for (MoveEffect effect: effects){
                effect.applyEffect(user, target, battle);
            }
        }else{
            System.out.println("#MOVE#PROBABLY# ProbablyLoose");
        }
    }

    public double getProbability() {
        return probability;
    }

    public ArrayList<MoveEffect> getEffects() {
        return effects;
    }

    public static ProbabilityEffect newProbabilityEffect(JsonNode effectsNode){
        double probability = effectsNode.get("probability").asDouble();
        ArrayList<MoveEffect> effects =new ArrayList<>();
        ProbabilityEffect probabilityEffect =
                new ProbabilityEffect(probability, effects);
        JsonNode nextEffectsNode = effectsNode.get("effects");
        MoveTools.effectsBuilder(nextEffectsNode, effects);
        return probabilityEffect;
    }
}
