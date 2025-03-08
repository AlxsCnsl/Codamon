package com.example.codamon.core.battle.effect.move_effect;

import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.battle.effect.battle_effect.status.StatusTools;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class StatusGiverEffect implements MoveEffect {
    private String statusName;

    public StatusGiverEffect(String status){
        this.statusName = status;
    }

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle){
        if(!targets.isEmpty()){
            for(Pokemon target: targets){
                target.setMajorStatus(StatusTools.newStatus(this.statusName));
                System.out.println("#MOVE#STATUS_GIVER# "+target.getName()+" is "+
                        this.getStatusName());
            }
        }
    }

    public String toString(){
        return "+Status/"
                + getStatusName().toString();
    }

    public String getStatusName(){
        return statusName;
    }

    public static StatusGiverEffect newMoveEffect(JsonNode effectsNode){
        String status = effectsNode.get("status").asText();
        return new StatusGiverEffect(status);
    }
}
