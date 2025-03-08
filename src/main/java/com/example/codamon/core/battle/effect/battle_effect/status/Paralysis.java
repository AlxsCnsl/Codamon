package com.example.codamon.core.battle.effect.battle_effect.status;

import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;

import java.util.Random;

public class Paralysis extends Status {
    public Paralysis(){
        this.name = "Paralysis";
        this.durationTurns = -1;
    }

    public boolean getIfNexMoveAccept(){
        System.out.println("#STATUS# is Paralysis");
        Random rand =new Random();
        int test = rand.nextInt(4)+1;
        if(test == 1){
            System.out.println("#STATUS# Paraliysis Block Move");
            return false;
        }System.out.println("#STATUS# Paraliysis not Block Move");
        return true;
    }

    public boolean getIfApplyIsPossible(Pokemon taget){
        for(Type type : taget.getTypes()){
            if(type == Type.ELECTRIC){
                System.out.println("#PARALYSIS# "+taget.getName()+
                        "  cannot be paralyzed.");
                return false;
            }
        }return true;
    }

    public double selfStatModifier(String stat){
        if(stat == "SPE"){
         return  0.5;
        }
        return 1.0;
    }
}
