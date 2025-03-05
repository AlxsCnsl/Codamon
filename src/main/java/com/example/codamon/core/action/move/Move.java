package com.example.codamon.core.action.move;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.type.TypeTools;
import com.example.codamon.core.action.Action;
import com.example.codamon.core.action.category.Category;
import com.example.codamon.core.effect.move_effect.MoveEffect;

import java.util.ArrayList;
import java.util.Random;

public class Move extends Action {
    private String name;
    private Type type;
    private Category category;
    private int power;
    private int accurate;
    private int pp;
    private int currentPp;
    private ArrayList<MoveEffect> effects;

    public Move(String name, Type type, Category category,
                int power, int accurate, int pp, int priority) {
        super(priority);
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accurate = accurate;
        this.pp = pp;
        this.currentPp = pp;
    }

    public Move(String name, Type type, Category category,
                int power, int accurate, int pp, int priority,
                ArrayList<MoveEffect> effects) {
        super(priority);
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accurate = accurate;
        this.pp = pp;
        this.currentPp = pp;
        this.effects = effects;
    }
    //STRINGIFIER______________________________________________________________
    public String toString(){
        return "["+this.name+"|"+this.currentPp+"/"+this.pp+"]";
    }
    //GETTER___________________________________________________________________

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public int getPower() {
        return power;
    }

    public int getAccurate() {
        return accurate;
    }

    public int getPp() {
        return pp;
    }

    public int getCurrentPp() {
        return currentPp;
    }

    public ArrayList<MoveEffect> getEffects(){return this.effects; }

    //EXECUTOR_________________________________________________________________
    public void execute(Pokemon user, Pokemon target, Battle battle){
        this.executeLog(user);
        if(this.currentPp > 0 ){
            this.currentPp -= 1;
            if(this.power != 0) {
                this.applyDamage(user, target, battle);
            }
            if (this.effects != null){
                for(MoveEffect effect : this.effects){
                    effect.applyEffect(user,target, battle);
                }
            }
            this.successExecuteLog(user);
        }
    }

    private void executeLog (Pokemon user){
        System.out.println("#MOVE# "+user.getName()+" want use "+this.name);
    }

    private void successExecuteLog (Pokemon user){
        System.out.println("#MOVE# "+user.getName()+" use "+this.name);
    }

    private void anyPpLog(){
        System.out.println("#MOVE# any Pp for use move : " + this.name);
    }

    private void applyDamage(Pokemon user, Pokemon target, Battle battle){
        String atk = "ATK"; String def = "DEF";
        if(this.category == Category.SPECIAL){
            atk = "SPA"; def = "SPD";
        }
        int damage = (int) ((((((user.getLvl() * 0.4 + 2)
                        * user.getCurrentState(atk) * this.getPower())
                        / target.getCurrentState(def))/50)+2)
                        * getStab(user)
                        * TypeTools.getTypesEfficiency(this.getType(), target.getTypes())
                        //Future Parameter
                        * getCritical()
                        * getRng());
        target.getDamage(damage);
    }

    private int getCritical(){
        Random random = new Random();
        int randCC = random.nextInt(16 )+1;
        if(randCC == 1){
            System.out.println("#MOVE# critical hit");
            return 2;
        }
        return 1;
    }

    private double getStab(Pokemon user){
        for(Type pokeType : user.getTypes()){
            if(pokeType == this.type){
                System.out.println("#MOVE# Stab");
                return 1.5;
            }
        }
        return 1.0;
    }

    private double getRng(){
        Random random = new Random();
        return  0.8 + (1.0 - 0.8) *  random.nextDouble();
    }

    private Boolean accurateTest(Pokemon user, Pokemon target){
        Random random = new Random();
        if(this.getAccurate() == 101){
            return true;
        }
        int accurate = (int) ((this.getAccurate()
                * user.getFactorModifiedState("ACC"))
                /target.getFactorModifiedState("ESC"));
        int test = random.nextInt( 100 )+1;
        if (test < accurate){
            return true;
        }
        return false;
    }
}
