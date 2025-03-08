package com.example.codamon.core.batlle.effect.batlle_effect.status;

import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;

public class Poison extends Status {

    public Poison(){
        this.name = "Poison";
        this.durationTurns = -1;
    }
    public void endPhaseDamage(){
        int damage = getOwner().getCurrentState("HP")/16;
        System.out.println("#Poison# "+getOwner().getName()+
                "  is Poisonous and loos Hp.");
        getOwner().getDamage(damage);
    }

    public boolean getIfApplyIsPossible(Pokemon taget){
        for(Type type : taget.getTypes()){
            if(type == Type.POISON || type == Type.STEEL){
                System.out.println("#Poison# "+taget.getName()+
                        "  cannot be poisosnous.");
                return false;
            }
        }return true;
    }
}
