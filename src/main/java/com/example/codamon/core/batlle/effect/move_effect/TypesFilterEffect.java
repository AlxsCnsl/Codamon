package com.example.codamon.core.batlle.effect.move_effect;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.move.MoveTools;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.type.TypeTools;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class TypesFilterEffect implements MoveEffect {

    private ArrayList<Type> types;
    private ArrayList<MoveEffect> effects;

    public TypesFilterEffect(ArrayList<Type> types, ArrayList<MoveEffect> effects){
        this.types = types;
        this.effects = effects;
    }

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle){
        System.out.println(this.types.toString());
        targets = this.targetFilter(targets);
        if(!targets.isEmpty()){
            for(Pokemon target: targets){
                for (MoveEffect effect: effects){
                    effect.applyEffect(move, targets, battle);
                }
            }
        }
    }

    public ArrayList<Pokemon> targetFilter(ArrayList<Pokemon> targets ){
        String consoleLog = "#EFFECT# Start filter : ";
        ArrayList<Pokemon> newTargets = new ArrayList<>();
        for(Pokemon target : targets){
            consoleLog += target.getName()+" ";
            for(Type type : this.types){
                if(!target.hasType(type) && !newTargets.contains(target)){
                    newTargets.add(target);
                    consoleLog += "> Targetable || " ;
                }else{
                    consoleLog += "> Untargetable || " ;
                }
            }
        }System.out.println(consoleLog);
        return newTargets;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public ArrayList<MoveEffect> getEffects() {
        return effects;
    }

    public static TypesFilterEffect  newMoveEffect(JsonNode effectsNode){
        ArrayList<Type> types =new ArrayList<>();
        for(int i = 0 ; i < effectsNode.get("types").size() ; i++){
            types.add(TypeTools.stringToType(
                    effectsNode.get("types").get(i).asText()));
        }
        ArrayList<MoveEffect> effects =new ArrayList<>();
        TypesFilterEffect typesFilterEffect =
                new TypesFilterEffect(types, effects);
        JsonNode nextEffectsNode = effectsNode.get("effects");
        MoveTools.effectsBuilder(nextEffectsNode, effects);
        return typesFilterEffect;
    }
}
