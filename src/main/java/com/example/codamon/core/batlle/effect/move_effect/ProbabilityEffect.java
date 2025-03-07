package com.example.codamon.core.batlle.effect.move_effect;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.batlle.move.MoveTools;
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

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle){
        Random rand = new Random();
        int maxProba = (int) (getProbability()*100);
        int test = rand.nextInt(100)+1;
        if(test<maxProba){
            System.out.println("#MOVE#PROBABLY# ProbablySuccess");
            for (MoveEffect effect: effects){
                effect.applyEffect(move, targets, battle);
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

    public static ProbabilityEffect newMoveEffect(JsonNode effectsNode){
        double probability = effectsNode.get("probability").asDouble();

        ArrayList<MoveEffect> effects =new ArrayList<>();

        ProbabilityEffect probabilityEffect =
                new ProbabilityEffect(probability, effects);

        JsonNode nextEffectsNode = effectsNode.get("effects");

        MoveTools.effectsBuilder(nextEffectsNode, effects);

        return probabilityEffect;
    }
}
