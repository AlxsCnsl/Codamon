package com.example.codamon.core.pokemon;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.Terrain;

import java.util.ArrayList;

public class Team {

    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private Trainer trainer;//Owner
    private Terrain terrain = null;
    private ArrayList<Pokemon> activePokemons;

    //CONSTRUCTOR_______________________________________________________________
    public Team(Trainer trainer, ArrayList<Pokemon> team){
        this.pokemons = team;
        this.trainer = trainer;
        this.constructorAlert();
    }

    public Team(Trainer trainer){
        this.trainer = trainer;
        this.constructorAlert();
    }

    private void constructorAlert(){
        System.out.println("#TEAM# Team : "+this.trainer.getName()+
                " is instantiated");
    }

    //STRINGIFIER______________________________________________________________
    public String toString(){
        String string = "|"+this.trainer.getName()+"'s pokemons :============";
        for(int i = 0; i < pokemons.size(); i++){
            string += "\n==========\n"+
                    "[N°"+(i+1)+"] "+ pokemons.get(i).toString();
        }
        string += "\n======================";
        return string;
    }

    //GETTER___________________________________________________________________
    public Trainer getTrainer() {
        return this.trainer;
    }

    public ArrayList<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public ArrayList<Pokemon> getActivePokemons(){
        return activePokemons;
    }

    //SETTER___________________________________________________________________

    //MODIFIER_________________________________________________________________
    public void addPokemon(Pokemon pokemon){
        this.pokemons.add(pokemon);
        System.out.println("#TEAM# " +this.trainer.getName()+"'s pokemons add "+
                "Pokemon : " + pokemon.getName());
    }

}
