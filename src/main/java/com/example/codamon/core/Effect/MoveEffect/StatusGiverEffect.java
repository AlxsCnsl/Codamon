package com.example.codamon.core.Effect.MoveEffect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;
import com.example.codamon.core.Effect.Status.Status;
import com.example.codamon.core.Effect.Status.StatusTools;
import com.fasterxml.jackson.databind.JsonNode;

public class StatusGiverEffect implements MoveEffect {
    private Status status;
    public StatusGiverEffect(Status status){
        this.status = status;
    }
    public void applyEffect(Pokemon user, Pokemon target, Battle battle){
        target.setStatus(StatusTools.newStatus(status));
        System.out.println("#MOVE#STATUS_GIVER# "+target.getName()+" is "+
                this.getStatus());
    }

    public String toString(){
        return "+Status/"
                +getStatus().toString();
    }

    public Status getStatus(){
        return status;
    }

    public static StatusGiverEffect newMoveEffect(JsonNode effectsNode){
        Status status = StatusTools.
                stringToStatusEnum(effectsNode.get("status").asText());
        return new StatusGiverEffect(status);
    }
}
