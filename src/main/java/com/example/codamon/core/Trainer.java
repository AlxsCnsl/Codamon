package com.example.codamon.core;

import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.batlle.control.TrainerController;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.pokemon.Team;

import java.util.ArrayList;

public class Trainer {
    private Team pokemonsTeam;
    private ArrayList<Pokemon> activePokemons = new ArrayList<>();
    private String name;
    private Terrain terrain = null;  ;
    private TrainerController control;

    public Trainer(String name, TrainerController control){
        this.name = name;
        this.control = control;
        this.pokemonsTeam = new Team(this);
    }


    public String toString(){
        return pokemonsTeam.toString();
    }
    //GETTER___________________________________________________________________


    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getActivePokemons(){
        return activePokemons;
    }

    public TrainerController getControl(){
        return control;
    }

    public Team getPokemonsTeam() {
        return this.pokemonsTeam;
    }

    public Terrain getTerrain(){
        return this.terrain;
    }

    public Pokemon getPokemonByIndex(int Index){
        return this.pokemonsTeam.getPokemons().get(Index);
    }

    //SETTER___________________________________________________________________
    public void setTeam(Team pokemonTeam) {
        this.pokemonsTeam = pokemonTeam;
    }

    public void setTerrain(Terrain terrain){
        this.terrain = terrain;
    }

    public void setActivePokemons(Pokemon pokemon){
        activePokemons.add(pokemon);
    }

    public void setController(TrainerController controller) {
        this.control = controller;
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
        if(!pokemonsTeam.getPokemons().isEmpty()) {
            for (Pokemon pokemon : pokemonsTeam.getPokemons()) {
                if (pokemon.equals(sendedPokemon) &&
                        pokemon.getTerrain() == null) {
                    sendedPokemon.gotToTerrain(this.terrain);
                    System.out.println("#TRAINER# " + name + " send  " +
                            sendedPokemon.getName());
                    return;
                }
            }
            System.out.println("#TRAINER# " + name + " wants to send out a " +
                    sendedPokemon.getName() + " that doesn't belong to them onto the terrain.");
            return;
        }System.out.println("#TRAINER#" + name + " has no Pokémon to send out onto the terrain.");
    }

    public void recallPokemon(Pokemon calledPokemon){
        if(!pokemonsTeam.getPokemons().isEmpty()) {
            for (Pokemon pokemon : pokemonsTeam.getPokemons()) {
                if (pokemon.equals(calledPokemon) &&
                        pokemon.getTerrain()!= null) {
                    calledPokemon.quitTerrain();
                    System.out.println("#TRAINER# " + name + " send  " +
                            calledPokemon.getName());
                    return;
                }
            }
            System.out.println("#TRAINER# " + name + " wants to recall out a " +
                    calledPokemon.getName() + " that doesn't belong to them onto the terrain.");
        }
    }

    public void switchPokemon(Pokemon calledPokemon, Pokemon sendedPokemon){
        this.recallPokemon(calledPokemon);
        this.sendPokemon(sendedPokemon);
    }
    //POKEMON TOOLS_____________________________________________________________

    public void addPokemon(Pokemon addedPokemon){
        if(pokemonsTeam.getPokemons().size() < 6){
            pokemonsTeam.addPokemon(addedPokemon);
            addedPokemon.setOwner(this);
            return;
        }System.out.println("#TRAINER# " + name + " wants to add " +
                addedPokemon.getName() + " to his team, but he can't have more than 6 Pokémon.");
    }

    public void setPokemon(int pokemonID, Pokemon addedPokemon){
        pokemonsTeam.getPokemons().set(pokemonID, addedPokemon);
        addedPokemon.setOwner(this);
        return;
    }

    public Boolean getTeamIsAlive(){
        for (Pokemon pokemon : pokemonsTeam.getPokemons()){
            if (pokemon.getIsAlive()){
                return true;
            }
        }
        return false;
    }

    //Turn Graphic Log_________________________________________________________
    public void updateHistory(String text, int size){
        Trainer user = getTerrain().getBattle().
                getPlayersTeams().getFirst().getFirst();
        user.getControl().updateHistory(text, size);
    }
}
