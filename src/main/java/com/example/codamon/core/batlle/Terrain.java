package com.example.codamon.core.batlle;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.ArrayList;

public class Terrain {

    private ArrayList<Trainer> trainersTeam = new ArrayList<>();//pour le 2playerVS2player
    private ArrayList<Pokemon> activePokemons = new ArrayList<>(); // pour le 2pokemonVs2pokemon
    private Battle battle = null;

    public Terrain(){

    }

    public String toString(){
        String str = "";
        for(Pokemon pokemon : activePokemons){
            str += ">>>>>>>>>>>>>>>>>>\n";
            str += pokemon.toString();
        }
        str += "\n>>>>>>>>>>>>>>>>>>";
        return str;
    }


    //GETTER___________________________________________________________________
    public ArrayList<Pokemon> getActivePokemons(){
        return this.activePokemons;
    }

    public ArrayList<Trainer> getTrainersTeam(){
        return this.trainersTeam;
    }

    public Battle getBattle(){
        return this.battle;
    }
    //SETTER____________________________________________________________________

    public void setBattle(Battle battle){
        this.battle=battle;
    }

    public void addPokemon(Pokemon pokemon) {
        if(pokemon.getTerrain() != null){
            return;
        }
        if (activePokemons != null &&
                !activePokemons.isEmpty()) {
            for (Pokemon activePokemon : activePokemons) {
                if (activePokemon.equals(pokemon)) {
                    System.out.println("#TERRAIN# impossible to add "
                            + pokemon.getName() + " on this terrain");
                    return;
                }
            }
        }
        System.out.println("#TERRAIN# "
                + pokemon.getName() + " added on this terrain");
        this.activePokemons.add(pokemon);
        pokemon.getOwner().setActivePokemons(pokemon);
        pokemon.setTerrain(this);
    }

    public void deletePokemon(Pokemon pokemon){
        if (activePokemons != null &&
                !activePokemons.isEmpty()) {
            for (Pokemon activePokemon : activePokemons) {
                if (activePokemon.equals(pokemon)) {
                    System.out.println("#TERRAIN# "
                            + pokemon.getName() + "removed on this terrain");
                    activePokemons.remove(pokemon);
                    pokemon.getOwner().getActivePokemons().remove(pokemon);
                    pokemon.setTerrain(null);
                    return;
                }
            }
        }
        System.out.println("#TERRAIN# impossible to delete "
                + pokemon.getName() + " on this terrain");
    }

    public void addTrainer(Trainer trainer){
        if(trainer.getTerrain() != null){
            System.out.println("#TERRAIN# impossible to add "
                    + trainer.getName() + " on this terrain");
            return;
        }
        if (trainersTeam != null && !trainersTeam.isEmpty()) {
            for (Trainer actTrainer : trainersTeam) {
                if (actTrainer.equals(trainer)) {
                    System.out.println("#TERRAIN# impossible to add "
                            + trainer.getName() + " on this terrain");
                    return;
                }
            }
        }
        System.out.println("#TERRAIN# "
                + trainer.getName() + " added on this terrain");
        this.trainersTeam.add(trainer);
        trainer.setTerrain(this);
    }

    public void deleteTrainer(Trainer trainer){
        if (trainersTeam != null &&
                !trainersTeam.isEmpty()) {
            for (Trainer actTrainer : trainersTeam) {
                if (actTrainer.equals(trainer)) {
                    System.out.println("#TERRAIN# "
                            + trainer.getName() + "removed on this terrain");
                    trainersTeam.remove(trainer);
                    trainer.setTerrain(null);
                    return;
                }
            }
        }
        System.out.println("#TERRAIN# impossible to delete "
                + trainer.getName() + " on this terrain");
    }
}

