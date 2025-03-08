package com.example.codamon.core.batlle.effect.batlle_effect.status;

import com.example.codamon.core.batlle.effect.batlle_effect.status.Status;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;

import static java.nio.file.Files.getOwner;

public class Burn extends Status {

    public Burn(){
        this.name = "Burn";
        this.durationTurns = -1;
    }

    public void endPhaseDamage(){
        int damage = getOwner().getCurrentState("HP")/16;
        System.out.println("#BURN# "+getOwner().getName()+
                "  is Burner and loos Hp.");
        getOwner().getDamage(damage);
    }

    public boolean getIfApplyIsPossible(Pokemon taget){
        for(Type type : taget.getTypes()){
            if(type == Type.FIRE){
                System.out.println("#BURN# "+taget.getName()+
                        "  cannot be burned.");
                return false;
            }
        }return true;
    }

    public double selfStatModifier(String stat){
        if(stat == "SPA"){
            return  0.5;
        }
        return 1.0;
    }
}
