package com.example.codamon.core.batlle.move;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.type.TypeTools;
import com.example.codamon.core.batlle.move.category.Category;
import com.example.codamon.core.batlle.effect.move_effect.MoveEffect;

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
    private Pokemon owner = null;
    private ArrayList<Pokemon> targets= new ArrayList<>();

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

    public Pokemon getOwner(){
        return this.owner;
    }

    //SETTER___________________________________________________________________
    public void setOwner(Pokemon owner){
        this.owner = owner;
    }

    public void unsetOwner(){
        this.owner = null;
    }

    public void addTarget(Pokemon pokemon){
        if(!targets.isEmpty()) {
            for (Pokemon target : targets) {
                if(target.equals(pokemon)) {
                    return;
                }
            }
        }
        this.targets.add(pokemon);
    }

    public void deleteAllTargets(){
        this.targets = new ArrayList<>();
    }

    public void deleteTarget(Pokemon pokemon){
        if(!targets.isEmpty()) {
            for (Pokemon target : targets) {
                if(target.equals(pokemon)){
                    targets.remove(pokemon);
                }
            }
        }
    }

    //EXECUTOR_________________________________________________________________
    public void execute(){
        if(targets.isEmpty()){
            noTargetLog();
            return;
        }
        if(!name.equals("Switch")){changeTargetIfIsSwitch();}
        executeLog();
        if(this.currentPp > 0 || this.currentPp == -1){
            if(!getOwner().asMajorStatus() ||
                    getOwner().getMajorStatus().getIfNexMoveAccept() ||
                    name.equals("Switch") ){
                for(MoveEffect effect : this.effects){
                    effect.applyEffect(
                            this, targets , owner.getTerrain().getBattle());
                }
            }
            if(currentPp!= -1){this.currentPp --;}
            successExecuteLog();
            this.deleteAllTargets();
            return;
        }anyPpLog();
    }

    private void changeTargetIfIsSwitch(){
        for (Pokemon target : targets){
            if(target.getTerrain()==null){
                addOtherEnemyTarget(target);
                targets.remove(target);
            }
        }
    }
    private void addOtherEnemyTarget(Pokemon target){
        Terrain terrain = target.getOwner().getTerrain();
        targets.add(terrain.getActivePokemons().getFirst());
        System.out.println("#MOVE# Change Target of "+name+
                " of "+owner.getName());
    }

    private void noTargetLog(){
        System.out.println("#MOVE# "+owner.getName()+" want use "+this.name+
                " but : no targets defined");
    }

    private void executeLog (){
        System.out.println("#MOVE# "+owner.getName()+" want use "+this.name);
    }

    private void successExecuteLog (){
        System.out.println("#MOVE# "+owner.getName()+" use "+this.name);
    }

    private void anyPpLog(){
        System.out.println("#MOVE# any Pp for use move : " + this.name);
    }

    public void applyDamage(Pokemon user, Pokemon target, Battle battle){
        String atk = "ATK"; String def = "DEF";
        if(this.category == Category.SPECIAL){
            atk = "SPA"; def = "SPD";
        }
        int damage = (int) ((((((user.getLevel() * 0.4 + 2)
                * user.getCurrentState(atk) * this.getPower())
                / target.getCurrentState(def))/50)+2)
                * getStab(user)
                * TypeTools.getTypesEfficiency(
                        this.getType(), target.getTypes())
                *otherParameter()
                * isCritical()
                * rng());
        target.setLastDamageReceived(damage);
        target.getDamage(damage);
    }

    private double otherParameter(){
        double multiplier = 1;
        return multiplier;
    }

    private int isCritical(){
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

    private double rng(){
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

