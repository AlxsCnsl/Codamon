package com.example.codamon.core.Effect.Status;

import java.util.Random;

public class Paralysis extends AbstractStatus {
    public Paralysis(){
        this.name = "Paralysis";
        this.durationTurns = -1;
    }

    public boolean testIfNexMoveAccept(){
        System.out.println("#STATUS# is Paralysis");
        Random rand =new Random();
        int test = rand.nextInt(4)+1;
        if(test == 1){
            System.out.println("#STATUS# Paraliysis Block Move");
            return false;
        }System.out.println("#STATUS# Paraliysis not Block Move");
        return true;
    }

    public double selfStatModifier(String stat){
        if(stat == "SPD"){
         return  0.5;
        }
        return 1.0;
    }
}
