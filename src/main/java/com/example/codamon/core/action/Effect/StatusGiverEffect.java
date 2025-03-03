package com.example.codamon.core.action.Effect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;
import com.example.codamon.core.action.Effect.Status.StatusEnum;
import com.example.codamon.core.action.Effect.Status.StatusTools;

public class StatusGiverEffect implements MoveEffect {
    StatusEnum status;
    public StatusGiverEffect(StatusEnum status ){
        this.status = status;
    }
    public void applyEffect(Pokemon user, Pokemon target, Battle battle){
        target.setStatus(StatusTools.newStatus(status));
    }
}
