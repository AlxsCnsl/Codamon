package com.example.codamon.core;

import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.pokemon.Team;

public class Trainer {
    private Team pokemonTeam;
    private String name;
    private Terrain terrain = null;  ;

    public Trainer(String name){
        this.name = name;
        this.pokemonTeam = new Team(this);
    }
    //GETTER___________________________________________________________________
    public String getName() {
        return name;
    }

    public Team getTeam() {
        return this.pokemonTeam;
    }

    public Terrain getTerrain(){
        return this.terrain;
    }

    //SETTER___________________________________________________________________
    public void setTeam(Team pokemonTeam) {
        this.pokemonTeam = pokemonTeam;
    }

    public void setTerrain(Terrain terrain){
        this.terrain = terrain;
    }

    //BATTLE TOOLS_____________________________________________________________
    public void enterTheTerrain(Terrain terrain){
        terrain.addTrainer(this);
    }

    public void quitTheTerrain(Terrain terrain){
        if(this.terrain != null){
            terrain.deleteTrainer(this);
        }else{
            System.out.println("#Trainer#"+this.name+"want to leave terrain but " +
                    "is not on it");
        }
    }

    public void sendPokemon(Pokemon sendedPokemon){
        if(!pokemonTeam.getPokemons().isEmpty()) {
            for (Pokemon pokemon : pokemonTeam.getPokemons()) {
                if (pokemon.equals(sendedPokemon)) {
                    sendedPokemon.gotToTerrain(this.terrain);
                    System.out.println("#TRAINER# " + name + " send o " +
                            sendedPokemon.getName() + " onto the terrain.");
                    return;
                }
            }
            System.out.println("#TRAINER# " + name + " wants to send out a " +
                    sendedPokemon.getName() + " that doesn't belong to them onto the terrain.");
            return;
        }System.out.println("#TRAINER#" + name + " has no Pokémon to send out onto the terrain.");
    }

    public void recallPokemon(Pokemon sendedPokemon){
        if(!pokemonTeam.getPokemons().isEmpty()) {
            for (Pokemon pokemon : pokemonTeam.getPokemons()) {
                if (pokemon.equals(sendedPokemon)) {
                    System.out.println("#TRAINER# " + name + " recall " +
                            sendedPokemon.getName() + " out to the terrain.");
                    sendedPokemon.gotToTerrain(this.terrain);
                }
            }
            System.out.println("#TRAINER# " + name + " wants to recall out a " +
                    sendedPokemon.getName() + " that doesn't belong to them onto the terrain.");
        }
    }
    //POKEMON TOOLS_____________________________________________________________

    public void addPokemon(Pokemon addedPokemon){
        if(pokemonTeam.getPokemons().size() < 6){
            pokemonTeam.addPokemon(addedPokemon);
        }System.out.println("#TRAINER# " + name + " wants to add " +
                addedPokemon.getName() + " to his team, but he can't have more than 6 Pokémon.");
    }
}
